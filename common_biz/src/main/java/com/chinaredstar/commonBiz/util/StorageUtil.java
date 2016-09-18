package com.chinaredstar.commonBiz.util;


import com.chinaredstar.commonBiz.bean.Storage;
import com.chinaredstar.commonBiz.bean.constant.StorageConstant;
import com.xiwa.base.components.bean.constant.FileType;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.util.RandomGUIDUtil;
import com.xiwa.base.util.StringUtil;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItem;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

/**
 * Created by yueyq on 2015/3/27.
 */
public class StorageUtil implements StorageConstant {
    /**
     * 通过request对象构建storage对象
     *
     * @param item
     * @return
     */
    public static Storage buildStorageFromRequest(FileItem item) {
        Storage storage = new Storage();
        storage.setSerialNumber(RandomGUIDUtil.getRawGUID().replace("-", ""));
        String uploadFileName = item.getName();//带后缀的原始文件名
        String uploadDocName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1, uploadFileName.length());//原始文件名
        String fileType = uploadDocName.substring(uploadDocName.lastIndexOf(".") + 1, uploadDocName.length()).toLowerCase();
        FileType _ft = FileType.getFileType(fileType);
        if (_ft != null) {
            storage.setFileType(_ft.getType());//设置文件类型(后缀名)
            storage.setStoreName(_buildStorageNameJson(uploadFileName, storage.getSerialNumber() + "." + _ft.getType()));
//            storage.setStoreName(storage.getSerialNumber() + "." + _ft.getType());//设置存储的文件名(序列号+文件类型)

        } else {
//            storage.setStoreName(storage.getSerialNumber());
            storage.setStoreName(_buildStorageNameJson(uploadFileName, storage.getSerialNumber()));

        }
        storage.setSerialNumber(RandomGUIDUtil.getRawGUID().replace("-", ""));
        storage.setFileName(uploadFileName);
        storage.setCreateDate(new Date());
        return storage;
    }

    public static void writeToStorage(String name, String path, byte[] data) throws ManagerException {
        try {
            File f = new File(path);
            if (!f.exists()) {
                throw new ManagerException("该路径不存在");
            } else if (!path.endsWith("/")) {
                path = path + "/";
            }
            FileOutputStream outSTr = null;
            BufferedOutputStream Buff = null;
            outSTr = new FileOutputStream(new File(path + name));
            Buff = new BufferedOutputStream(outSTr);
            Buff.write(data);
            Buff.flush();
            Buff.close();
        } catch (Exception e) {
            throw new ManagerException(e.getMessage());
        }
    }

    /**
     * 构造storageJSON
     *
     * @param uploadFileName
     * @param storeName
     * @return
     */
    private static String _buildStorageNameJson(String uploadFileName, String storeName) {
        if (StringUtil.isValid(uploadFileName) && StringUtil.isValid(storeName)) {
            JSONObject uploadJson = new JSONObject();
            uploadJson.put(File_Name, uploadFileName);
            uploadJson.put(Storage_Name, storeName);
            return uploadJson.toString();
        } else {
            return null;
        }
    }
}
