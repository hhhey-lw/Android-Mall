package com.wei.diploma_project.bean;

import java.util.Date;

/**
 * 
 * @TableName order
 */
public class OrderBean {
    /**
     * 订单id  不同商品创建不同订单  分开
     */
    private Integer oid;

    /**
     * 用户id
     */
    private Integer uid;

    /**
     * 地址id 
     */
    private Integer addrId;

    /**
     * 订单编号 20233xxx...
     */
    private String orderIndex;

    /**
     * 快递单号
     */
    private String freightIndex;

    /**
     * 创建\付款\发货\完成\评价  12345
     */
    private Integer ostatus;

    /**
     * 订单创建时间
     */
    private Date createTime;

    /**
     * 完成时间
     */
    private Date finisheTime;

    /**
     * 付款时间
     */
    private Date paymentTime;

    /**
     * 发货时间
     */
    private Date deliveryTime;

    /**
     * 总价
     */
    private Double priceTotal;

    /**
     * 运费
     */
    private Double freightExpense;

    /**
     * 支付方式：1=微信支付
     */
    private Integer paymentMode;

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getAddrId() {
        return addrId;
    }

    public void setAddrId(Integer addrId) {
        this.addrId = addrId;
    }

    public String getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(String orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getFreightIndex() {
        return freightIndex;
    }

    public void setFreightIndex(String freightIndex) {
        this.freightIndex = freightIndex;
    }

    public Integer getOstatus() {
        return ostatus;
    }

    public void setOstatus(Integer ostatus) {
        this.ostatus = ostatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getFinisheTime() {
        return finisheTime;
    }

    public void setFinisheTime(Date finisheTime) {
        this.finisheTime = finisheTime;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Double getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(Double priceTotal) {
        this.priceTotal = priceTotal;
    }

    public Double getFreightExpense() {
        return freightExpense;
    }

    public void setFreightExpense(Double freightExpense) {
        this.freightExpense = freightExpense;
    }

    public Integer getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(Integer paymentMode) {
        this.paymentMode = paymentMode;
    }

    public OrderBean() {
    }

    public OrderBean(Integer oid, Integer uid, Integer addrId, String orderIndex, String freightIndex, Integer ostatus, Date createTime, Date finisheTime, Date paymentTime, Date deliveryTime, Double priceTotal, Double freightExpense, Integer paymentMode) {
        this.oid = oid;
        this.uid = uid;
        this.addrId = addrId;
        this.orderIndex = orderIndex;
        this.freightIndex = freightIndex;
        this.ostatus = ostatus;
        this.createTime = createTime;
        this.finisheTime = finisheTime;
        this.paymentTime = paymentTime;
        this.deliveryTime = deliveryTime;
        this.priceTotal = priceTotal;
        this.freightExpense = freightExpense;
        this.paymentMode = paymentMode;
    }

    @Override
    public String toString() {
        return "OrderBean{" +
                "oid=" + oid +
                ", uid=" + uid +
                ", addrId=" + addrId +
                ", orderIndex='" + orderIndex + '\'' +
                ", freightIndex='" + freightIndex + '\'' +
                ", ostatus=" + ostatus +
                ", createTime=" + createTime +
                ", finisheTime=" + finisheTime +
                ", paymentTime=" + paymentTime +
                ", deliveryTime=" + deliveryTime +
                ", priceTotal=" + priceTotal +
                ", freightExpense=" + freightExpense +
                ", paymentMode=" + paymentMode +
                '}';
    }
}