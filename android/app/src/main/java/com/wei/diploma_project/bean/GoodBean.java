package com.wei.diploma_project.bean;

import com.google.android.material.timepicker.TimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @TableName good
 */
public class GoodBean implements Serializable {
    /**
     * 商品id
     */
    private Integer gid;

    /**
     * 商品名称
     */
    private String gname;

    /**
     * 商品图片，按某个特殊字符切割
     */
    private String gimage;

    /**
     * 商品原价
     */
    private double gprimalPrice;

    /**
     * 商品促销价
     */
    private double gdiscountPrice;

    /**
     * 是否促销 分原价或促销价
     */
    private Integer gsaleStatus;

    /**
     * 商品库存
     */
    private Integer gnumber;

    /**
     * 商品类型
     */
    private Integer gtypeId;

    /**
     * 商品状态：0 1 2 未开售，正在售，售罄
     */
    private Integer gstatus;

    /**
     * 商品创建时间
     */
    private Date gcreateTime;

    /**
     * 商品修改时间
     */
    private Date gupdateTime;

    public GoodBean() {
    }

    public GoodBean(String gname) {
        this.gname = gname;
    }

    public GoodBean(String gname, double gprimalPrice) {
        this.gname = gname;
        this.gprimalPrice = gprimalPrice;
    }

    public GoodBean(Integer gid, String gname, String gimage, double gprimalPrice, double gdiscountPrice, Integer gsaleStatus, Integer gnumber, Integer gtypeId, Integer gstatus, Date gcreateTime, Date gupdateTime) {
        this.gid = gid;
        this.gname = gname;
        this.gimage = gimage;
        this.gprimalPrice = gprimalPrice;
        this.gdiscountPrice = gdiscountPrice;
        this.gsaleStatus = gsaleStatus;
        this.gnumber = gnumber;
        this.gtypeId = gtypeId;
        this.gstatus = gstatus;
        this.gcreateTime = gcreateTime;
        this.gupdateTime = gupdateTime;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getGimage() {
        return gimage;
    }

    public void setGimage(String gimage) {
        this.gimage = gimage;
    }

    public double getGprimalPrice() {
        return gprimalPrice;
    }

    public void setGprimalPrice(double gprimalPrice) {
        this.gprimalPrice = gprimalPrice;
    }

    public double getGdiscountPrice() {
        return gdiscountPrice;
    }

    public void setGdiscountPrice(double gdiscountPrice) {
        this.gdiscountPrice = gdiscountPrice;
    }

    public Integer getGsaleStatus() {
        return gsaleStatus;
    }

    public void setGsaleStatus(Integer gsaleStatus) {
        this.gsaleStatus = gsaleStatus;
    }

    public Integer getGnumber() {
        return gnumber;
    }

    public void setGnumber(Integer gnumber) {
        this.gnumber = gnumber;
    }

    public Integer getGtypeId() {
        return gtypeId;
    }

    public void setGtypeId(Integer gtypeId) {
        this.gtypeId = gtypeId;
    }

    public Integer getGstatus() {
        return gstatus;
    }

    public void setGstatus(Integer gstatus) {
        this.gstatus = gstatus;
    }

    public Date getGcreateTime() {
        return gcreateTime;
    }

    public void setGcreateTime(Date gcreateTime) {
        this.gcreateTime = gcreateTime;
    }

    public Date getGupdateTime() {
        return gupdateTime;
    }

    public void setGupdateTime(Date gupdateTime) {
        this.gupdateTime = gupdateTime;
    }

    @Override
    public String toString() {
        return "GoodBean{" +
                "gid=" + gid +
                ", gname='" + gname + '\'' +
                ", gimage='" + gimage + '\'' +
                ", gprimalPrice=" + gprimalPrice +
                ", gdiscountPrice=" + gdiscountPrice +
                ", gsaleStatus=" + gsaleStatus +
                ", gnumber=" + gnumber +
                ", gtypeId=" + gtypeId +
                ", gstatus=" + gstatus +
                ", gcreateTime=" + gcreateTime +
                ", gupdateTime=" + gupdateTime +
                '}';
    }
}