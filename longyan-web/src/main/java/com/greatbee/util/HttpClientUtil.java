package com.greatbee.util;

import com.xiwa.base.manager.ManagerException;
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
import org.apache.http.client.utils.DateUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

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
        System.out.println(url);
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        StringBuffer sb = new StringBuffer();

        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            nvps.add(new BasicNameValuePair(key, params.get(key)));
            sb.append(key + "=" + params.get(key) + "&");
        }
        String getUrl = url + "?" + sb.toString();
        System.out.println(DateUtils.formatDate(new Date(),"yyyy-MM-dd hh:mm:ss")+getUrl);
        try {
            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String response = invoke(httpClient, httpost);
        try {
            return JSONObject.fromObject(response);
        } catch (JSONException e) {
            System.out.println(e.getMessage());
            System.out.println("[response][" + response.getBytes().length / 1000 + "KB]" + response);
        }
        return null;
    }


    public static JSONObject httpGet(String url) {
        System.out.println(url);
        DefaultHttpClient httpClient = new DefaultHttpClient();

        HttpGet httpGet = new HttpGet(url);

        String response = invoke(httpClient, httpGet);
        try {
            return JSONObject.fromObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
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
            String response = IOUtils.toString(inputStream, encoding);
            return response;
        } catch (IOException e) {
            throw new ManagerException(e.getMessage());
        }
    }
}

