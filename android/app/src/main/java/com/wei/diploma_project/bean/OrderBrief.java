package com.wei.diploma_project.bean;

import java.util.Date;
import java.util.List;

/**
 * User: 韦龙
 * Date: 2023/3/25
 * description:
 */
public class OrderBrief {
    private Integer oid;
    private Integer uid;
    private Integer addr_id;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getAddr_id() {
        return addr_id;
    }

    public void setAddr_id(Integer addr_id) {
        this.addr_id = addr_id;
    }

    public String getFreightIndex() {
        return freightIndex;
    }

    public void setFreightIndex(String freightIndex) {
        this.freightIndex = freightIndex;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    private String orderIndex;
    private String freightIndex;
    private Integer ostatus;
    private Double total;
    private Date createTime;
    private List<GoodBean> good;
    private List<OrderItem> item;

    public List<OrderItem> getItem() {
        return item;
    }

    public void setItem(List<OrderItem> item) {
        this.item = item;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public String getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(String orderIndex) {
        this.orderIndex = orderIndex;
    }

    public Integer getOstatus() {
        return ostatus;
    }

    public void setOstatus(Integer ostatus) {
        this.ostatus = ostatus;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<GoodBean> getGood() {
        return good;
    }

    public void setGood(List<GoodBean> good) {
        this.good = good;
    }
}
