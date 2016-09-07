package com.lanchui.commonBiz.util;

import com.lanchui.commonBiz.bean.RedstarCommunity;

import java.util.ArrayList;
import java.util.List;

/**
 * 社区录入率和入住率计算工具
 * Created by LeiYun on 2016/7/5.
 */
public class InputRateAndOccupanyRateUtil {

    public static List<RedstarCommunity> computeValue(List<RedstarCommunity> resultList){

        List<RedstarCommunity> dataList = new ArrayList<RedstarCommunity>();

        //录入率和入住率
        for (RedstarCommunity community : resultList) {
            double occupanyRate;
            double inputRate;

          //  RedstarCommunity r = (RedstarCommunity) community;

            //计算入住率
            if (community.getAlreadyCheckAmount() != null && community.getRoomMount() != null && community.getRoomMount() > 0) {
                occupanyRate = DoubleUtil.div(community.getAlreadyCheckAmount(), community.getRoomMount(), 2);
                community.setOccupanyRate(RateUtil.getDoubleValue(occupanyRate * 100));
            } else {
                community.setOccupanyRate(0.0);
            }
            //计算录入率
            if (community.getAlreadyInputAmount() != null && community.getRoomMount() != null && community.getRoomMount() > 0) {
                inputRate = DoubleUtil.div(community.getAlreadyInputAmount(), community.getRoomMount(), 2);
                community.setInputRate(RateUtil.getDoubleValue(inputRate * 100));
            } else {
                community.setInputRate(0.0);
            }

            dataList.add(community);
        }

        return dataList;


    }



}
