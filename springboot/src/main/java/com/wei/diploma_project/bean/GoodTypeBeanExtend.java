package com.wei.diploma_project.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * User: 韦龙
 * Date: 2023/3/13
 * description:
 */
/* 此用来包裹 商品小类 和 商品列表信息 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodTypeBeanExtend extends GoodTypeBean {
    private List<GoodBean> goodList;
}
