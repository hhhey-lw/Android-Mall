package com.wei.diploma_project.api;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CartApi {
    // 拿 用户的购物车 信息
    @POST("/cart/get")
    @FormUrlEncoded
    Call<ResponseBody> getCartList(@Field("uid") int uid);
    // 添加商品到购物车中  data 为商品必要信息的 json格式
    @POST("/cart/add")
    Call<ResponseBody> addGoodToCart(@Body RequestBody data);
    // 将勾选中的商品提交为待付款的订单
    @POST("/cart/submit")
    Call<ResponseBody> submitCartToOrder(@Body RequestBody data);
    // 更新购物车 商品数量
    @POST("/cart/update")
    @FormUrlEncoded
    Call<ResponseBody> updateGoodNumber(@Field("cid") int cid, @Field("purchaseNumber") int purchaseNumber);

    @DELETE("/cart/delete/{cid}")
    Call<ResponseBody> deleteCart(@Path("cid") int cid);

}
