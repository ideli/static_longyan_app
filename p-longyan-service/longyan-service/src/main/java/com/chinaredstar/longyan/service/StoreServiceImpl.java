package com.chinaredstar.longyan.service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.chinaredstar.longyan.api.StoreService;
import com.chinaredstar.longyan.api.vo.Response;
import com.chinaredstar.longyan.common.IntegerUtil;
import com.chinaredstar.longyan.model.RedstarStore;
import com.chinaredstar.longyan.model.RedstarStoreFavorite;
import com.chinaredstar.longyan.repository.RedstarStoreFavoriteRepository;
import com.chinaredstar.longyan.repository.RedstarStoreRepository;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by mdc on 2016/7/29.
 */
@Service
public class StoreServiceImpl extends BasicService implements StoreService {
    protected static Logger logger = LoggerFactory.getLogger(StoreServiceImpl.class);

    @Autowired
    private RedstarStoreRepository redstarStoreRepository;

    @Autowired
    private RedstarStoreFavoriteRepository redstarStoreFavoriteRepository;

    protected final static String STORE_DEFAULT_TEL = "400-100-500";

    @Override
    @Transactional(readOnly = true)
    public Response getStoreAndReview(String qrCode, String userId, String dataSK,int page,int rows) {
        if (StringUtils.isEmpty(qrCode)) {
            errorResponse(-1001, "qrCode不能为空");
            return response;
        }
        List<RedstarStore> resultList = redstarStoreRepository.findByQrCode(qrCode);
        if (!StringUtils.isEmpty(userId)) {
            for (RedstarStore redstarStore : resultList) {
                List<RedstarStoreFavorite> redstarStoreFavorites = redstarStoreFavoriteRepository.findByUserIdAndStoreId(userId, redstarStore.getId());
                if (redstarStoreFavorites.size() > 0) {
                    redstarStore.setFavorite(true);
                }
            }
        }

        for (RedstarStore redstarStore : resultList) {
            //TODO 计算评分

        }
        successResponse("查询成功");
        response.addKey("resultList", resultList);
        logger.info("[Redstar_Store] response=" + new JSONObject(response.getDataMap()));
        return response;
    }

    @Override
    @Transactional
    public Response favoriteStore(String storeId, boolean favorite, String userId) {
        if (StringUtils.isEmpty(storeId) && !IntegerUtil.isInteger(storeId)) {
            errorResponse(-1001, "storeId不能为空且为整形数字");
            return response;
        }
        int storeIdInt = Integer.parseInt(storeId);
        if (StringUtils.isEmpty(userId)) {
            errorResponse(-1001, "userId不能为空");
            return response;
        }
        RedstarStore redstarStore = redstarStoreRepository.findOne(storeIdInt);
        if (redstarStore == null) {
            errorResponse(50201, "店铺信息未查询到请确认storeId");
            return response;
        }
        try {
            if (favorite) {//是否收藏
                RedstarStoreFavorite redstarStoreFavorite = new RedstarStoreFavorite();
                redstarStoreFavorite.setStoreId(storeIdInt);
                redstarStoreFavorite.setStoreName(redstarStore.getStoreName());
                redstarStoreFavorite.setBrandLogo(redstarStore.getBrandLogo());
                redstarStoreFavorite.setUserId(userId);
                redstarStoreFavorite.setCreateDate(new Date());
                redstarStoreFavoriteRepository.save(redstarStoreFavorite);
                successResponse("收藏成功");
            } else {
                List<RedstarStoreFavorite> redstarStoreFavorites = redstarStoreFavoriteRepository.findByUserIdAndStoreId(userId, storeIdInt);
                for (RedstarStoreFavorite redstarStoreFavorite : redstarStoreFavorites) {
                    redstarStoreFavoriteRepository.delete(redstarStoreFavorite);
                }
                successResponse("取消收藏成功");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error(ex.getMessage());
            errorResponse(EXCEPTION_CODE,"店铺收藏异常");
        }
        return response;
    }



}
