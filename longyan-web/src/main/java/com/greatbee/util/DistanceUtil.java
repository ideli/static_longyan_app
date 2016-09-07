package com.greatbee.util;

/**
 * Created by lenovo on 2016/7/8.
 */
public class DistanceUtil {


    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    public static double getDistance(double lat1, double lng1, double lat2, double lng2){
        double EARTH_RADIUS = 6378.137;//地球半径
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
                Math.cos(radLat1) * Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000)/10000;
        return s;
    }

    public static double getDistance2(double LonA, double LatA, double LonB, double LatB)
    {
        // 东西经，南北纬处理，只在国内可以不处理(假设都是北半球，南半球只有澳洲具有应用意义)
        double MLonA = LonA;
        double MLatA = LatA;
        double MLonB = LonB;
        double MLatB = LatB;
        // 地球半径（千米）
        double R = 6378.137;
        double C = Math.sin(rad(LatA)) * Math.sin(rad(LatB)) + Math.cos(rad(LatA)) * Math.cos(rad(LatB)) * Math.cos(rad(MLonA - MLonB));
        return (R * Math.acos(C));
    }





    public  static  void  main(String[] args){
        //121.391948,31.243075
        double la1 = 31.243075;
        double lg1=121.391948;

        //121.393226,31.242721
        double la2 = 31.242721;
        double lg2=121.393226;

        System.out.println(getDistance(31.0,121.0,31.0,121.08));

        System.out.println(getDistance2(121.0,31.0,121.08,31.0));

    }

}
