package com.lanchui.commonBiz.util;

import com.lanchui.commonBiz.bean.RedstarCommunity;
import com.xiwa.base.util.DataUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 序列号生成工具类
 * Created by usagizhang on 15/10/22.
 */
public class SerialNumberUtil {
    public static String buildUserOrderSN(RedstarCommunity community) {
        StringBuilder seriralNum = new StringBuilder();
        if(community==null){
            seriralNum.append("User-");
        }else{
            seriralNum.append(community.getId()+"-");
        }
        String tmpDate = (new SimpleDateFormat("yyyyMMdd-HHmmss-SSS")).format(new Date(System.currentTimeMillis()));
        seriralNum.append(tmpDate);
        return seriralNum.toString();
    }

    /**
     * 生成  num 位的随机数
     * @param num
     * @return
     */
    public static String buildRandomNUm(int num){
        Random random = new Random();
        if(num<=0){
            num = 6;
        }
        StringBuilder sb = new StringBuilder("1");
        for (int i=0;i<num-1;i++){
            sb.append("0");
        }
        int numTmp = DataUtil.getInt(sb.toString(),100000);
        int result = (int)((Math.random()*9+1)*numTmp);
        return DataUtil.getString(result,null);
    }
}
