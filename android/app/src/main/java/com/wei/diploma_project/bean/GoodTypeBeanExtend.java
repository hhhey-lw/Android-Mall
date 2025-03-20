package com.wei.diploma_project.bean;

import java.util.List;

/* 商品小类 ：商品列表 */
public class GoodTypeBeanExtend {
    private String gtype_name;
    private Integer gtype_id;
    private List<GoodBean> goodList;

    public GoodTypeBeanExtend() {
    }

    public GoodTypeBeanExtend(String gtype_name, Integer gtype_id, List<GoodBean> goodList) {
        this.gtype_name = gtype_name;
        this.gtype_id = gtype_id;
        this.goodList = goodList;
    }

    public Integer getGtype_id() {
        return gtype_id;
    }

    public void setGtype_id(Integer gtype_id) {
        this.gtype_id = gtype_id;
    }

    public String getGtype_name() {
        return gtype_name;
    }

    public void setGtype_name(String gtype_name) {
        this.gtype_name = gtype_name;
    }

    public List<GoodBean> getGoodList() {
        return goodList;
    }

    public void setGoodList(List<GoodBean> goodList) {
        this.goodList = goodList;
    }
}
