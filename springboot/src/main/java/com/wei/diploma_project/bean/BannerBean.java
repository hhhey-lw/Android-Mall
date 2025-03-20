package com.wei.diploma_project.bean;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName banner
 */
@Data
public class BannerBean implements Serializable {
    /**
     * 轮播图id
     */
    private Integer bid;

    /**
     * 轮播图图片URL
     */
    private String bImageurl;

    /**
     * 禁用或启用
     */
    private Integer bStatus;

    private static final long serialVersionUID = 1L;
}