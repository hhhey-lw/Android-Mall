package com.wei.diploma_project.bean;


/**
 * 
 * @TableName order_item
 */
public class OrderItem {
    /**
     * 订单子项 ID
     */
    private Integer oitemId;

    /**
     * 订单ID
     */
    private Integer oid;

    /**
     * 商品ID
     */
    private Integer gid;

    /**
     * 商品购买数量
     */
    private Integer gpurchaseNumber;

    /**
     * 商品购买价格
     */
    private Double gpurchasePrice;

    private Integer oratingStatus;

    public Integer getOratingStatus() {
        return oratingStatus;
    }

    public void setOratingStatus(Integer oratingStatus) {
        this.oratingStatus = oratingStatus;
    }

    public Integer getOitemId() {
        return oitemId;
    }

    public void setOitemId(Integer oitemId) {
        this.oitemId = oitemId;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
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

    public Double getGpurchasePrice() {
        return gpurchasePrice;
    }

    public void setGpurchasePrice(Double gpurchasePrice) {
        this.gpurchasePrice = gpurchasePrice;
    }

    public OrderItem() {
    }

    public OrderItem(Integer oitemId, Integer oid, Integer gid, Integer gpurchaseNumber, Double gpurchasePrice, Integer oratingStatus) {
        this.oitemId = oitemId;
        this.oid = oid;
        this.gid = gid;
        this.gpurchaseNumber = gpurchaseNumber;
        this.gpurchasePrice = gpurchasePrice;
        this.oratingStatus = oratingStatus;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "oitemId=" + oitemId +
                ", oid=" + oid +
                ", gid=" + gid +
                ", gpurchaseNumber=" + gpurchaseNumber +
                ", gpurchasePrice=" + gpurchasePrice +
                ", oratingStatus=" + oratingStatus +
                '}';
    }
}