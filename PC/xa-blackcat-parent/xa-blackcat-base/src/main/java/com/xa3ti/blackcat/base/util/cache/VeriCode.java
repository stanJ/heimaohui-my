package com.xa3ti.blackcat.base.util.cache;

import java.util.Calendar;

public class VeriCode {

    private String phoneNumber;

    private String vericode;

    private long times = Calendar.getInstance().getTimeInMillis();

    public VeriCode(String phoneNumber, String vericode) {
        this.phoneNumber = phoneNumber;
        this.vericode = vericode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getVericode() {
        return vericode;
    }

    public void setVericode(String vericode) {
        this.vericode = vericode;
    }

    public long getTimes() {
        return times;
    }

    public void setTimes(long times) {
        this.times = times;
    }
    
}
