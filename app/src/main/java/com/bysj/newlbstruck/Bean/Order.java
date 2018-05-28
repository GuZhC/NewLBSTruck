package com.bysj.newlbstruck.Bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by liuchuan on 2018/5/28.
 */

public class Order extends BmobObject {
    private String ArrivalTime;
    private String StartPointLng;
    private String StartPointLat;
    private String Pathways;
    private String HopeGoodsClass;

    public String getArrivalTime() {
        return ArrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        ArrivalTime = arrivalTime;
    }

    public String getStartPointLng() {
        return StartPointLng;
    }

    public void setStartPointLng(String startPointLng) {
        StartPointLng = startPointLng;
    }

    public String getStartPointLat() {
        return StartPointLat;
    }

    public void setStartPointLat(String startPointLat) {
        StartPointLat = startPointLat;
    }

    public String getPathways() {
        return Pathways;
    }

    public void setPathways(String pathways) {
        Pathways = pathways;
    }

    public String getHopeGoodsClass() {
        return HopeGoodsClass;
    }

    public void setHopeGoodsClass(String hopeGoodsClass) {
        HopeGoodsClass = hopeGoodsClass;
    }
}
