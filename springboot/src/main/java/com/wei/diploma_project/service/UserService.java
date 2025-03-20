package com.wei.diploma_project.service;

import com.github.pagehelper.PageInfo;
import com.wei.diploma_project.bean.GoodBean;
import com.wei.diploma_project.bean.UserBean;
import org.springframework.stereotype.Service;

/**
* @author 韦龙
* @description 针对表【user】的数据库操作Service
* @createDate 2023-03-10 17:29:57
*/
public interface UserService {
    public UserBean findUserByLoginName(String loginname);

    int updateUserInfo(UserBean u);

    int modifyPwd(UserBean u);

    /* 列表实现分页 */
    PageInfo<UserBean> getUserWithPageInfo(int pageNum, int pageSize);

    boolean addUser(UserBean u);

    boolean updateUserStatus(String loginname, int status);

    /* 列表实现分页 */
    PageInfo<UserBean> getUserPageInfoByNameLike(int pageNum, int pageSize, String like);

    boolean updateUserAvatar(String imgURL, int uid);

    UserBean getUserByUID(int uid);
}
