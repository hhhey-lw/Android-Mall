package com.wei.diploma_project.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonParser;
import com.wei.diploma_project.R;
import com.wei.diploma_project.api.CartApi;
import com.wei.diploma_project.api.GoodApi;
import com.wei.diploma_project.bean.GoodBean;
import com.wei.diploma_project.bean.UserBean;
import com.wei.diploma_project.util.RequestUtil;
import com.wei.diploma_project.util.TransparentBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/* 主页点击功能按钮 进入的对应类型的页面 */
public class GoodSingleShowActivity extends AppCompatActivity {
//    数据
    private GoodBean goodBean;
    private UserBean user;
//    组件
    private ImageView idSingleGoodImage;
    private TextView idSingleGoodName;
    private TextView idSingleGoodPrice;
    private ImageButton idBackBtn;
    private ImageButton idJoinCart;

    private TextView idGoCommentBtn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransparentBar.transparent(this);
        setContentView(R.layout.good_single_show);
        TransparentBar.fillStatusBar(this);

        initView();
        initEvent();

        int goodId;
        /* 提取传递的数据 */
        try {
            Intent intent = getIntent();
            goodId = Integer.parseInt(intent.getStringExtra("goodId"));
            //  拿数据
            getGoodInfo(goodId);
        }catch (Exception e) {
            e.printStackTrace();
            System.err.println("sorry no info");
        }
    }

    private void initEvent() {
        idBackBtn.setOnClickListener(v -> {
            onBackPressed();
        });
        user = RequestUtil.getLoginUser(this);
        if (user == null)
            return;
        /* 前端@Body RequestBody 后端@RequestBody接收 */
        idJoinCart.setOnClickListener(v -> {
            /* 构建数据 */
            HashMap<String, Integer> data = new HashMap<>();
            data.put("uid", user.getUid());
            data.put("gid", goodBean.getGid());
            data.put("gpurchaseNumber", 1);
            data.put("gstatus", goodBean.getGstatus());
            /* 将数据放入请求体中 */
            String json = new Gson().toJson(data);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),json);

            RequestUtil.getRetrofit().create(CartApi.class)
                    .addGoodToCart(body)
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                /* 拿到Result的JSON串 取出其中数据data json -> JSONObject */
                                String json = new String(response.body().bytes());
                                JSONObject jsonObject = new JSONObject(json);
                                String code = jsonObject.getString("code");
                                if (code.equals("200"))
                                    Toast.makeText(GoodSingleShowActivity.this, "添加成功！", Toast.LENGTH_SHORT).show();
                                else

                                    Toast.makeText(GoodSingleShowActivity.this, "添加失败！", Toast.LENGTH_SHORT).show();
                            } catch (IOException | JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
        });

        idGoCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), GoodCommentActivity.class);
                intent.putExtra("gid", goodBean.getGid()+"");
                startActivity(intent);
            }
        });
    }

    private void initView() {
        idSingleGoodImage = findViewById(R.id.idSingleGoodImage);
        idSingleGoodName = findViewById(R.id.idSingleGoodName);
        idSingleGoodPrice = findViewById(R.id.idSingleGoodPrice);
        idBackBtn = findViewById(R.id.idBackBtn);
        idJoinCart = findViewById(R.id.idJoinCart);
        idGoCommentBtn = findViewById(R.id.idGoCommentBtn);
    }

    //  请求商品信息
    private void getGoodInfo(int goodId) {
        RequestUtil.getRetrofit().create(GoodApi.class).getGoodById(goodId).enqueue(new Callback<GoodBean>() {
            @Override
            public void onResponse(Call<GoodBean> call, Response<GoodBean> response) {
                goodBean = response.body();
                initViewInfo();
            }

            @Override
            public void onFailure(Call<GoodBean> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    //    讲获取的商品信息赋值到页面
    private void initViewInfo() {
        idSingleGoodName.setText(goodBean.getGname());
        idSingleGoodPrice.setText("￥" + goodBean.getGprimalPrice());
        Glide.with(idSingleGoodImage).load(RequestUtil.BaseURL + goodBean.getGimage()).into(idSingleGoodImage);
    }

}
