package com.wei.diploma_project.bean;


/**
 * 
 * @TableName shoppingcart
 */
public class ShoppingcartBean {
    /**
     * 购物车id
     */
    private Integer cid;

    /**
     * 用户id
     */
    private Integer uid;

    /**
     * 商品id
     */
    private Integer gid;

    /**
     * 商品购买数量
     */
    private Integer gpurchaseNumber;

    /**
     * 商品状态
     */
    private Integer gstatus;

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

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
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

    public ShoppingcartBean() {
    }

    public ShoppingcartBean(Integer uid, Integer gid, Integer gpurchaseNumber, Integer gstatus) {
        this.uid = uid;
        this.gid = gid;
        this.gpurchaseNumber = gpurchaseNumber;
        this.gstatus = gstatus;
    }
}