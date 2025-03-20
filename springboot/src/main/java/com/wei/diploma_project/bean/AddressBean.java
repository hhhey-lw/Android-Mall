package com.wei.diploma_project.bean;

import java.io.Serializable;
import lombok.Data;
import lombok.ToString;

/**
 * 
 * @TableName address
 */
@Data
@ToString
public class AddressBean implements Serializable {
    /**
     * 地址ID
     */
    private Integer addrId;

    /**
     * 关联用户ID
     */
    private Integer uid;

    /**
     * 
     */
    private String username;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 省份
     */
    private String addrProvince;

    /**
     * 市
     */
    private String addrCity;

    /**
     * 区
     */
    private String addrDistrict;

    /**
     * 详细到街道...
     */
    private String addrDetail;

    /**
     * 是否默认地址
     */
    private Integer addrDefualt;

    private Integer delStatus;

    private static final long serialVersionUID = 1L;
}