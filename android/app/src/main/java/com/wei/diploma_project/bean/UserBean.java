package com.wei.diploma_project.bean;

import java.util.Date;

/**
 * User: 韦龙
 * Date: 2023/3/10
 * description:
 */
public class UserBean {
    private Integer uid;
    private String loginname;
    private String password;
    private String username;
    private String avatar;
    private String phone;
    private Integer gender;
    private Date create_time;
    private Date update_time;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "uid=" + uid +
                ", loginname='" + loginname + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                ", phone='" + phone + '\'' +
                ", gender=" + gender +
                ", create_time=" + create_time +
                ", update_time=" + update_time +
                '}';
    }
}
