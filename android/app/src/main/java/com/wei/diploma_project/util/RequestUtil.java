package com.wei.diploma_project.util;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.wei.diploma_project.bean.UserBean;

import java.io.IOException;
import java.util.Date;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestUtil {

    public static final String BaseURL = "http://192.168.43.99:8888";

    private static Retrofit retrofit;

    private static Context context;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            /* 统一添加 token 到 header中 */
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        SharedPreferences loginInfo = context.getSharedPreferences("loginInfo", MODE_PRIVATE);
                        Request request = chain.request().newBuilder()
                                .addHeader("token", loginInfo.getString("token", ""))
                                .build();
                        return chain.proceed(request);
                    }).build();

            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .serializeNulls()
                    .create();
            retrofit = new Retrofit.Builder()
                    .client(httpClient)
                    .baseUrl(BaseURL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    public static UserBean getLoginUser(Context c) {
        SharedPreferences loginInfo = c.getSharedPreferences("loginInfo", MODE_PRIVATE);
        UserBean user = null;
        /* 若登录 */
        if (!loginInfo.getString("token", "").equals("")) {
            /* Date类型为时间戳， 定义转换  !!!!!!*/
            try {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) -> new Date((json.getAsJsonPrimitive().getAsLong())));
                Gson gson = gsonBuilder.create();

                user = gson.fromJson(loginInfo.getString("user", ""), UserBean.class);

            } catch (Exception e) {
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                user = gson.fromJson(loginInfo.getString("user", ""), UserBean.class);
            }
        }else {
            ToastUtils.showLongToast(c, "请先登录");
            return null;
        }

        return user;
    }

    public static void setContext(Context context) {
        RequestUtil.context = context;
    }
}
