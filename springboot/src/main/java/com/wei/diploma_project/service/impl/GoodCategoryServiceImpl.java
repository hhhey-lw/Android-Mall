package com.wei.diploma_project.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wei.diploma_project.bean.GoodCategoryBean;
import com.wei.diploma_project.bean.GoodCategoryWithStatusBean;
import com.wei.diploma_project.mapper.GoodCategoryMapper;
import com.wei.diploma_project.service.GoodCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: 韦龙
 * Date: 2023/5/2
 * description:
 */
@Service
public class GoodCategoryServiceImpl implements GoodCategoryService {
    @Autowired
    private GoodCategoryMapper goodCategoryMapper;

    @Override
    public PageInfo<GoodCategoryWithStatusBean> getAllCategory(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<GoodCategoryWithStatusBean> allCategory = goodCategoryMapper.getAllCategory();
        return new PageInfo<>(allCategory);
    }

    @Override
    public boolean addCategory(String categoryName) {
        return goodCategoryMapper.addCategory(categoryName);
    }

    @Override
    public boolean updateCategoryByID(int id, String newName) {
        return goodCategoryMapper.updateCategoryByID(id, newName);
    }

    @Override
    public boolean disableCategoryByID(int id) {
        return goodCategoryMapper.changeCategoryStatusByID(id, 0);
    }

    @Override
    public boolean ableCategoryByID(int id) {
        return goodCategoryMapper.changeCategoryStatusByID(id, 1);
    }

    @Override
    public int getStatusByID(int id) {
        return goodCategoryMapper.getStatusByID(id);
    }
}
