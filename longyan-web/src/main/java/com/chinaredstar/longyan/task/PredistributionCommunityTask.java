package com.chinaredstar.longyan.task;

import com.chinaredstar.commonBiz.bean.RedstarTaskLog;
import com.chinaredstar.commonBiz.manager.RedstarCommonManager;
import com.chinaredstar.commonBiz.manager.RedstarShoppingMallManager;
import com.chinaredstar.commonBiz.manager.RedstarTaskLogManager;
import com.chinaredstar.longyan.bean.constant.LanchuiConstant;
import com.xiwa.base.manager.ManagerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by StevenDong on 2016/10/08.
 */
@Component(value = "predistributionCommunityTask")
public class PredistributionCommunityTask implements LanchuiConstant {


    @Autowired
    private RedstarCommonManager redstarCommonManager;

    @Autowired
    private RedstarShoppingMallManager redstarShoppingMallManager;

    @Autowired
    private RedstarTaskLogManager redstarTaskLogManager;

    /**
     * 对小区所属商场ID（ownerMallId）为0的小区进行自动预分配
     * 判定规则：（城的概念：直辖市，县级市，地级市平级）
     * 1、一城一店：当前城市全部归属商场
     * 2、一城多店：根据距离远近判断
     * 3、一城无店：小区暂放系统
     */

