package com.wei.diploma_project.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wei.diploma_project.bean.BannerBean;
import com.wei.diploma_project.bean.CommentBean;
import com.wei.diploma_project.mapper.BannerMapper;
import com.wei.diploma_project.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 韦龙
* @description 针对表【banner】的数据库操作Service实现
* @createDate 2023-03-15 16:17:58
*/
@Service
public class BannerServiceImpl implements BannerService{

    @Autowired
    private BannerMapper bannerMapper;

    @Override
    public List<BannerBean> getBannerRes() {
        return bannerMapper.getBannerRes();
    }

    @Override
    public PageInfo<BannerBean> getBannerAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<BannerBean> data = bannerMapper.getBannerAll();
        return new PageInfo<>(data);
    }

    @Override
    public boolean changeBannerStatus(int cid) {
        int status = bannerMapper.getStatusById(cid);
        return bannerMapper.changeStatus(cid, status == 1 ? 0 : 1);
    }

    @Override
    public boolean addBanner(BannerBean banner) {
        return bannerMapper.addBanner(banner);
    }
}
