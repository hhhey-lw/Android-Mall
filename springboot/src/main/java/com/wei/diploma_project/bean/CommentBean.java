package com.wei.diploma_project.bean;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.ToString;

/**
 * 
 * @TableName comment
 */
@Data
@ToString
public class CommentBean implements Serializable {
    /**
     * 评论ID
     */
    private Integer cid;

    /**
     * 商品ID
     */
    private Integer gid;

    /**
     * 用户ID
     */
    private Integer uid;

    /**
     * 评论内容
     */
    private String cContent;

    /**
     * 评论时间
     */
    private Date cCreateTime;

    /**
     * 好评程度 1-5星
     */
    private Integer cDegree;

    /**
     * 评论点赞数
     */
    private Integer cLike;
    private Integer cStatus;

    private String username;
    private String avatar;

    private static final long serialVersionUID = 1L;
}