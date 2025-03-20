package com.wei.diploma_project.mapper;

import com.wei.diploma_project.bean.GoodTypeBeanWithStatus;

import java.util.List;

/**
 * User: 韦龙
 * Date: 2023/5/2
 * description:
 */
public interface GoodTypeMapper {
    List<GoodTypeBeanWithStatus> getAllTypeByCategory(int gcategory_id);

    boolean addType(int gcategory_id, String gtype_name);

    boolean updateTypeByID(int gtype_id, int gcategory_id, String newName);

    boolean changeTypeStatusByID(int gtype_id, int status);

    int getStatusByID(int gtype_id);
}
