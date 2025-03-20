package com.wei.diploma_project.bean;

import java.io.Serializable;

/**
 * 
 * @TableName address
 */
public class AddressBean implements Serializable {
    /**
     * 地址ID
     */
    private Integer addrId;

    /**
     * 关联用户ID
     */
    private Integer uid;

    /**
     * 
     */
    private String username;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 省份
     */
    private String addrProvince;

    /**
     * 市
     */
    private String addrCity;

    /**
     * 区
     */
    private String addrDistrict;

    /**
     * 详细到街道...
     */
    private String addrDetail;

    /**
     * 是否默认地址
     */
    private Integer addrDefualt;

    private Integer delStatus;

    public Integer getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

    private static final long serialVersionUID = 1L;

    public Integer getAddrId() {
        return addrId;
    }

    public void setAddrId(Integer addrId) {
        this.addrId = addrId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddrProvince() {
        return addrProvince;
    }

    public void setAddrProvince(String addrProvince) {
        this.addrProvince = addrProvince;
    }

    public String getAddrCity() {
        return addrCity;
    }

    public void setAddrCity(String addrCity) {
        this.addrCity = addrCity;
    }

    public String getAddrDistrict() {
        return addrDistrict;
    }

    public void setAddrDistrict(String addrDistrict) {
        this.addrDistrict = addrDistrict;
    }

    public String getAddrDetail() {
        return addrDetail;
    }

    public void setAddrDetail(String addrDetail) {
        this.addrDetail = addrDetail;
    }

    public Integer getAddrDefualt() {
        return addrDefualt;
    }

    public void setAddrDefualt(Integer addrDefualt) {
        this.addrDefualt = addrDefualt;
    }

    @Override
    public String toString() {
        return "AddressBean{" +
                "addrId=" + addrId +
                ", uid=" + uid +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", addrProvince='" + addrProvince + '\'' +
                ", addrCity='" + addrCity + '\'' +
                ", addrDistrict='" + addrDistrict + '\'' +
                ", addrDetail='" + addrDetail + '\'' +
                ", addrDefualt=" + addrDefualt +
                ", delStatus=" + delStatus +
                '}';
    }
}