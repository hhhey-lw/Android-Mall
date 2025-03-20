package com.wei.diploma_project.api;


import com.wei.diploma_project.bean.GoodBean;
import com.wei.diploma_project.bean.GoodCategoryBeanExtend;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GoodApi {
    // 拿 商品的大类别
    @GET("/good/getCategory")
    Call<List<GoodCategoryBeanExtend>> getGoodCategory();
    // 拿具体的某个商品
    @GET("/good/getGood/{goodId}")
    Call<GoodBean> getGoodById(@Path("goodId") int goodId);
    // 拿某大类的所有商品
    @GET("/good/category/{gid}")
    Call<ResponseBody> getGoodByCategory(@Path("gid") int gid);
    // 推荐的商品
    @GET("/rec/list/{uid}")
    Call<ResponseBody> getRecommendationList(@Path("uid") int uid);

    // 模糊搜索
    @GET("/good/search/{nameLike}")
    Call<ResponseBody> getGoodByNameLike(@Path("nameLike") String nameLike);

    // 模糊搜索
    @GET("/rec/sale/best")
    Call<ResponseBody> getSaleBestList();
}
