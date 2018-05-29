package com.bysj.newlbstruck.Bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by liuchuan on 2018/5/28.
 */

public class UserOrder  extends BmobObject implements Serializable {
    private String CategoryGoods;
    private String UserId;
    private String StartPointLng;
    private String StartPointLat;
    private String ReceiptTime;
    private String ReceiptPlace;
    private String PackingForm;
    private String PackagingSize;
    private String Number;
    private String FreightLimit;
    private String EndPointLng;
    private String EndPointLat;
    private String DeliveryTime;
    private String DeliveryPlace;
    private String weight;

    public String getCategoryGoods() {
        return CategoryGoods;
    }

    public void setCategoryGoods(String categoryGoods) {
        CategoryGoods = categoryGoods;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
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

    public String getReceiptTime() {
        return ReceiptTime;
    }

    public void setReceiptTime(String receiptTime) {
        ReceiptTime = receiptTime;
    }

    public String getReceiptPlace() {
        return ReceiptPlace;
    }

    public void setReceiptPlace(String receiptPlace) {
        ReceiptPlace = receiptPlace;
    }

    public String getPackingForm() {
        return PackingForm;
    }

    public void setPackingForm(String packingForm) {
        PackingForm = packingForm;
    }

    public String getPackagingSize() {
        return PackagingSize;
    }

    public void setPackagingSize(String packagingSize) {
        PackagingSize = packagingSize;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getFreightLimit() {
        return FreightLimit;
    }

    public void setFreightLimit(String freightLimit) {
        FreightLimit = freightLimit;
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

    public String getDeliveryTime() {
        return DeliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        DeliveryTime = deliveryTime;
    }

    public String getDeliveryPlace() {
        return DeliveryPlace;
    }

    public void setDeliveryPlace(String deliveryPlace) {
        DeliveryPlace = deliveryPlace;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
