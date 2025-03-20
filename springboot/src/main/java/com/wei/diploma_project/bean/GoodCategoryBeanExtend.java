package com.wei.diploma_project.bean;

import lombok.Data;

import java.util.List;

/**
 * User: 韦龙
 * Date: 2023/3/14
 * description:  商品大类 ： 商品小类列表
 */
@Data
public class GoodCategoryBeanExtend extends GoodCategoryBean {
    private List<GoodTypeBeanExtend> goodTypeList;
}
