package com.wei.diploma_project.bean;

public class CartBean {

    private Integer cid;  // 购物车 cart id
    private Integer uid;  // 用户id
    private Integer gid;  // 商品id
    private GoodBean good;  // 商品信息
    private Integer gpurchaseNumber;  // 购买数量
    private Integer gstatus;


    public CartBean() {
    }

    public CartBean(Integer cid, Integer uid, GoodBean good, Integer gpurchase_number, Integer gstatus) {
        this.cid = cid;
        this.uid = uid;
        this.good = good;
        this.gpurchaseNumber = gpurchase_number;
        this.gstatus = gstatus;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public GoodBean getGood() {
        return good;
    }

    public void setGood(GoodBean good) {
        this.good = good;
    }

    public Integer getGpurchaseNumber() {
        return gpurchaseNumber;
    }

    public void setGpurchaseNumber(Integer gpurchaseNumber) {
        this.gpurchaseNumber = gpurchaseNumber;
    }

    public Integer getGstatus() {
        return gstatus;
    }

    public void setGstatus(Integer gstatus) {
        this.gstatus = gstatus;
    }

    @Override
    public String toString() {
        return "CartBean{" +
                "cid=" + cid +
                ", uid=" + uid +
                ", gid=" + gid +
                ", good=" + good +
                ", gpurchaseNumber=" + gpurchaseNumber +
                ", gstatus=" + gstatus +
                '}';
    }
}
