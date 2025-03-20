package com.wei.diploma_project.activity;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.wei.diploma_project.R;
import com.wei.diploma_project.adapter.OrderDetailListAdapter;
import com.wei.diploma_project.adapter.OrderListAdapter;
import com.wei.diploma_project.api.OrderApi;
import com.wei.diploma_project.bean.GoodBean;
import com.wei.diploma_project.bean.OrderBean;
import com.wei.diploma_project.bean.OrderBrief;
import com.wei.diploma_project.bean.OrderItem;
import com.wei.diploma_project.bean.UserBean;
import com.wei.diploma_project.util.RequestUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* 订单列表 */
public class OrderActivity extends AppCompatActivity {
    private RecyclerView orderListView;
    private OrderListAdapter orderListAdapter;

    /* 功能栏 */
    private TextView idTextBtn1;
    private TextView idTextBtn2;
    private TextView idTextBtn3;
    private TextView idTextBtn4;
    private TextView idTextBtn5;

    /* 数据集 */
    private List<OrderBrief> dataList;
    private UserBean user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_all);

        initView();
        initEvent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
        idTextBtn1.performClick(); // 预点击一下
    }

    private void initData() {

        SharedPreferences loginInfo = getSharedPreferences("loginInfo", MODE_PRIVATE);
        /* 若登录 token不为空 拿用户所有订单 */
        if (!loginInfo.getString("token", "").equals("")) {
            user = RequestUtil.getLoginUser(this);

            RequestUtil.getRetrofit().create(OrderApi.class).getAllOrder(user.getUid()).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        /* 转json */
                        String json = new String(response.body().bytes());
                        JSONObject jsonObject = new JSONObject(json);

                        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                        JsonParser parser = new JsonParser();
                        JsonArray array = parser.parse(jsonObject.getString("data")).getAsJsonArray();

                        dataList = new ArrayList<>();
                        for (JsonElement e : array) {
                            OrderBrief brief = gson.fromJson(e, OrderBrief.class);
                            dataList.add(brief);
                        }
                        /* 渲染数据 */
                        orderListAdapter.setData(dataList);

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

    }


    private void initEvent() {
        idTextBtn1.setOnClickListener(getClickEvent());
        idTextBtn2.setOnClickListener(getClickEvent());
        idTextBtn3.setOnClickListener(getClickEvent());
        idTextBtn4.setOnClickListener(getClickEvent());
        idTextBtn5.setOnClickListener(getClickEvent());
    }

    private View.OnClickListener getClickEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedToFalse();
                switch (v.getId()) {
                    case R.id.idTextBtn1: {
                        idTextBtn1.setSelected(true);
                        orderListAdapter.setData(dataList);
                        break;
                    }
                    case R.id.idTextBtn2: {
                        idTextBtn2.setSelected(true);
                        List<OrderBrief> temp = new ArrayList<>();
                        for (OrderBrief o : dataList) {
                            if (o.getOstatus() == 1)    // 待付款
                                temp.add(o);
                        }
                        orderListAdapter.setData(temp);
                        break;
                    }
                    case R.id.idTextBtn3: {
                        idTextBtn3.setSelected(true);
                        List<OrderBrief> temp = new ArrayList<>();
                        for (OrderBrief o : dataList) {
                            if (o.getOstatus() == 2)    // 待发货
                                temp.add(o);
                        }
                        orderListAdapter.setData(temp);
                        break;
                    }
                    case R.id.idTextBtn4: {
                        idTextBtn4.setSelected(true);
                        List<OrderBrief> temp = new ArrayList<>();
                        for (OrderBrief o : dataList) {
                            if (o.getOstatus() == 3)    // 待收货
                                temp.add(o);
                        }
                        orderListAdapter.setData(temp);
                        break;
                    }
                    case R.id.idTextBtn5: {
                        idTextBtn5.setSelected(true);
                        List<OrderBrief> temp = new ArrayList<>();
                        for (OrderBrief o : dataList) {
                            if (o.getOstatus() == 4)    // 待评价
                                temp.add(o);
                        }
                        orderListAdapter.setData(temp);
                        break;
                    }
                }
            }
        };
    }

    private void initView() {
        orderListView = findViewById(R.id.idOrderView);
        orderListAdapter = new OrderListAdapter();
        orderListView.setAdapter(orderListAdapter);
        orderListView.setLayoutManager(new LinearLayoutManager(this));

        idTextBtn1 = findViewById(R.id.idTextBtn1);
        idTextBtn2 = findViewById(R.id.idTextBtn2);
        idTextBtn3 = findViewById(R.id.idTextBtn3);
        idTextBtn4 = findViewById(R.id.idTextBtn4);
        idTextBtn5 = findViewById(R.id.idTextBtn5);
    }

    // 重置所有文本的选中状态 这个用来切换TarBar Selected or Not 样式
    private void setSelectedToFalse(){
        idTextBtn1.setSelected(false);
        idTextBtn2.setSelected(false);
        idTextBtn3.setSelected(false);
        idTextBtn4.setSelected(false);
        idTextBtn5.setSelected(false);
    }
}
