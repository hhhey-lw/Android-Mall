package com.wei.diploma_project.service;


import com.github.pagehelper.PageInfo;
import com.wei.diploma_project.bean.BannerBean;
import com.wei.diploma_project.bean.CommentBean;

import java.util.List;

/**
* @author 韦龙
* @description 针对表【banner】的数据库操作Service
* @createDate 2023-03-15 16:17:58
*/
public interface BannerService{
    List<BannerBean> getBannerRes();

    PageInfo<BannerBean> getBannerAll(int pageNum, int pageSize);

    boolean changeBannerStatus(int cid);

    boolean addBanner(BannerBean banner);
}
