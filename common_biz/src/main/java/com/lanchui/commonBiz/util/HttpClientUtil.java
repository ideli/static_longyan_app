package com.lanchui.commonBiz.util;

import com.lanchui.commonBiz.bean.constant.PlatformType;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.pipeline.PipelineContext;
import com.xiwa.base.util.Charset;
import com.xiwa.base.util.StringUtil;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * NVWA HTTP
 * <p/>
 * Created by lufaxdev on 2014/11/25.
 */
public class HttpClientUtil {

    private static Logger logger = Logger.getLogger(HttpClientUtil.class);

    /**
     * Invoke
     *
     * @param httpclient
     * @param httpost
     * @return
     */
    private static String invoke(HttpClient httpclient,
                                 HttpUriRequest httpost) {
        HttpResponse response = null;

        try {
            response = httpclient.execute(httpost);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        HttpEntity entity = response.getEntity();
        String charset = EntityUtils.getContentCharSet(entity);
        String body = null;
        try {
            body = EntityUtils.toString(entity);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return body;
    }

    /**
     * Get
     *
     * @param url
     * @return
     */
    public static String get(String url) throws ManagerException {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String response = invoke(httpClient, new HttpGet(url));
        httpClient.getConnectionManager().shutdown();
        if (StringUtil.isInvalid(response)) {
            throw new ManagerException("response is null");
        }
        return response;
    }

    /**
     * POST
     *
     * @param url
     * @param params
     * @return
     */
    public static JSONObject post(String url, Map<String, String> params) {
        logger.info("[http][request]url=" + url + ",params=" + JSONObject.fromObject(params).toString());
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            nvps.add(new BasicNameValuePair(key, params.get(key)));
        }
        try {
            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String response = invoke(httpClient, httpost);
        logger.info("[http][response]" + response);
        try {
            return JSONObject.fromObject(response);
        } catch (JSONException e) {
            System.out.println(e.getMessage());
            System.out.println("[response][" + response.getBytes().length / 1000 + "KB]" + response);
        }
        return null;
    }

    /**
     * WXPOST
     *
     * @param url
     * @param params
     * @return
     */
    public static JSONObject wxPost(String url, Map<String, String> params) throws ManagerException {
        logger.info("[http][request]url=" + url + ",params=" + JSONObject.fromObject(params).toString());

        String xmlStr = _mapToXMLStr(params);
        String response = sendPostBody(url, xmlStr);

        logger.info("[http][response]" + response);
        try {
            // String resStr = new String(response.getBytes("ISO-8859-1"),"UTF-8");
            return _xmlToJSON(response);
        } catch (JSONException e) {
            System.out.println(e.getMessage());
            System.out.println("[response][" + response.getBytes().length / 1000 + "KB]" + response);
        }
        return null;
    }

    /**
     * map对象转换成xml字符串
     *
     * @param map
     * @return
     */
    private static String _mapToXMLStr(Map<String, String> map) {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("xml");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (!StringUtil.equals(entry.getKey(), "sign")) {
                Element elm = root.addElement(entry.getKey());
                elm.setText(entry.getValue());
            }
        }
        Element elms = root.addElement("sign");
        elms.setText(map.get("sign"));
        return document.asXML();
    }

    /**
     * 解析微信传过来的 xml数据
     *
     * @param xmlStr
     * @return
     */
    private static JSONObject _xmlToJSON(String xmlStr) {
        try {
            Document document = DocumentHelper.parseText(xmlStr);
            //获取文档的根节点
            Element root = document.getRootElement();
            Map<String, String> map = new HashMap<String, String>();
            //对某节点下的所有子节点进行遍历.
            for (Iterator it = root.elementIterator(); it.hasNext(); ) {
                Element element = (Element) it.next();
                map.put(element.getName(), element.getText());
            }
            return JSONObject.fromObject(map);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * POST    多一个日志参数
     *
     * @param url
     * @param params
     * @return
     */
    public static JSONObject post(String url, Map<String, String> params, boolean logEnable) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            nvps.add(new BasicNameValuePair(key, params.get(key)));
        }
        try {
            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            if (logEnable) {
                logger.error(e.getMessage());
            }
        }
        String response = invoke(httpClient, httpost);
        try {
            if (logEnable) {
                logger.info(response);
            }
            return JSONObject.fromObject(response);
        } catch (JSONException e) {
            System.out.println(e.getMessage());
            System.out.println("[response][" + response.getBytes().length / 1000 + "KB]" + response);
            if (logEnable) {
                logger.error(e.getMessage());
                logger.error("[response][" + response.getBytes().length / 1000 + "KB]" + response);
            }
        }
        return null;
    }

    public static String sendPostBody(String postUrl, String postBody) throws ManagerException {
        try {
            if (StringUtil.isInvalid(postUrl)) {
                throw new ManagerException("url is null");
            } else if (StringUtil.isInvalid(postBody)) {
                throw new ManagerException("post body is null");
            }
            // Configure and open a connection to the site you will send the request
            URL url = new URL(postUrl);
            URLConnection urlConnection = url.openConnection();
            // 设置doOutput属性为true表示将使用此urlConnection写入数据
            urlConnection.setDoOutput(true);
            // 定义待写入数据的内容类型，我们设置为application/x-www-form-urlencoded类型
            urlConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            // 得到请求的输出流对象
            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream(), Charset.UTF_8);

            // 把数据写入请求的Body
            out.write(postBody);
            out.flush();
            out.close();

            // 从服务器读取响应
            InputStream inputStream = urlConnection.getInputStream();
            String encoding = urlConnection.getContentEncoding();
            if (StringUtil.isInvalid(encoding)) {
                encoding = "UTF-8";
            }
            String response = IOUtils.toString(inputStream, encoding);
            return response;
        } catch (IOException e) {
            throw new ManagerException(e.getMessage());
        }
    }

