package com.chinaredstar.commonBiz.util;

import com.chinaredstar.commonBiz.bean.RedstarCommunity;
import com.xiwa.base.util.StringUtil;

/**
 * 根据社区拼接业主地址
 * Created by bingcheng on 2015/7/1.
 */
public class AddressUtil {

    public static String getAddress(RedstarCommunity community,String graden,String building,String unit,String houseNumber){
        StringBuilder sb = new StringBuilder();
        if(StringUtil.isValid(community.getProvince())){
            sb.append(community.getProvince()+" ");
        }
        if(StringUtil.isValid(community.getCity())){
            sb.append(community.getCity()+" ");
        }
        if(StringUtil.isValid(community.getArea())){
            sb.append(community.getArea()+" ");
        }
        if(StringUtil.isValid(community.getAddress())){
            sb.append(community.getAddress()+" ");
        }else{
            sb.append("xx小区 ");
        }
        if(StringUtil.isValid(graden)){
            if(graden.contains("苑")){
                sb.append(graden+" ");
            }else {
                sb.append(graden + "苑 ");
            }
        }
        if(StringUtil.isValid(building)){
            if(building.contains("栋")){
                sb.append(building+" ");
            }else {
                sb.append(building + "栋 ");
            }
        }
        if(StringUtil.isValid(unit)){
            if(unit.contains("单元")){
                sb.append(unit+" ");
            }else {
                sb.append(unit + "单元 ");
            }
        }
        if(StringUtil.isValid(houseNumber)){
            if(houseNumber.contains("室")){
                sb.append(houseNumber);
            }else {
                sb.append(houseNumber + "室");
            }
        }
        return sb.toString();
    }
}
