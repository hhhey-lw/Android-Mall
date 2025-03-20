package com.wei.diploma_project.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wei.diploma_project.bean.GoodTypeBeanWithStatus;
import com.wei.diploma_project.mapper.GoodTypeMapper;
import com.wei.diploma_project.service.GoodTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: 韦龙
 * Date: 2023/5/2
 * description:
 */
@Service
public class GoodTypeServiceImpl implements GoodTypeService {
    @Autowired
    private GoodTypeMapper goodTypeMapper;

    @Override
    public PageInfo<GoodTypeBeanWithStatus> getAllTypeByCategoryID(int gcategory_id, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<GoodTypeBeanWithStatus> data = goodTypeMapper.getAllTypeByCategory(gcategory_id);
        return new PageInfo<>(data);
    }

    @Override
    public boolean addType(int gcategory_id, String gtype_name) {
        return goodTypeMapper.addType(gcategory_id, gtype_name);
    }

    @Override
    public boolean updateTypeByID(int gtype_id, int gcategory_id, String newName) {
        return goodTypeMapper.updateTypeByID(gtype_id, gcategory_id, newName);
    }

    @Override
    public boolean disableTypeByID(int id) {
        return goodTypeMapper.changeTypeStatusByID(id, 0);
    }

    @Override
    public boolean ableTypeByID(int id) {
        return goodTypeMapper.changeTypeStatusByID(id, 1);
    }

    @Override
    public int getStatusByID(int gtype_id) {
        return goodTypeMapper.getStatusByID(gtype_id);
    }
}
