package com.wei.diploma_project.api;

import com.wei.diploma_project.bean.CommentBean;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CommentApi {

    @GET("/comment/all/{gid}")
    Call<ResponseBody> getCommentByGid(@Path("gid") int gid);

    @POST("/comment/add/{oid}_{oitem_id}")
    Call<ResponseBody> addComment(@Body CommentBean e, @Path("oid") int oid, @Path("oitem_id") int oitem_id);
}
