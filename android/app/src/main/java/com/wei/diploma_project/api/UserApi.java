package com.wei.diploma_project.api;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UserApi {

    // 登录
    @POST("/user/login")
    @FormUrlEncoded
    Call<ResponseBody> doLogin(@Field("loginname") String loginname, @Field("password") String password);

    // 更新信息
    @POST("/user/updateInfo")
    @FormUrlEncoded
    Call<ResponseBody> updateUserInfo(@Field("loginname") String loginname
            , @Field("username") String username
            , @Field("phone") String phone
            , @Field("gender") int gender);

    // 更新信息
    @POST("/user/modifyPwd")
    @FormUrlEncoded
    Call<ResponseBody> modifyPwd(@Field("loginname") String loginname
            , @Field("oldPassword") String oldPassword
            , @Field("password") String password
            , @Field("confirmPassword") String confirmPassword);

    @POST("/user/code/{phoneNumber}")
    Call<ResponseBody> sendCode(@Path("phoneNumber") String phoneNumber);

    @POST("/user/login/{code}/{phoneNumber}")
    Call<ResponseBody> loginByCode(@Path("code") String code, @Path("phoneNumber") String phoneNumber);

    @Multipart
    @POST("/user/update/avatar/{uid}")
    Call<ResponseBody> updateAvatar(@Part MultipartBody.Part imgFile, @Path("uid") int uid);
}
