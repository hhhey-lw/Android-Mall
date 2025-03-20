package com.wei.diploma_project.mapper;

import com.wei.diploma_project.bean.GoodBean;
import com.wei.diploma_project.bean.GoodCategoryBean;
import com.wei.diploma_project.bean.GoodTypeBean;

import java.util.List;

/**
* @author 韦龙
* @description 针对表【good】的数据库操作Mapper
* @createDate 2023-03-12 21:03:34
* @Entity com.wei.diploma_project.bean.GoodBean
*/

public interface GoodMapper {
    //  查商品小类
    List<GoodBean> findGoodByType(int typeId);
    //  商品大类 查 对应多个小类
    List<GoodTypeBean> findTypeByCategory(int categoryId);
    //  查所有商品大类
    List<GoodCategoryBean> getGoodCategory();

    GoodBean getGoodById(int goodId);

    List<GoodBean> getGoodByCategory(int gid);

    // 模糊搜索
    List<GoodBean> getGoodByNameLike(String nameLike);

    boolean addGood(GoodBean g);

    boolean updateGood(GoodBean g);

    boolean updateGoodStatus(GoodBean g);

    // 拿所有商品ID
    List<Integer> getAllGoodID();
    // 拿最热销的几个商品
    List<GoodBean> getSaleBestList(int num);
}
