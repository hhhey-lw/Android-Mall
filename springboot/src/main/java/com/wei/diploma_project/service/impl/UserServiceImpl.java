package com.wei.diploma_project.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wei.diploma_project.bean.GoodBean;
import com.wei.diploma_project.bean.UserBean;
import com.wei.diploma_project.mapper.UserMapper;
import com.wei.diploma_project.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 韦龙
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-03-10 17:29:57
*/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserBean findUserByLoginName(String loginname) {
        return userMapper.findUserByLoginName(loginname);
    }

    @Override
    public int updateUserInfo(UserBean u) {
        return userMapper.updateUserInfo(u);
    }

    @Override
    public int modifyPwd(UserBean u) {
        return userMapper.modifyPwd(u);
    }

    @Override
    public PageInfo<UserBean> getUserWithPageInfo(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<UserBean> animalList = userMapper.getAllUser();
        return new PageInfo<>(animalList);
    }

    @Override
    public boolean addUser(UserBean u) {
        return userMapper.addUser(u);
    }

    @Override
    public boolean updateUserStatus(String loginname, int status) {
        return userMapper.updateUserStatus(loginname, status);
    }

    @Override
    public PageInfo<UserBean> getUserPageInfoByNameLike(int pageNum, int pageSize, String like) {
        PageHelper.startPage(pageNum, pageSize);
        List<UserBean> animalList = userMapper.getUserByNameLike(like);
        return new PageInfo<>(animalList);
    }

    @Override
    public boolean updateUserAvatar(String imgURL, int uid) {
        return userMapper.updateUserAvatar(imgURL, uid);
    }

    @Override
    public UserBean getUserByUID(int uid) {
        return userMapper.getUserByUID(uid);
    }
}
