package com.wei.diploma_project.api;

import com.wei.diploma_project.bean.BannerBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BannerApi {
    // 获取Banner资源
    @GET("/banner/getBanner")
    Call<List<BannerBean>> getBannerRes();
}
