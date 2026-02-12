package com.kk.mumuchat.common.core.utils.measure;

/**
 * 距离测算工具类
 *
 * @author xueyi
 */
public class CalculationUtil {

    /**
     * 地球半径
     */
    private static final double EARTH_RADIUS = 6378138.0;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 判断一个点是否在圆形区域内
     *
     * @param circleLng 圆心经度
     * @param circleLat 圆心纬度
     * @param cordLng   坐标经度
     * @param cordLat   坐标纬度
     * @param radius    半径
     */
    public static boolean isInCircle(Double circleLng, Double circleLat, Double cordLng, Double cordLat, Double radius) {
        return getDistance(circleLng, circleLat, cordLng, cordLat) <= radius;
    }

    /**
     * 通过经纬度获取距离(单位：米)
     *
     * @param lng1 经度1
     * @param lat1 纬度1
     * @param lng2 经度2
     * @param lat2 纬度2
     * @return 距离
     */
    public static Double getDistance(Double lng1, Double lat1, Double lng2, Double lat2) {
        try {
            double radLat1 = rad(lat1);
            double radLat2 = rad(lat2);
            double a = radLat1 - radLat2;
            double b = rad(lng1) - rad(lng2);
            double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
                    Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
            s = s * EARTH_RADIUS;
            s = Math.round(s * 10000d) / 10000d;
            return s;
        } catch (Exception e) {
            return (double) -1;
        }
    }
}