    /**
     * 带头参数的post请求
     *
     * @param postUrl
     * @param postBody
     * @param headerKey
     * @param headerValue
     * @return
     * @throws ManagerException
     */
    public static String sendPost(String postUrl, String postBody, String headerKey, String headerValue) throws ManagerException {
        try {
            if (StringUtil.isInvalid(postUrl)) {
                throw new ManagerException("url is null");
            } else if (StringUtil.isInvalid(postBody)) {
                throw new ManagerException("post body is null");
            }

            //转换base64编码
            String base64Value = Base64.encode(headerValue.getBytes());
            logger.info("base64Value=" + base64Value);

            // Configure and open a connection to the site you will send the request
            URL url = new URL(postUrl);
            URLConnection urlConnection = url.openConnection();
            // 设置doOutput属性为true表示将使用此urlConnection写入数据
            urlConnection.setDoOutput(true);
            // 定义待写入数据的内容类型，我们设置为application/x-www-form-urlencoded类型
            urlConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            urlConnection.setRequestProperty(headerKey, base64Value);
            // 得到请求的输出流对象
            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream(), Charset.UTF_8);

            // 把数据写入请求的Body
            out.write(postBody);
            out.flush();
            out.close();

            // 从服务器读取响应
            InputStream inputStream = urlConnection.getInputStream();
            String encoding = urlConnection.getContentEncoding();
            if (StringUtil.isInvalid(encoding)) {
                encoding = "UTF-8";
            }
            String response = IOUtils.toString(inputStream, encoding);
            return response;
        } catch (IOException e) {
            throw new ManagerException(e.getMessage());
        }
    }

    /**
     * 根据UA判断平台
     *
     * @param context
     * @return
     */
    public static String getPlatform(PipelineContext context) {
        String ua = context.getRequest().getHttpServletRequest().getHeader("User-Agent");
        String platform = "android";
        if (ua.indexOf(PlatformType.IPHONE.getType()) > 0 || ua.indexOf(PlatformType.IPAD.getType()) > 0) {
            platform = "ios";
        }
        return platform;
    }
}

