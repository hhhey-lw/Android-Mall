package com.wei.diploma_project.mapper;


import com.wei.diploma_project.bean.GoodCategoryBean;
import com.wei.diploma_project.bean.GoodCategoryWithStatusBean;

import java.util.List;

/**
* @author 韦龙
* @description 针对表【goodcategory】的数据库操作Mapper
* @createDate 2023-05-02 00:31:44
* @Entity com.wei.diploma_project.bean.Goodcategory
*/
public interface GoodCategoryMapper {
    List<GoodCategoryWithStatusBean> getAllCategory();

    boolean addCategory(String categoryName);

    boolean updateCategoryByID(int id, String newName);

    boolean changeCategoryStatusByID(int id, int status);

    int getStatusByID(int id);
}
