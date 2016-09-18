package com.greatbee.util;

import com.xiwa.base.pipeline.PipelineContext;
import com.xiwa.base.pipeline.PipelineException;

/**
 * Created by Administrator on 2015/4/19.
 */
//@Component("loadFile")
public class LoadFile extends BasePipeline {

//    @Autowired
//    private StorageManager weixinStorageManager;

    /**
     * 服务器端提取文件
     * @param context
     * @throws com.xiwa.base.pipeline.PipelineException
     */
    @Override
    public void execute(PipelineContext context) throws PipelineException {

//        String storageName = context.getRequest().getString(Request_Store_Name);
//        String serialNumber = context.getRequest().getString("serialNumber");
//        String fileStorageType = context.getRequest().getString(Request_File_Storage_Type);
//        if (StringUtil.isInvalid(storageName) && StringUtil.isInvalid(serialNumber)) {
//            throw new PipelineException("storageName or serialNumber is null");
//        }
//        String storagePath = weixinStorageManager.getApplicationStoreDataPath();
//        if (StringUtil.isValid(fileStorageType) && fileStorageType.equalsIgnoreCase(FileStorageType.Temp.getType())) {
//            //使用临时文件路径
//            storagePath = weixinStorageManager.getApplicationTempDataPath();
//        }
//        storagePath = storagePath + "/";
//        if (StringUtil.isValid(storageName)) {
//            //拼装完整的文件路径
//            storagePath = storagePath + storageName;
//        } else if (StringUtil.isValid(serialNumber)) {
//            //通过序列号查询文件的数据
//            try {
//                List<Storage> storageList = weixinStorageManager.getBeanListByColumn("serialNumber", serialNumber);
//                if (CollectionUtil.isInvalid(storageList)) {
//                    throw new PipelineException("没有查询到存储数据,serialNumber=" + serialNumber);
//                } else if (StringUtil.isValid(storageList.get(0).getStoreName())) {
//                    //把查询出来的storageName拼装上去
//                    context.getRequest().addRequestData(Request_Store_Name, storageList.get(0).getStoreName());
//                    storagePath = storagePath + storageList.get(0).getStoreName();
//                } else {
//                    throw new PipelineException("没有查询到存储的storageName,id=" + storageList.get(0).getId() + ",serialNumber=" + serialNumber);
//                }
//            } catch (ManagerException e) {
//                logger.error(e);
//                throw new PipelineException(e.getMessage());
//            }
//        }
//        context.getRequest().addRequestData(Request_FilePath,storagePath);
    }
}
