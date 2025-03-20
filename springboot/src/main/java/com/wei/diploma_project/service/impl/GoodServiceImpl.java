package com.wei.diploma_project.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wei.diploma_project.bean.*;
import com.wei.diploma_project.mapper.GoodMapper;
import com.wei.diploma_project.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author 韦龙
* @description 针对表【good】的数据库操作Service实现
* @createDate 2023-03-12 21:03:34
*/
@Service
public class GoodServiceImpl implements GoodService {

    @Autowired
    private GoodMapper goodBeanMapper;

    @Override
    public List<GoodBean> findGoodByType(int typeId) {
        return goodBeanMapper.findGoodByType(typeId);
    }
    /* 商品小类组合对应的商品列表 */
    @Override
    public List<GoodTypeBeanExtend> findGoodByCategory(int categoryId) {
        ArrayList<GoodTypeBeanExtend> res = new ArrayList<>();
        //  大类查小类
        List<GoodTypeBean> typeList = goodBeanMapper.findTypeByCategory(categoryId);
        for (GoodTypeBean typeBean : typeList) {
            //  查小类 商品列表
            List<GoodBean> goods = goodBeanMapper.findGoodByType(typeBean.getGtype_id());
            // 装填数据
            GoodTypeBeanExtend goodTypeBeanExtend = new GoodTypeBeanExtend();
            goodTypeBeanExtend.setGtype_id(typeBean.getGtype_id());
            goodTypeBeanExtend.setGtype_name(typeBean.getGtype_name());
            goodTypeBeanExtend.setGoodList(goods);
            res.add(goodTypeBeanExtend);
        }
        return res;
    }
    /* 查商品大类 */
    @Override
    public List<GoodCategoryBean> findGoodCategory() {
        return goodBeanMapper.getGoodCategory();
    }

    /* 查整体信息 */
    @Override
    public List<GoodCategoryBeanExtend> findGoodCategoryList() {
        List<GoodCategoryBeanExtend> goodCategoryBeanExtends = new ArrayList<>();
        /* 商品大类信息 */
        List<GoodCategoryBean> goodCategory = findGoodCategory();
        for (GoodCategoryBean e : goodCategory) {
            /* 查所有小类 */
            List<GoodTypeBeanExtend> data = findGoodByCategory(e.getGcategory_id());
            GoodCategoryBeanExtend o = new GoodCategoryBeanExtend();

            o.setGcategory_id(e.getGcategory_id());
            o.setGcategory_name(e.getGcategory_name());
            o.setGoodTypeList(data);

            goodCategoryBeanExtends.add(o);
        }
        return goodCategoryBeanExtends;
    }

    @Override
    public GoodBean getGoodById(int goodId) {
        return goodBeanMapper.getGoodById(goodId);
    }

    @Override
    public List<GoodBean> getGoodByCategory(int gid) {
        return goodBeanMapper.getGoodByCategory(gid);
    }

    @Override
    public List<GoodBean> getGoodByNameLike(String nameLike) {
        return goodBeanMapper.getGoodByNameLike(nameLike);
    }

    @Override
    public boolean addGood(GoodBean g) {
        return goodBeanMapper.addGood(g);
    }

    @Override
    public boolean updateGood(GoodBean g) {
        return goodBeanMapper.updateGood(g);
    }

    @Override
    public boolean updateGoodStatus(GoodBean g) {
        g.setGstatus(g.getGstatus() == 1 ? 0 : 1); // 1上架 0下架
        return goodBeanMapper.updateGoodStatus(g);
    }

    @Override
    public PageInfo<GoodBean> getGoodPageInfoByType(int pageNum, int pageSize, int typeId) {
        PageHelper.startPage(pageNum, pageSize);
        List<GoodBean> goodBeanList = goodBeanMapper.findGoodByType(typeId);
        return new PageInfo<>(goodBeanList);
    }

}
