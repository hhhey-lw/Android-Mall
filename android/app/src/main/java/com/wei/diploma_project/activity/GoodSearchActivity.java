package com.wei.diploma_project.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.wei.diploma_project.adapter.HomeGoodListAdapter;
import com.wei.diploma_project.adapter.SaleBestListAdapter;
import com.wei.diploma_project.api.GoodApi;
import com.wei.diploma_project.api.OrderApi;
import com.wei.diploma_project.bean.GoodBean;
import com.wei.diploma_project.bean.OrderBrief;
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

public class GoodSearchActivity extends AppCompatActivity {
    // 视图 & 适配器
    private RecyclerView goodListView;
    private RecyclerView saleBestView;

    private GoodListShowAdapter goodListAdapter;
    private SaleBestListAdapter saleBestListAdapter;
    // 组件
    private EditText InputText;
    private ImageView SearchBtn;
    // 搜索数据
    private List<GoodBean> dataSet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.good_search);

        initView();

        initEvent();

        initInfo();
    }

    private void initInfo() {
        RequestUtil.getRetrofit().create(GoodApi.class).getSaleBestList().enqueue(new Callback<ResponseBody>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    /* 拿到Result的JSON串 取出其中数据data */
                    String json = new String(response.body().bytes());
                    JSONObject jsonObject = new JSONObject(json);
                    String j = jsonObject.getString("data");

                    /* data 为数组类型  */
                    JsonParser parser = new JsonParser();
                    JsonArray array = parser.parse(j).getAsJsonArray();

                    ArrayList<GoodBean> dataList = new ArrayList<>();
                    /* 可解析 以下格式的 Date */
                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                    for (JsonElement e : array) {
//                        System.err.println(e);
                        dataList.add(gson.fromJson(e, GoodBean.class));
                    }

                    saleBestListAdapter.setLocalDataSet(dataList);
                    saleBestListAdapter.notifyDataSetChanged();

                } catch (JSONException | IOException e) {
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
        SearchBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                String searchText = InputText.getText().toString().trim();
                if (searchText.equals("")) {
                    Toast.makeText(GoodSearchActivity.this, "商品名称不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
//                Toast.makeText(GoodSearchActivity.this, searchText, Toast.LENGTH_SHORT).show();
                SearchData(searchText);
            }
        });
    }

    private void SearchData(String searchText) {
        if (dataSet == null)
            dataSet = new ArrayList<>();
        dataSet.clear();

        RequestUtil.getRetrofit().create(GoodApi.class).getGoodByNameLike(searchText).enqueue(new Callback<ResponseBody>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    /* 转json */
                    String json = new String(response.body().bytes());
                    JSONObject jsonObject = new JSONObject(json);

                    System.err.println(jsonObject);
                    if (jsonObject.getString("code").equals("500")) {
                        Toast.makeText(GoodSearchActivity.this, "暂无该商品", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                    JsonParser parser = new JsonParser();
                    JsonArray array = parser.parse(jsonObject.getString("data")).getAsJsonArray();

                    for (JsonElement e : array) {
                        GoodBean o = gson.fromJson(e, GoodBean.class);
                        dataSet.add(o);
                    }

                    if (dataSet.size() == 0) {
                        Toast.makeText(GoodSearchActivity.this, "没有该商品", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    goodListAdapter.setLocalDataSet(dataSet);
                    goodListAdapter.notifyDataSetChanged();

                }catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
//        异步问题
//        System.err.println(localList.size());
//        return localList;
    }

    private void initView() {
        goodListView = findViewById(R.id.idShowGoodList);
        goodListAdapter = new GoodListShowAdapter();
        goodListView.setLayoutManager(new LinearLayoutManager(this));
        goodListView.setAdapter(goodListAdapter);

        saleBestView = findViewById(R.id.saleBestView);
        saleBestListAdapter = new SaleBestListAdapter();
        saleBestView.setLayoutManager(new LinearLayoutManager(this));
        saleBestView.setAdapter(saleBestListAdapter);

        InputText = findViewById(R.id.idInputText);
        SearchBtn = findViewById(R.id.idSearchBtn);
    }
}
