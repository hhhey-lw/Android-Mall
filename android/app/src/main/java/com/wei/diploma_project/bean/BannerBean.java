package com.wei.diploma_project.bean;


public class BannerBean {
    /**
     * 轮播图id
     */
    private Integer bid;

    /**
     * 轮播图图片URL
     */
    private String bimageurl;

    /**
     * 禁用或启用
     */
    private Integer bStatus;

    public BannerBean() {
    }

    public BannerBean(Integer bid, String bImageurl, Integer bStatus) {
        this.bid = bid;
        this.bimageurl = bImageurl;
        this.bStatus = bStatus;
    }

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public String getbImageurl() {
        return bimageurl;
    }

    public void setbImageurl(String bImageurl) {
        this.bimageurl = bImageurl;
    }

    public Integer getbStatus() {
        return bStatus;
    }

    public void setbStatus(Integer bStatus) {
        this.bStatus = bStatus;
    }
}
