package com.wei.diploma_project.activity;

import android.content.Intent;
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
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.wei.diploma_project.R;
import com.wei.diploma_project.adapter.CommentListAdapter;
import com.wei.diploma_project.api.CommentApi;
import com.wei.diploma_project.bean.CartBean;
import com.wei.diploma_project.bean.CommentBean;
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

public class GoodCommentActivity extends AppCompatActivity {

    private RecyclerView idShowCommentList;
    private CommentListAdapter commentListAdapter;

    private List<CommentBean> mData;

    /* 功能栏 */
    private TextView idTextBtn1;
    private TextView idTextBtn2;
    private TextView idTextBtn3;
    private TextView idTextBtn4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.good_comment);

        initView();
        initEvent();
        initInfo();
        idTextBtn1.performClick();
    }

    private void initInfo() {
        Intent intent = getIntent();
        Integer gid = Integer.valueOf(intent.getStringExtra("gid"));
        // 根据商品ID拿对应LIST评论
        RequestUtil.getRetrofit().create(CommentApi.class).getCommentByGid(gid).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    /* 拿到Result的JSON串 取出其中数据data */
                    String json = new String(response.body().bytes());
                    JSONObject jsonObject = new JSONObject(json);
                    String j = jsonObject.getString("data");
//                    System.err.println("data : " + j);
                    /* data 为数组类型  */
                    JsonParser parser = new JsonParser();
                    JsonArray array = parser.parse(j).getAsJsonArray();

                    List<CommentBean> data = new ArrayList<>();

                    /* 可解析 以下格式的 Date */
                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                    for (JsonElement e : array) {
                        data.add(gson.fromJson(e, CommentBean.class));
                    }
                    mData = data;
                    commentListAdapter.setData(mData);
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
        idTextBtn1.setOnClickListener(getClickEvent());
        idTextBtn2.setOnClickListener(getClickEvent());
        idTextBtn3.setOnClickListener(getClickEvent());
        idTextBtn4.setOnClickListener(getClickEvent());
    }

    private void initView() {
        idShowCommentList = findViewById(R.id.idShowCommentList);
        idShowCommentList.setLayoutManager(new LinearLayoutManager(this));
        commentListAdapter = new CommentListAdapter();
        idShowCommentList.setAdapter(commentListAdapter);

        idTextBtn1 = findViewById(R.id.idTextBtn1);
        idTextBtn2 = findViewById(R.id.idTextBtn2);
        idTextBtn3 = findViewById(R.id.idTextBtn3);
        idTextBtn4 = findViewById(R.id.idTextBtn4);
    }

    // 切换功能项
    private View.OnClickListener getClickEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedToFalse();
                switch (v.getId()) {
                    case R.id.idTextBtn1: {
                        idTextBtn1.setSelected(true);
                        commentListAdapter.setData(mData);
                        break;
                    }
                    case R.id.idTextBtn2: {
                        idTextBtn2.setSelected(true);
                        commentListAdapter.setData(getDegree(5)); // 5星好评
                        break;
                    }
                    case R.id.idTextBtn3: {
                        idTextBtn3.setSelected(true);
                        commentListAdapter.setData(getDegree4_2()); // 4-2星
                        break;
                    }
                    case R.id.idTextBtn4: {
                        idTextBtn4.setSelected(true);
                        commentListAdapter.setData(getDegree(1)); // 1 星差评
                        break;
                    }
                }
            }
        };
    }
    // 按评分拿对应 LIST DATA
    private List<CommentBean> getDegree(int rating) {
        List<CommentBean> data = new ArrayList<>();
        for (CommentBean e : mData) {
            if (e.getCdegree() == rating)
                data.add(e);
        }
        return data;
    }
    // 中评
    private List<CommentBean> getDegree4_2() {
        List<CommentBean> data = new ArrayList<>();
        for (CommentBean e : mData) {
            if (e.getCdegree() < 5 && e.getCdegree() > 1)
                data.add(e);
        }
        return data;
    }

    // 重置所有文本的选中状态 这个用来切换TarBar Selected or Not 样式
    private void setSelectedToFalse(){
        idTextBtn1.setSelected(false);
        idTextBtn2.setSelected(false);
        idTextBtn3.setSelected(false);
        idTextBtn4.setSelected(false);
    }
}
