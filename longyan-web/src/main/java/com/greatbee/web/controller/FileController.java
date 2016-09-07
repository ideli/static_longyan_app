package com.greatbee.web.controller;

import com.greatbee.bean.constant.FileStorageType;
import com.greatbee.bean.constant.LanchuiConstant;
import com.greatbee.util.BusinessUtil;
import com.lanchui.commonBiz.bean.Storage;
import com.lanchui.commonBiz.manager.StorageManager;
import com.lanchui.commonBiz.util.StorageUtil;
import com.xiwa.base.bean.Response;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.pipeline.Pipeline;
import com.xiwa.base.pipeline.PipelineContext;
import com.xiwa.base.util.DataUtil;
import com.xiwa.base.util.StringUtil;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;

/**
 * Created by wangj on 2015/4/16.
 */
@Controller
public class FileController extends BaseController implements LanchuiConstant {

    private static final Logger logger = Logger.getLogger(FileController.class);
//    @Autowired
//    private Pipeline loadFile;
//    @Autowired
//    private StorageManager weixinStorageManager;

    /**
     * 文件下载
     *
     * @param fileName
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/common/storage/{fileName}.{type}")
    public
    @ResponseBody
    void fileDownload(@PathVariable String fileName, @PathVariable String type, HttpServletResponse response) {
//        logger.info("[fileDownload]fileName=" + fileName + ",type=" + type);
//        PipelineContext pipelineContext = buildPipelineContent();
//        String storageName = fileName + "." + type;
//        logger.info("[fileDownload]storageName=" + storageName);
//        pipelineContext.getRequest().addRequestData(Request_Store_Name, storageName);
//        try {
//            pipelineContext.getRequest().addRequestData(Request_File_Storage_Type, FileStorageType.Normal.getType());
//            loadFile.execute(pipelineContext);
//            BusinessUtil.writeFileToResponse(pipelineContext, response);
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error(e);
//        }
    }

    /**
     * 微信用户端上传图片
     * @param res
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/common/storage/upload")
    public
    @ResponseBody
    Response fileStorage(HttpServletResponse res) {
        logger.info("start upload............");
        PipelineContext pipelineContext = buildPipelineContent();
//        try {
//            HttpServletRequest request = pipelineContext.getRequest().getHttpServletRequest();
//
//            if (ServletFileUpload.isMultipartContent(request)) {
//                FileItemFactory factory = new DiskFileItemFactory();
//                ServletFileUpload upload = new ServletFileUpload(factory);
//                List items = upload.parseRequest(request);
//                Iterator iter = items.iterator();
//
//                boolean isImg= false;//后台校验，是否是图片
//                for (Object o : items){
//                    FileItem item = (FileItem) o;
//                    if (item.isFormField()) { // 如果是普通表单项目，显示表单内容
//
//                    }else{//非普通表单数据
//                        String imgName = item.getName();
//                        String strExtension = imgName.substring(imgName.lastIndexOf('.') + 1);
//                        if(StringUtil.equals(strExtension,"jpg") || StringUtil.equals(strExtension,"jpeg") || StringUtil.equals(strExtension,"gif")||StringUtil.equals(strExtension,"png")||StringUtil.equals(strExtension,"bmp")){
//                            isImg = true;//表明上传的是图片
//                        }
//                    }
//                }
//                //如果为false，则说明没有上传图片
//                if(!isImg){
//                    throw new ManagerException("请选择有效图片！");
//                }
//
//                //保存路径
//                String storagePath = weixinStorageManager.getApplicationStoreDataPath();
//                while (iter.hasNext()) {
//                    FileItem item = (FileItem) iter.next();
//                    if (!item.isFormField() && !item.getName().equals("") && item.getName() != null) {
//                        //构建storage对象
//                        Storage storage = StorageUtil.buildStorageFromRequest(item);
//                        //获取上传文件的buffer
//                        byte[] buffer = item.get();
//                        //设置buffer对象里面的文件大小
//                        storage.setFileLength(DataUtil.getString(buffer.length, null));
//                        //store file
//                        JSONObject json = JSONObject.fromObject(storage.getStoreName());
//                        StorageUtil.writeToStorage(json.getString("storeName").toString(),storagePath,buffer);
//
//                        logger.info("[fileStorage][request]"+"storage="+JSONObject.fromObject(storage).toString());
//                        int createId=weixinStorageManager.addBean(storage);
//                        logger.info("[fileStorage][response]"+"createId="+createId);
//                        pipelineContext.getResponse().addKey("attachment",storage.getStoreName());
//                    }
//                }
//            }
//        } catch (Exception e) {
//            //error code -500
//            _error(e, pipelineContext);
//            e.printStackTrace();
//        }
        return pipelineContext.getResponse();
    }

    /**
     * 上传文件
     * @param res
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/filesUpload")
    public
    @ResponseBody
    Response filesUpload(HttpServletResponse res) {
        logger.info("start upload............");
        PipelineContext pipelineContext = buildPipelineContent();
//        try {
//            HttpServletRequest request = pipelineContext.getRequest().getHttpServletRequest();
//
//            if (ServletFileUpload.isMultipartContent(request)) {
//                FileItemFactory factory = new DiskFileItemFactory();
//                ServletFileUpload upload = new ServletFileUpload(factory);
//                List items = upload.parseRequest(request);
//                Iterator iter = items.iterator();
//
//                boolean isImg= false;//后台校验，是否是图片
//                for (Object o : items){
//                    FileItem item = (FileItem) o;
//                    if (item.isFormField()) { // 如果是普通表单项目，显示表单内容
//
//                    }else{//非普通表单数据
//                        String imgName = item.getName();
//                        String strExtension = imgName.substring(imgName.lastIndexOf('.') + 1);
//                    }
//                }
//                //保存路径
//                String storagePath = weixinStorageManager.getApplicationStoreDataPath();
//                while (iter.hasNext()) {
//                    FileItem item = (FileItem) iter.next();
//                    if (!item.isFormField() && !item.getName().equals("") && item.getName() != null) {
//                        //构建storage对象
//                        Storage storage = StorageUtil.buildStorageFromRequest(item);
//                        //获取上传文件的buffer
//                        byte[] buffer = item.get();
//                        //设置buffer对象里面的文件大小
//                        storage.setFileLength(DataUtil.getString(buffer.length, null));
//                        //store file
//                        JSONObject json = JSONObject.fromObject(storage.getStoreName());
//                        StorageUtil.writeToStorage(json.getString("storeName").toString(),storagePath,buffer);
//
//                        logger.info("[fileStorage][request]"+"storage="+JSONObject.fromObject(storage).toString());
//                        int createId=weixinStorageManager.addBean(storage);
//                        logger.info("[fileStorage][response]"+"createId="+createId);
//                        pipelineContext.getResponse().addKey("attachment",storage.getStoreName());
//                    }
//                }
//            }
//        } catch (Exception e) {
//            //error code -500
//            _error(e, pipelineContext);
//            e.printStackTrace();
//        }
        return pipelineContext.getResponse();
    }
}
