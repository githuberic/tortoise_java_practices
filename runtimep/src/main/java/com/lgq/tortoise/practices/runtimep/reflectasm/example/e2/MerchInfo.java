package com.lgq.tortoise.practices.runtimep.reflectasm.example.e2;

/**
 * @author lgq
 */
public class MerchInfo {
    public String fcID;
    public String fcMerchId;
    public String fcMerchName;

    public String getFcID() {
        return fcID;
    }

    public void setFcID(String fcID) {
        this.fcID = fcID;
    }

    public String getFcMerchId() {
        return fcMerchId;
    }

    public void setFcMerchId(String fcMerchId) {
        this.fcMerchId = fcMerchId;
    }

    public String getFcMerchName() {
        return fcMerchName;
    }

    public void setFcMerchName(String fcMerchName) {
        this.fcMerchName = fcMerchName;
    }

    @Override
    public String toString() {
        return "MerchInfo{" +
                "fcID='" + fcID + '\'' +
                ", fcMerchId='" + fcMerchId + '\'' +
                ", fcMerchName='" + fcMerchName + '\'' +
                '}';
    }
}
