package com.bysj.newlbstruck.Bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * @author GuZhC
 * @create 2018/5/19
 * @Describe
 */
public class User extends BmobUser {
    boolean isDrive;
    BmobFile imageHead;
    String licenseNumber;
    String carInfo;


    public boolean isDrive() {
        return isDrive;
    }

    public void setDrive(boolean drive) {
        isDrive = drive;
    }

    public BmobFile getImageHead() {
        return imageHead;
    }

    public void setImageHead(BmobFile imageHead) {
        this.imageHead = imageHead;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(String carInfo) {
        this.carInfo = carInfo;
    }
}
