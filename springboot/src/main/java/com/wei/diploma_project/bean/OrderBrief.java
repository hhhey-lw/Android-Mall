package com.wei.diploma_project.bean;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * User: 韦龙
 * Date: 2023/3/25
 * description:
 */
@Data
public class OrderBrief {
    private Integer oid;
    private Integer uid;
    private Integer addr_id;
    private String orderIndex;
    private String freightIndex;
    private Integer ostatus;
    private Double total;
    private Date createTime;
    private List<GoodBean> good;
    private List<OrderItem> item;
}
