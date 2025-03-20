package com.wei.diploma_project.service;

import com.github.pagehelper.PageInfo;
import com.wei.diploma_project.bean.GoodCategoryBean;
import com.wei.diploma_project.bean.GoodCategoryWithStatusBean;

import java.util.List;

/**
 * User: 韦龙
 * Date: 2023/5/2
 * description:
 */
public interface GoodCategoryService {
    PageInfo<GoodCategoryWithStatusBean> getAllCategory(int pageNum, int pageSize);

    boolean addCategory(String categoryName);

    boolean updateCategoryByID(int id, String newName);

    boolean disableCategoryByID(int id);

    boolean ableCategoryByID(int id);

    int getStatusByID(int id);
}
