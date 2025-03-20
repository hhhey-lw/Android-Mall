package com.wei.diploma_project.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * @TableName order
 */
@Data
public class OrderBean {
    /**
     * 订单id  不同商品创建不同订单  分开
     */
    private Integer oid;

    /**
     * 用户id
     */
    private Integer uid;

    /**
     * 地址id 
     */
    private Integer addrId;

    /**
     * 订单编号 20233xxx...
     */
    private String orderIndex;

    /**
     * 快递单号
     */
    private String freightIndex;

    /**
     * 创建\付款\发货\完成\评价  12345
     */
    private Integer ostatus;

    /**
     * 订单创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 完成时间
     */
    private Date finisheTime;

    /**
     * 付款时间
     */
    private Date paymentTime;

    /**
     * 发货时间
     */
    private Date deliveryTime;

    /**
     * 总价
     */
    private Double priceTotal;

    /**
     * 运费
     */
    private Double freightExpense;

    /**
     * 支付方式：1=微信支付
     */
    private Integer paymentMode;

}