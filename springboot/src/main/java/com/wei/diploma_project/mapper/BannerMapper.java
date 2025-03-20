package com.wei.diploma_project.mapper;

import com.wei.diploma_project.bean.BannerBean;

import java.util.List;

/**
* @author 韦龙
* @description 针对表【banner】的数据库操作Mapper
* @createDate 2023-03-15 16:17:58
* @Entity com.wei.diploma_project.bean.Banner
*/
public interface BannerMapper {
    List<BannerBean> getBannerRes();

    List<BannerBean> getBannerAll();

    int getStatusById(int id);

    boolean changeStatus(int id, int status);

    boolean addBanner(BannerBean banner);
}
