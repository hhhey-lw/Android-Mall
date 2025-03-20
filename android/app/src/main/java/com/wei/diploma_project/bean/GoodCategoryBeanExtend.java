package com.wei.diploma_project.bean;

import java.util.List;

/* 携带 商品大类 -> 商品小类:商品列表 */
public class GoodCategoryBeanExtend {

    private Integer gcategory_id;
    private String gcategory_name;
    private List<GoodTypeBeanExtend> goodTypeList;

    public Integer getGcategory_id() {
        return gcategory_id;
    }

    public void setGcategory_id(Integer gcategory_id) {
        this.gcategory_id = gcategory_id;
    }

    public String getGcategory_name() {
        return gcategory_name;
    }

    public void setGcategory_name(String gcategory_name) {
        this.gcategory_name = gcategory_name;
    }

    public List<GoodTypeBeanExtend> getGoodTypeList() {
        return goodTypeList;
    }

    public void setGoodTypeList(List<GoodTypeBeanExtend> goodTypeList) {
        this.goodTypeList = goodTypeList;
    }

    public GoodCategoryBeanExtend() {
    }

    public GoodCategoryBeanExtend(Integer gcategory_id, String gcategory_name) {
        this.gcategory_id = gcategory_id;
        this.gcategory_name = gcategory_name;
    }
}
