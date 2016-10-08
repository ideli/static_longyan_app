package com.chinaredstar.longyan.util;

import com.chinaredstar.commonBiz.bean.RedstarCommunityUpdateLog;
import com.chinaredstar.commonBiz.bean.RedstarDevelopers;
import com.chinaredstar.commonBiz.manager.RedstarCommonManager;
import com.chinaredstar.longyan.exception.FormException;
import com.xiwa.base.bean.Request;
import com.xiwa.base.bean.search.ext.TextSearch;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.util.StringUtil;
import org.springframework.util.CollectionUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


/**
 * 小区更新表单工具类
 * Created by usagizhang on 15-11-5.
 */
public class CommunityUpdateLogFormUtil {

    /**
     * 设置地址
     *
     * @param request
     * @param community
     * @throws FormException
     */
    public static void setAddress(Request request, RedstarCommunityUpdateLog community) throws FormException {
        String address = request.getString("address");
        if (StringUtil.isInvalid(address)) {
            throw new FormException("请输入小区详细地址");
        }
        community.setAddress(address);
    }

    /**
     * 设置小区别称
     *
     * @param request
     * @param community
     * @throws FormException
     */
    public static void setShortName(Request request, RedstarCommunityUpdateLog community) throws FormException {
        String shortName = request.getString("shortName");
        if (StringUtil.isValid(shortName)) {
            community.setShortName(shortName);
        }
    }

    /**
     * 设置总户数
     *
     * @param request
     * @param community
     * @throws FormException
     */
    public static void setRoomMount(Request request, RedstarCommunityUpdateLog community) throws FormException {
        Integer roomMount = request.getInt("roomMount");
        if (roomMount == null || roomMount < 1) {
            throw new FormException("请输入总户数");
        }
        community.setRoomMount(roomMount);
    }

    public static void setBuildingAmount(Request request, RedstarCommunityUpdateLog community) throws FormException {
        Integer buildingAmount = request.getInt("buildingAmount");
        if (buildingAmount == null || buildingAmount < 1) {
            throw new FormException("请输入总幢数");
        }
        community.setBuildingAmount(buildingAmount);
    }

    public static void setAlreadyCheckAmount(Request request, RedstarCommunityUpdateLog community) throws FormException {
        Integer checkMemberRate = request.getInt("checkMemberRate");
        if (checkMemberRate != null) {
            if (checkMemberRate > 100) {
                throw new FormException("入住率不能超过100%");
            }
            //已入住
            double _thisValue = checkMemberRate * community.getRoomMount() / 100;
            community.setAlreadyCheckAmount(RateUtil.getIntegerValue(_thisValue));
        }
    }

    public static void setPriceSection(Request request, RedstarCommunityUpdateLog community) throws FormException {
        Integer priceSection = request.getInt("priceSection");
        if (priceSection == null || priceSection < 1) {
            throw new FormException("房屋均价没有填写");
        }
        community.setPriceSection(priceSection);
    }

    public static void setConstructionTypes(Request request, RedstarCommunityUpdateLog community) throws FormException {
        String constructionTypes = request.getString("constructionTypes");
        if (StringUtil.isInvalid(constructionTypes)) {
            throw new FormException("建筑类型没有填写");
        }
        community.setConstructionTypes(constructionTypes);
    }

    public static void setRenovations(Request request, RedstarCommunityUpdateLog community) throws FormException {
        String renovations = request.getString("renovations");
        if (StringUtil.isInvalid(renovations)) {
            throw new FormException("交房装修没有填写");
        }
        community.setRenovations(renovations);
    }

    public static void setDeliveryTime(Request request, RedstarCommunityUpdateLog community) throws FormException {
        String deliveryTime = request.getString("deliveryTime");
        if (StringUtil.isInvalid(deliveryTime)) {
            throw new FormException("交房时间没有填写");
        }
        community.setDeliveryTime(deliveryTime);
    }

    public static void setDevelopers(Request request, RedstarCommunityUpdateLog community, RedstarCommonManager redstarCommonManager) throws FormException, ManagerException {

        String developers = request.getString("developers");
        if (StringUtil.isInvalid(developers)) {
            throw new FormException("开发商没有填写");
        }
        TextSearch developerSearch = new TextSearch("name");
        developerSearch.setSearchValue(developers);
        if (CollectionUtils.isEmpty(redstarCommonManager.getDataList(RedstarDevelopers.class, developerSearch))) {
            RedstarDevelopers redstarDevelopers = new RedstarDevelopers();
            redstarDevelopers.setName(developers);
            redstarCommonManager.addData(redstarDevelopers);
        }
        community.setDevelopers(developers);
    }

    public static void setPropertyName(Request request, RedstarCommunityUpdateLog community, RedstarCommonManager redstarCommonManager) throws FormException, ManagerException {
        String propertyName = request.getString("propertyName");
//        if (StringUtil.isInvalid(propertyName)) {
//            throw new FormException("物业公司没有填写");
//        }
//        TextSearch propertyNameSearch = new TextSearch("name");
//        propertyNameSearch.setSearchValue(propertyName);
//        if (CollectionUtils.isEmpty(redstarCommonManager.getDataList(RedstarProperty.class, propertyNameSearch))) {
//            RedstarProperty redstarPropertys = new RedstarProperty();
//            redstarPropertys.setName(propertyName);
//            redstarCommonManager.addData(redstarPropertys);
//        }
        community.setPropertyName(propertyName);
    }

    public static void setHotline(Request request, RedstarCommunityUpdateLog community) throws FormException {
        String hotline = request.getString("hotline");
        if (StringUtil.isValid(hotline)) {
            community.setHotline(hotline);
        }
    }

    /**
     * 设置经度
     *
     * @param request
     * @param community
     * @throws FormException
     */
    public static void setLongitude(Request request, RedstarCommunityUpdateLog community) throws FormException {
        // 非必填字段不验证是否为空
//        String longitude = request.getString("longitude");
        Double longitude = request.getDouble("longitude");
        if (longitude > 0) {
            community.setLongitude(longitude);
        }
    }


    /**
     * 设置纬度
     *
     * @param request
     * @param community
     * @throws FormException
     */
    public static void setLatitude(Request request, RedstarCommunityUpdateLog community) throws FormException {
        // 非必填字段不验证是否为空
        Double latitude = request.getDouble("latitude");
        if (latitude > 0) {
            community.setLatitude(latitude);
        }
    }


    public static Map<String, String> transBean2Map(Object obj) {

        if (obj == null) {
            return null;
        }
        Map<String, String> map = new HashMap<String, String>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    String value = String.valueOf(getter.invoke(obj));

                    map.put(key, value);
                }

            }
        } catch (Exception e) {
            System.out.println("transBean2Map Error " + e);
        }
        return map;
    }

}
