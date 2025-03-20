package com.wei.diploma_project.api;


import com.wei.diploma_project.bean.AddressBean;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AddressApi {
    @POST("/addr/add")
    Call<ResponseBody> addAddress(@Body AddressBean data);

    // LIST ALL
    @GET("/addr/get/{uid}")
    Call<ResponseBody> getAddress(@Path("uid") int uid);

    @DELETE("/addr/del/{addr_id}")
    Call<ResponseBody> delAddress(@Path("addr_id") int addr_id);

    // Single
    @GET("/addr/find/{addr_id}")
    Call<ResponseBody> getSingleAddress(@Path("addr_id") int addr_id);

    @POST("/addr/update")
    Call<ResponseBody> updateAddress(@Body AddressBean data);

    // 切换默认地址
    @POST("/addr/default/{uid}-{addr_id}")
    Call<ResponseBody> setDefaultAddr(@Path("uid") int uid, @Path("addr_id") int addr_id);

    // 拿到默认地址
    @GET("/addr/default/{uid}")
    Call<ResponseBody> getDefaultAddr(@Path("uid") int uid);
}
