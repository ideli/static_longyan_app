package com.greatbee.util;

import com.greatbee.bean.constant.LanchuiConstant;
import com.xiwa.base.pipeline.PipelineContext;
import com.xiwa.base.pipeline.PipelineException;
import com.xiwa.base.util.DataUtil;
import com.xiwa.base.util.StringUtil;
import net.sf.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

/**
 * @author XueChao.Zhang
 * @version 1.00 14-7-23 下午2:24
 */
public class BusinessUtil implements LanchuiConstant {

    /**
     * 可以根据 "xxx.xxx.xxx" 的形式获取Map对象
     * 递归调用
     *
     * @param key
     * @param objectMap
     * @return
     * @throws com.xiwa.base.pipeline.PipelineException
     */
    public static Object getMapValue(String key, Map<String, Object> objectMap) throws PipelineException {
        JSONObject objectJsonMap = JSONObject.fromObject(objectMap);
        if (StringUtil.isValid(key)) {
            String[] keys = key.split("\\.");
            if (objectJsonMap.containsKey(keys[0])) {
                if (keys.length == 1) {
                    return objectJsonMap.get(keys[0]);
                } else {
                    return getMapValue(key.substring(key.indexOf(".") + 1, key.length()), (Map<String, Object>) objectJsonMap.get(keys[0]));
                }
            } else {
                throw new PipelineException("no key in hashMap,key=" + key);
            }
        } else {
            return null;
        }
    }

    /**
     * 获取WebApplicationContext对象
     *
     * @return
     */
    public static WebApplicationContext getWebApplicationContext() {
        ServletContext sc = (((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()).getSession().getServletContext();
        WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(sc);
        return applicationContext;
    }

    /**
     * 从服务器取得文件
     * @param pipelineContext
     * @param response
     * @throws java.io.IOException
     */
    public static void writeFileToResponse(PipelineContext pipelineContext, HttpServletResponse response) throws IOException {
        ServletOutputStream out = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        String filePath = (String) pipelineContext.getRequest().getDataMap().get(Request_FilePath);
        String storageName = (String) pipelineContext.getRequest().getDataMap().get(Request_Store_Name);
        int fileLength = DataUtil.getInt(pipelineContext.getRequest().getDataMap().get(Request_FileLength), -1);

        out = response.getOutputStream();
        response.setHeader("Content-disposition", "attachment;filename="
                + storageName);
        response.setContentLength(fileLength);
        bis = new BufferedInputStream(new FileInputStream(filePath));
        bos = new BufferedOutputStream(out);
        byte[] buff = new byte[2048];
        int bytesRead;
        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
            bos.write(buff, 0, bytesRead);
        }
        if (bis != null)
            bis.close();
        if (bos != null)
            bos.close();
    }


}
