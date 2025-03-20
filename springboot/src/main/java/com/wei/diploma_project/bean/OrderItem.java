package com.wei.diploma_project.bean;

import lombok.Data;
import lombok.ToString;

/**
 * 
 * @TableName order_item
 */
@Data
@ToString
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

}