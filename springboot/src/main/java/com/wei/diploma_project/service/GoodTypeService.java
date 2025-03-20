package com.wei.diploma_project.service;

import com.github.pagehelper.PageInfo;
import com.wei.diploma_project.bean.GoodCategoryWithStatusBean;
import com.wei.diploma_project.bean.GoodTypeBeanWithStatus;

/**
 * User: 韦龙
 * Date: 2023/5/2
 * description:
 */
public interface GoodTypeService {
    PageInfo<GoodTypeBeanWithStatus> getAllTypeByCategoryID(int gcategory_id, int pageNum, int pageSize);

    boolean addType(int gcategory_id, String gtype_name);

    boolean updateTypeByID(int gtype_id, int gcategory_id, String newName);

    boolean disableTypeByID(int id);

    boolean ableTypeByID(int id);

    int getStatusByID(int gtype_id);
}
