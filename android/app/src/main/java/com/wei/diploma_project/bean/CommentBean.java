package com.wei.diploma_project.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName comment
 */
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
    private String ccontent;

    /**
     * 评论时间
     */
    private Date ccreateTime;

    /**
     * 好评程度 1-5星
     */
    private Integer cdegree;

    /**
     * 评论点赞数
     */
    private Integer clike;

    private String username;
    private String avatar;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getCcontent() {
        return ccontent;
    }

    public void setCcontent(String ccontent) {
        this.ccontent = ccontent;
    }

    public Date getCcreateTime() {
        return ccreateTime;
    }

    public void setCcreateTime(Date ccreateTime) {
        this.ccreateTime = ccreateTime;
    }

    public Integer getCdegree() {
        return cdegree;
    }

    public void setCdegree(Integer cdegree) {
        this.cdegree = cdegree;
    }

    public Integer getClike() {
        return clike;
    }

    public void setClike(Integer clike) {
        this.clike = clike;
    }

    private static final long serialVersionUID = 1L;
}