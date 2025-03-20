package com.wei.diploma_project.mapper;

import com.wei.diploma_project.bean.UserBean;

import java.util.List;

/**
* @author 韦龙
* @description 针对表【user】的数据库操作Mapper
* @createDate 2023-03-10 17:29:57
* @Entity com.wei.diploma_project.domain.User
*/

public interface UserMapper  {

    UserBean findUserByLoginName(String loginname);

    int updateUserInfo(UserBean u);

    boolean updateUserAvatar(String imgURL, int uid);

    int modifyPwd(UserBean u);

    List<UserBean> getAllUser();

    boolean addUser(UserBean u);

    boolean updateUserStatus(String loginname, int status);

    List<UserBean> getUserByNameLike(String like);

    UserBean getUserByUID(int uid);
}
