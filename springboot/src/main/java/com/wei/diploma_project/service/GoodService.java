package com.wei.diploma_project.service;

import com.github.pagehelper.PageInfo;
import com.wei.diploma_project.bean.*;

import java.util.List;

/**
* @author 韦龙
* @description 针对表【good】的数据库操作Service
* @createDate 2023-03-12 21:03:34
*/
public interface GoodService {
    // 查小类商品列表
    List<GoodBean> findGoodByType(int typeId);
    // 查大类商品列表
    List<GoodTypeBeanExtend> findGoodByCategory(int categoryId);
    // 查所有大类
    List<GoodCategoryBean> findGoodCategory();

    List<GoodCategoryBeanExtend> findGoodCategoryList();

    GoodBean getGoodById(int goodId);

    List<GoodBean> getGoodByCategory(int gid);
    // 模糊搜索
    List<GoodBean> getGoodByNameLike(String nameLike);

    PageInfo<GoodBean> getGoodPageInfoByType(int pageNum, int pageSize, int typeId);

    boolean addGood(GoodBean g);

    boolean updateGood(GoodBean g);

    boolean updateGoodStatus(GoodBean g);
}
