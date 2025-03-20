package com.wei.diploma_project.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
import com.wei.diploma_project.adapter.GoodListShowAdapter;
import com.wei.diploma_project.api.GoodApi;
import com.wei.diploma_project.bean.CartBean;
import com.wei.diploma_project.bean.GoodBean;
import com.wei.diploma_project.util.RequestUtil;
import com.wei.diploma_project.util.TransparentBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* 主页点击功能按钮 进入的对应类型的页面 */
public class GoodListShowActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GoodListShowAdapter goodListShowAdapter;

    private List<GoodBean> dataSet;

    /* 切换顺序 */
    private TextView txtBtn1;
    private TextView txtBtn2;
    private TextView txtBtn3;
    private TextView txtBtn4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransparentBar.transparent(this);
        setContentView(R.layout.good_list_show);
        TransparentBar.fillStatusBar(this);
        int index = 2;
        try {
            Intent intent = getIntent();
            index = intent.getIntExtra("gid", 2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        initView();
        initEvent();
        initData(index);

        /* 填充数据 */
        recyclerView = findViewById(R.id.idShowGoodList);
        goodListShowAdapter = new GoodListShowAdapter();

        recyclerView.setAdapter(goodListShowAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /* 提取传递的数据 */
        Intent intent = getIntent();
        System.err.println(intent.getStringExtra("title"));
    }

    private void initData(int index) {
        RequestUtil.getRetrofit().create(GoodApi.class).getGoodByCategory(index).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    /* 拿到Result的JSON串 取出其中数据data */
                    String json = new String(response.body().bytes());
                    JSONObject jsonObject = new JSONObject(json);
                    String j = jsonObject.getString("data");
                    System.err.println(j);
                    /* data 为数组类型  */
                    JsonParser parser = new JsonParser();
                    JsonArray array = parser.parse(j).getAsJsonArray();

                    List<GoodBean> data = new ArrayList<>();

                    /* 可解析 以下格式的 Date */
                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                    for (JsonElement e : array) {
                        data.add(gson.fromJson(e, GoodBean.class));
                    }

                    dataSet = data;
                    goodListShowAdapter.setLocalDataSet(dataSet);
                    goodListShowAdapter.notifyDataSetChanged();
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                goodListShowAdapter.setLocalDataSet(getLocalDataSet());
                goodListShowAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setUnselectedBtn() {
        txtBtn1.setSelected(false);
        txtBtn2.setSelected(false);
        txtBtn3.setSelected(false);
        txtBtn4.setSelected(false);
    }

    private void initEvent() {
        txtBtn1.setOnClickListener(getClickEvent());
        txtBtn2.setOnClickListener(getClickEvent());
        txtBtn3.setOnClickListener(getClickEvent());
        txtBtn4.setOnClickListener(getClickEvent());
        txtBtn1.performClick();
    }

    private View.OnClickListener getClickEvent() {
        return v -> {
            setUnselectedBtn();
            v.setSelected(true);
            Toast.makeText(GoodListShowActivity.this, "点击：" + ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
        };
    }

    private void initView() {
        txtBtn1 = findViewById(R.id.idTextBtn1);
        txtBtn2 = findViewById(R.id.idTextBtn2);
        txtBtn3 = findViewById(R.id.idTextBtn3);
        txtBtn4 = findViewById(R.id.idTextBtn4);
    }

    private List<GoodBean> getLocalDataSet() {
        List<GoodBean> goodBeans = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            goodBeans.add(new GoodBean("超级无敌商品超级无敌商品超级无敌商品超级无敌商品超级无敌商品超" + i, i * 3 + .9));
        }
        return goodBeans;
    }

}
