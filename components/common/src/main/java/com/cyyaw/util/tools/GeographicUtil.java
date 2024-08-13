package com.cyyaw.util.tools;

/**
 * 地理位置
 */
public class GeographicUtil {

    //赤道半径 6378.137Km ；两极半径 6359.752Km；平均半径 6371.012Km ；赤道周长 40075.7Km
    private static double EARTH_RADIUS = 6378.137;


    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }


    /**
     * 通过经纬度获取距离(单位：米)
     * @param lon1 纬度1
     * @param lat1 经度1
     * @param lon2 纬度2
     * @param lat2 经度2
     * @return
     */
    public static double getDistance(double lon1, double lat1, double lon2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lon1) - rad(lon2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        return s * 1000;//单位米
    }


}
