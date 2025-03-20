package com.wei.diploma_project.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.wei.diploma_project.adapter.UserAddressAdapter;
import com.wei.diploma_project.api.AddressApi;
import com.wei.diploma_project.bean.AddressBean;
import com.wei.diploma_project.bean.CartBean;
import com.wei.diploma_project.bean.UserBean;
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

public class UserAddressActivity extends AppCompatActivity {
    private RecyclerView idUserAddressView;
    private UserAddressAdapter userAddressAdapter;
    private Button idAddUserAddrBtn;

    private UserBean user;
    private List<AddressBean> mData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransparentBar.transparent(this);
        setContentView(R.layout.user_address);
        TransparentBar.fillStatusBar(this);

        initView();
        initInfo();
        initEvent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initInfo();
    }

    public void initInfo() {
        user = RequestUtil.getLoginUser(this);
        if (user == null)
            return;
        RequestUtil.getRetrofit().create(AddressApi.class).getAddress(user.getUid()).enqueue(
                new Callback<ResponseBody>() {
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

                            List<AddressBean> data = new ArrayList<>();

                            /* 可解析 以下格式的 Date */
                            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                            for (JsonElement e : array) {
                                data.add(gson.fromJson(e, AddressBean.class));
                            }
                            System.err.println(data);

                            mData = data;
                            userAddressAdapter.setLocalDataSet(data);
                            idUserAddressView.setAdapter(userAddressAdapter);
                            userAddressAdapter.notifyDataSetChanged();

                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                }
        );
    }

    private void initEvent() {
        if (user == null) {
            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
            return;
        }
        idAddUserAddrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), UserAddressEditActivity.class);
                intent.putExtra("action", "add");
                startActivity(intent);
            }
        });

        try {
            Intent intent = getIntent();
            String action = intent.getStringExtra("action");
            if (action.equals("choose"))
                userAddressAdapter.setShowUseBtn(true);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        idUserAddressView = findViewById(R.id.idUserAddressView);
        userAddressAdapter = new UserAddressAdapter(this);
        idUserAddressView.setLayoutManager(new LinearLayoutManager(this));

        idAddUserAddrBtn = findViewById(R.id.idAddUserAddrBtn);
    }
}
