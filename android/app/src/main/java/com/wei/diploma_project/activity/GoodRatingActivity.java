package com.wei.diploma_project.activity;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.wei.diploma_project.R;
import com.wei.diploma_project.adapter.RatingViewAdapter;
import com.wei.diploma_project.api.OrderApi;
import com.wei.diploma_project.bean.GoodBean;
import com.wei.diploma_project.bean.OrderBean;
import com.wei.diploma_project.bean.OrderItem;
import com.wei.diploma_project.util.RequestUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GoodRatingActivity extends AppCompatActivity {
    // 组件
    private RecyclerView idRatingView;
    private RatingViewAdapter adapter;

    // 页面传递
    int oid;
    /* 网络数据  */
    OrderBean orderBean;
    List<OrderItem> itemList;
    List<GoodBean> goodList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.good_rating);

        initView();
        initInfo();
        initEvent();
    }

    public void initInfo() {
        Intent intent = getIntent();
        oid = intent.getIntExtra("oid", -1);
        RequestUtil.getRetrofit().create(OrderApi.class).getOrderInfo(oid).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String json = new String(response.body().bytes());
                    JSONObject jsonObject = new JSONObject(json);
                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

                    JSONObject data = jsonObject.getJSONObject("data");
                    /* 订单对象 */
                    orderBean = gson.fromJson(data.getString("order"), OrderBean.class);

                    JsonParser parser = new JsonParser();
                    JsonArray item = parser.parse(data.getString("item")).getAsJsonArray();
                    JsonArray good = parser.parse(data.getString("good")).getAsJsonArray();
                    /* 列表对象 */
                    itemList = new ArrayList<>();
                    goodList = new ArrayList<>();

                    /* 可解析 以下格式的 Date */
                    for (JsonElement e : item) {
                        itemList.add(gson.fromJson(e, OrderItem.class));
                    }
                    for (JsonElement e : good) {
                        goodList.add(gson.fromJson(e, GoodBean.class));
                    }

                    // 绑定
                    idRatingView.setAdapter(adapter);
                    adapter.setData(oid, orderBean.getUid(), itemList, goodList);

                }catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void initEvent() {

    }

    private void initView() {
        idRatingView = findViewById(R.id.idRatingView);
        idRatingView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RatingViewAdapter(this);
    }
}
