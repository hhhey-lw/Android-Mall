package com.wei.diploma_project.api;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OrderApi {
    // 具体某个订单信息
    @POST("/order/info")
    @FormUrlEncoded
    Call<ResponseBody> getOrderInfo(@Field("oid") int oid);
    // 所有订单信息
    @POST("/order/all")
    @FormUrlEncoded
    Call<ResponseBody> getAllOrder(@Field("uid") int uid);

    /* 支付后 通知 后台api 更新订单状态 */
    @POST("/order/query/{out_trade_no}")
    Call<ResponseBody> queryOrderByTradeNo(@Path("out_trade_no") String out_trade_no);

    /* 收货 */
    @POST("/order/received/{orderIndex}")
    Call<ResponseBody> receivedGood(@Path("orderIndex") String out_trade_no);

    /* 订单 更改地址 */
    @POST("/order/updateAddr/{oid}-{addr_id}")
    Call<ResponseBody> updateAddr(@Path("oid") int oid, @Path("addr_id") int addr_id);
}