    public void execute() throws ManagerException {

        Long startTime = System.currentTimeMillis();
        RedstarTaskLog redstarTaskLog = new RedstarTaskLog();
        redstarTaskLog.setTaskClassName(this.getClass().getSimpleName());
        redstarTaskLog.setLogDatetime(new Date());
        redstarTaskLog.setRemark("小区自动预分配");
        redstarTaskLog.setAction(taskLogStart);
        redstarTaskLogManager.addBean(redstarTaskLog);

        StringBuffer sbQuerySqlMallInCity = new StringBuffer();
        sbQuerySqlMallInCity.append("SELECT mallTb.cityName,count(mallTb.cityName) AS mallCount ");
        sbQuerySqlMallInCity.append("FROM (select CASE WHEN locate('市',area)>0 THEN area ELSE city END AS cityName ");
        sbQuerySqlMallInCity.append("FROM xiwa_redstar_shopping_mall WHERE mallType = ?) AS mallTb ");
        sbQuerySqlMallInCity.append("GROUP BY cityName");
        String querySQLMallInCity = sbQuerySqlMallInCity.toString();

        List<Object> paramsListQueryMallInCity = new ArrayList<Object>();
        paramsListQueryMallInCity.add("real");

        // 查询出每个城市中全部商场数目
        List lsMallInCity = redstarShoppingMallManager.excuteBySql(querySQLMallInCity, paramsListQueryMallInCity);

        // 一城一店的商场信息查询SQL生成
        StringBuffer sbQuerySqlMallInfo = new StringBuffer();
        sbQuerySqlMallInfo.append("select id, name, lng, lat FROM xiwa_redstar_shopping_mall WHERE mallType = ? AND (city = ? OR area = ?)");
        String QuerySQLMallInfo = sbQuerySqlMallInfo.toString();


        for (int i = 0; i < lsMallInCity.size(); i++) {
            List<Object> paramsListUpdate = new ArrayList<Object>();
            Object[] objMallInCity = (Object[]) lsMallInCity.get(i);
            if (Integer.valueOf(objMallInCity[1].toString()) == 1) { //城市中全部商场数目为1（一城一店）
                // 依据城市反查商场信息
                List<Object> paramsListQueryMallInfo = new ArrayList<Object>();
                paramsListQueryMallInfo.add("real");
                paramsListQueryMallInfo.add(objMallInCity[0]); // 城市名
                paramsListQueryMallInfo.add(objMallInCity[0]);
                List lsMallInfo = redstarShoppingMallManager.excuteBySql(QuerySQLMallInfo, paramsListQueryMallInfo);

                Object[] objMallInfo = (Object[]) lsMallInfo.get(0);

                // 小区预分配商场SQL生成
                StringBuffer sbUpdateSql = new StringBuffer();
                sbUpdateSql.append("update xiwa_redstar_community set ownerMallId='");
                sbUpdateSql.append(objMallInfo[0]);// 商场ID
                sbUpdateSql.append("' ,ownerMallName='");
                sbUpdateSql.append(objMallInfo[1]);// 商场名
                sbUpdateSql.append("' WHERE city ='");
                sbUpdateSql.append(objMallInCity[0]);// 城市名
                sbUpdateSql.append("' OR area ='");
                sbUpdateSql.append(objMallInCity[0]);// 城市名
                sbUpdateSql.append("'");
                String updateSQL = sbUpdateSql.toString();

                // 小区预分配商场SQL执行
                redstarShoppingMallManager.excuteBySql(updateSQL);

            } else if (Integer.valueOf(objMallInCity[1].toString()) > 1) { // 一城多店

                // 依据城市反查商场信息
                List<Object> paramsListQueryMallInfo = new ArrayList<Object>();
                paramsListQueryMallInfo.add("real");
                paramsListQueryMallInfo.add(objMallInCity[0]); // 城市名
                paramsListQueryMallInfo.add(objMallInCity[0]); // 城市名
                List lsMallInfo = redstarShoppingMallManager.excuteBySql(QuerySQLMallInfo,paramsListQueryMallInfo);

                // 计算每个小区与商场距离
                StringBuffer sbQuerySqlDistance = new StringBuffer();

                sbQuerySqlDistance.append("Select c.id, ");

                for (int j = 0; j < lsMallInfo.size(); j++) {
                    Object[] objMallInfo = (Object[]) lsMallInfo.get(j);
                    sbQuerySqlDistance.append("round(6378.138 * 2 * asin(sqrt( pow(sin((c.latitude * pi() / 180 - ");
                    sbQuerySqlDistance.append(objMallInfo[3]);
                    sbQuerySqlDistance.append("*pi() / 180) / 2),2) + cos(c.latitude * pi() / 180) * cos(");
                    sbQuerySqlDistance.append(objMallInfo[3]);
                    sbQuerySqlDistance.append("*pi() / 180) * pow(  sin((c.longitude * pi() / 180 - ");
                    sbQuerySqlDistance.append(objMallInfo[2]);
                    sbQuerySqlDistance.append("*pi()/180) / 2),2))) * 1000) AS distance"+j+",");
                }

                sbQuerySqlDistance.deleteCharAt(sbQuerySqlDistance.length()-1); // 删除最后一位逗号
                sbQuerySqlDistance.append(" FROM xiwa_redstar_community c WHERE c.longitude>0 AND c.latitude>0 and c.city = ?  ");
                String QuerySqlDistance = sbQuerySqlDistance.toString();

                List<Object> paramsListQueryDistance = new ArrayList<Object>();
                paramsListQueryDistance.add(objMallInCity[0]); // 城市名

                List lsDistance = redstarShoppingMallManager.excuteBySql(QuerySqlDistance,paramsListQueryDistance);

                for (int k = 0; k < lsDistance.size(); k++) {
                    Object[] objDistance = (Object[]) lsDistance.get(k);
                    int shortestDistanceMallIdx = 0;
                    Double min =  Double.valueOf(objDistance[1].toString());
                    for (int l =2; l < objDistance.length; l++) {
                        if(Double.valueOf(objDistance[l].toString()) < min) {  // 判断最小值
                            min = Double.valueOf(objDistance[l].toString());
                            shortestDistanceMallIdx = l-1; // 比lsMallInfo的index少一
                        }
                    }

                    Object[] objshortestDistance = (Object[])lsMallInfo.get(shortestDistanceMallIdx);
                        // 小区预分配商场SQL生成
                        StringBuffer sbUpdateSql = new StringBuffer();
                        sbUpdateSql.append("update xiwa_redstar_community set ownerMallId='");
                        sbUpdateSql.append(objshortestDistance[0]);// 商场ID
                        sbUpdateSql.append("' ,ownerMallName='");
                        sbUpdateSql.append(objshortestDistance[1]);// 商场名
                        sbUpdateSql.append("' WHERE id ='");
                        sbUpdateSql.append(objDistance[0]);// 小区ID
                        sbUpdateSql.append("'");
                        String updateSQL = sbUpdateSql.toString();

                        // 小区预分配商场SQL执行
                        redstarShoppingMallManager.excuteBySql(updateSQL);

                }
            }
        }
        //执行完成日志
        redstarTaskLog = new RedstarTaskLog();
        redstarTaskLog.setTaskClassName(this.getClass().getSimpleName());
        redstarTaskLog.setLogDatetime(new Date());
        redstarTaskLog.setRemark("小区自动预分配");
        redstarTaskLog.setAction(taskLogEnd);
        redstarTaskLog.setExecuteTime(System.currentTimeMillis() - startTime);
        redstarTaskLogManager.addBean(redstarTaskLog);
    }

}
