package com.wei.diploma_project.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * User: 韦龙
 * Date: 2023/3/10
 * description:
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
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
    private Integer del_status;
}
