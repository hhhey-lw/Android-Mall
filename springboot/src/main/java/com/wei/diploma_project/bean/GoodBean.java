package com.wei.diploma_project.bean;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

/**
 * 
 * @TableName good
 */
@Data
@ToString
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

    private String gdetail;
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

    // 销量
    private Integer gsaleNumber;

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

}