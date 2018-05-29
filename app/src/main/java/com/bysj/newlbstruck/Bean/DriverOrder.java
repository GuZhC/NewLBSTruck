package com.bysj.newlbstruck.Bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by liuchuan on 2018/5/28.
 */

public class DriverOrder  extends BmobObject {
    private String ArrivalTime;
    private String StartPointLng;
    private String StartPointLat;
    private String Pathways;
    private String HopeGoodsClass;
    private String FreightLimit;
    private String FreeTonnage;
    private String EndPointLng;
    private String EndPointLat;
    private String DriverId;
    private String Destination;
    private String DepartureTime;
    private String DeparturePlace;
    private String CarSize;
    private String CarModel;
    private String TransportTonnage;
    private Integer state;
    private String UserId;
    private String UserOrderId;

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

    public String getFreightLimit() {
        return FreightLimit;
    }

    public void setFreightLimit(String freightLimit) {
        FreightLimit = freightLimit;
    }

    public String getFreeTonnage() {
        return FreeTonnage;
    }

    public void setFreeTonnage(String freeTonnage) {
        FreeTonnage = freeTonnage;
    }

    public String getEndPointLng() {
        return EndPointLng;
    }

    public void setEndPointLng(String endPointLng) {
        EndPointLng = endPointLng;
    }

    public String getEndPointLat() {
        return EndPointLat;
    }

    public void setEndPointLat(String endPointLat) {
        EndPointLat = endPointLat;
    }

    public String getDriverId() {
        return DriverId;
    }

    public void setDriverId(String driverId) {
        DriverId = driverId;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public String getDepartureTime() {
        return DepartureTime;
    }

    public void setDepartureTime(String departureTime) {
        DepartureTime = departureTime;
    }

    public String getDeparturePlace() {
        return DeparturePlace;
    }

    public void setDeparturePlace(String departurePlace) {
        DeparturePlace = departurePlace;
    }

    public String getCarSize() {
        return CarSize;
    }

    public void setCarSize(String carSize) {
        CarSize = carSize;
    }

    public String getCarModel() {
        return CarModel;
    }

    public void setCarModel(String carModel) {
        CarModel = carModel;
    }

    public String getTransportTonnage() {
        return TransportTonnage;
    }

    public void setTransportTonnage(String transportTonnage) {
        TransportTonnage = transportTonnage;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserOrderId() {
        return UserOrderId;
    }

    public void setUserOrderId(String userOrderId) {
        UserOrderId = userOrderId;
    }
}
