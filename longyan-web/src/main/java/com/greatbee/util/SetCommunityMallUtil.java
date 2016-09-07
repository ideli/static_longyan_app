package com.greatbee.util;

import com.greatbee.bean.constant.LanchuiConstant;
import com.lanchui.commonBiz.bean.RedstarCommunity;
import com.lanchui.commonBiz.bean.RedstarMallEmployee;
import com.lanchui.commonBiz.bean.RedstarShoppingMall;
import com.lanchui.commonBiz.manager.RedstarCommonManager;
import com.xiwa.base.bean.search.ext.IntSearch;
import com.xiwa.base.bean.search.ext.MultiSearchBean;
import com.xiwa.base.bean.search.ext.TextSearch;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.zeus.trinity.bean.Employee;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by niu on 2016/6/20.
 */
public class SetCommunityMallUtil implements LanchuiConstant {

    public static RedstarCommunity setCommunityMall(RedstarCommonManager redstarCommonManager, Employee employee,RedstarCommunity community,MultiSearchBean multiSearchBean) throws ManagerException {

        IntSearch employeeIdSearch = new IntSearch(Query_Column_EmployeeId);
        employeeIdSearch.setSearchValue(String.valueOf(employee.getId()));

        List<RedstarMallEmployee> meList = redstarCommonManager.getDataList(RedstarMallEmployee.class,employeeIdSearch);

        //有商场员工 mallId mallName
        if(!CollectionUtils.isEmpty(meList)){
            RedstarMallEmployee me = meList.get(0);
            RedstarShoppingMall mall = (RedstarShoppingMall) redstarCommonManager.getDataById(me.getShoppingMallId(),RedstarShoppingMall.class);

            if(Default_MallType.equals(mall.getMallType())){
                community.setOwnerMallId(me.getShoppingMallId());
                community.setOwnerMallName(me.getShoppingMallName());
                //ownerId,ownerName
                community.setOwnerId(employee.getId());
                community.setOwnerXingMing(employee.getXingMing());
                return  community;
            }
        }

        //无商场员工
        if(multiSearchBean==null){
                multiSearchBean = new MultiSearchBean();
                TextSearch provinceSearch = new TextSearch("provinceCode");
                provinceSearch.setSearchValue(community.getProvinceCode());
                multiSearchBean.addSearchBean(provinceSearch);

                TextSearch cityCodeSearch = new TextSearch("cityCode");
                cityCodeSearch.setSearchValue(community.getCityCode());
                multiSearchBean.addSearchBean(cityCodeSearch);

          }

          TextSearch typeSearch = new TextSearch(Query_Column_MallType);
          typeSearch.setSearchValue(Default_MallType);

          multiSearchBean.addSearchBean(typeSearch);

            //查询该城市的商场
          List<RedstarShoppingMall> mallList = redstarCommonManager.getDataList(RedstarShoppingMall.class,multiSearchBean);

            //该省市存在商场的情况
          if(!CollectionUtils.isEmpty(mallList)){
                if (mallList.size()==1){
                    //当前城市只有一个商场
                    RedstarShoppingMall mall = mallList.get(0);
                    community.setOwnerMallId(mall.getId());
                    community.setOwnerMallName(mall.getName());
                    return  community;
                }else{
                    //多个商场的情况 根据经纬度计算
                    //判断当前小区经纬度不为空
                    if (community.getLatitude()!=null&&community.getLatitude()!=0
                            &&community.getLongitude()!=null && community.getLongitude()!=null ){

                        String sql = "SELECT id,name,round(6378.138 * 2 * asin( sqrt( pow( sin( ( lat * pi() / 180-?* pi() / 180) / 2),2) + cos(lat * pi() / 180) * cos(?* pi() / 180) * pow(sin((lng*pi()/? * pi()/180 )/2),2))) * 1000 ) AS distance  FROM xiwa_redstar_shopping_mall WHERE provinceCode =?  and  cityCode=?  and mallType=real  ORDER BY distance";

                        List<Object> paramsList = new ArrayList<Object>();
                        paramsList.add(community.getLatitude());
                        paramsList.add(community.getLatitude());
                        paramsList.add(community.getLongitude());
                        paramsList.add(community.getProvinceCode());
                        paramsList.add(community.getCityCode());

                        List<Object[]> resultList = redstarCommonManager.excuteBySql(sql,paramsList);
                        if(!CollectionUtils.isEmpty(resultList)){
                            Object[] objArray = resultList.get(0);
                            if(objArray!=null && objArray.length>0){
                                community.setOwnerMallId(Integer.parseInt(String.valueOf(objArray[0])));
                                community.setOwnerMallName(String.valueOf(objArray[1]));
                            }
                        }

                    }
                }
          }

        return  community;
    }



}
