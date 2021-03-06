package com.bysj.newlbstruck.utils;

public enum StateEnum {
    NO_RECEIPT(0, "未接单"), RECEIPT(1, "已接单"), ARRIVE(2, "已到达"), DELIVERY(3, "开始运货"), COMPLETE(4, "已完成"), PAYMENT(5, "已支付"), NOSTATE(10, "未知状态");

    int value;
    String name;

    StateEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static String getState(int value) {
        String rtn = "";
        for (StateEnum stateEnum : StateEnum.values()) {
            if (stateEnum.getValue() == value) {
                rtn = stateEnum.getName();
            }
        }
        return rtn;
    }
}  