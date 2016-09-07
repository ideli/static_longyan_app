package com.greatbee.lanchui.task;
import com.lanchui.commonBiz.bean.RedstarStore;
import com.lanchui.commonBiz.bean.lg.ShopInfo;
import com.lanchui.commonBiz.manager.DispatchDriver;
import com.lanchui.commonBiz.manager.RedstarCommonManager;
import com.lanchui.commonBiz.manager.RedstarStoreManager;
import com.lanchui.commonBiz.manager.lg.ShopInfoManager;
import com.xiwa.base.manager.ManagerException;

import com.xiwa.base.util.RandomGUIDUtil;
import com.xiwa.base.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lenovo on 2016/7/6.
 */
@Component(value = "storeInfoSync")
public class StoreSyncTask {

    protected static Logger logger = Logger.getLogger(StoreSyncTask.class);
    @Autowired
    private RedstarStoreManager redstarStoreManager;

    @Autowired
    private ShopInfoManager shopInfoManager;

    @Autowired
    private RedstarCommonManager redstarCommonManager;

    @Transactional
    public void execute() throws ManagerException {
        logger.info("龙国店铺数据同步 ---start" + new Date());
        List<ShopInfo> shopInfos = shopInfoManager.getShopInfo();
        RedstarStore redstarStore;
        List<RedstarStore> addList = new ArrayList<RedstarStore>();
        List<Object> updateList = new ArrayList<Object>();
        List<Integer> removeList = new ArrayList<Integer>();
        List<RedstarStore> stores = redstarStoreManager.getBeanList();
        boolean hasStore;
        int j = 0;
        for (ShopInfo shopInfo : shopInfos) {
            hasStore = false;
            for (RedstarStore store : stores) {
                if (store.getStoreCode().equals(shopInfo.getId().toString())) {
                    if (shopInfo.getIsDel() == 0) {
                        redstarStore = store;
                        redstarStore.setStoreCode(shopInfo.getId().toString());
                        redstarStore.setStoreType("none");
                        redstarStore.setStoreName(shopInfo.getBrandName());
                        redstarStore.setBelongMarket(shopInfo.getMarketId().toString());
                        redstarStore.setBelongMarketCode(shopInfo.getSeriesName());
                        redstarStore.setBelongMarket(shopInfo.getMarketName());
                        redstarStore.setBrandName(shopInfo.getBrandName());
                        redstarStore.setOpenTime(shopInfo.getSalesStartTime());
                        redstarStore.setCloseTime(shopInfo.getSalesEndTime());
                        redstarStore.setBelongMarketAddress(shopInfo.getShopTel());
                        if(!StringUtil.isValid(redstarStore.getQrCode())){
                            redstarStore.setQrCode(RandomGUIDUtil.getGUID(16));
                        }
                        updateList.add(redstarStore);
                        hasStore = true;
                        break;
                    } else {    //remove
                        redstarStore = store;
                        removeList.add(redstarStore.getId());
                        hasStore = true;
                        break;
                    }
                }
            }
            if (!hasStore && shopInfo.getIsDel() == 0) { //insert
                redstarStore = new RedstarStore();
                redstarStore.setStoreCode(shopInfo.getId().toString());
                redstarStore.setStoreType("none");
                redstarStore.setStoreName(shopInfo.getBrandName());
                redstarStore.setBelongMarket(shopInfo.getMarketId().toString());
                redstarStore.setBelongMarketCode(shopInfo.getSeriesName());
                redstarStore.setBelongMarket(shopInfo.getMarketName());
                redstarStore.setBrandName(shopInfo.getBrandName());
                redstarStore.setOpenTime(shopInfo.getSalesStartTime());
                redstarStore.setCloseTime(shopInfo.getSalesEndTime());
                redstarStore.setBelongMarketAddress(shopInfo.getShopTel());
                if(!StringUtil.isValid(redstarStore.getQrCode())){
                    redstarStore.setQrCode(RandomGUIDUtil.getGUID(16));
                }
                addList.add(redstarStore);
            }
        }
        redstarStoreManager.addBeans(addList);
        redstarCommonManager.batchUpdateIdentified(RedstarStore.class, updateList);
        if (removeList.size() > 0) {
            int[] ids = new int[removeList.size()];
            for (int i = 0; i < removeList.size(); i++) {
                ids[i] = removeList.get(i);
            }
            redstarStoreManager.removeBeans(ids);
        }
        logger.info("update :" + updateList.size() + "---addList :" + addList.size() + "---removeList :" + removeList.size());
        logger.info("龙果数据同步--end"  + new Date());

    }
}
