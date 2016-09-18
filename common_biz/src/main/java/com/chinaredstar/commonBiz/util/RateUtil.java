package com.chinaredstar.commonBiz.util;

import java.math.BigDecimal;

/**
 * Created by niu on 2016/5/6.
 */
public class RateUtil {

    //四舍五入不保留
    public static  Double getDoubleValue(Double _thisValue){
       return new BigDecimal(_thisValue).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    //四舍五入不保留
    public static  Integer getIntegerValue(Double _thisValue){
        return new BigDecimal(_thisValue).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
    }

    public  static  void  main (String[] args){
        System.out.println(getDoubleValue(55.0));
        System.out.println(getDoubleValue(55.54));
        double _thisValue = 100*2/100;
        System.out.println(_thisValue);
    }
}
