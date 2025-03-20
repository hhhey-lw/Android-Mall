package com.wei.diploma_project.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.wei.diploma_project.R;
import com.wei.diploma_project.api.AddressApi;
import com.wei.diploma_project.bean.AddressBean;
import com.wei.diploma_project.bean.UserBean;
import com.wei.diploma_project.util.RequestUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAddressEditActivity extends AppCompatActivity {
    //申明对象
    private CityPickerView mPicker = new CityPickerView();

    private Button idSubmitAddress;
    private UserBean user;
    private AddressBean mAddr;

    private EditText idAddressUsername;
    private EditText idAddressPhone;
    private EditText idAddressProvince;
    private EditText idAddressCity;
    private EditText idAddressDistrict;
    private EditText idAddressDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_address_edit);

        initView();
        initEvent();
        initData();
    }

    private void initData() {
        try {
            Intent intent = getIntent();
            if (intent.getStringExtra("action").equals("add")) {
                // 添加 ...
                idAddressUsername.setText(user.getUsername());
                idAddressPhone.setText(user.getPhone());
                idSubmitAddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AddressBean e = new AddressBean();
                        e.setUid(user.getUid());
                        e.setUsername(idAddressUsername.getText().toString());
                        e.setPhone(idAddressPhone.getText().toString());
                        e.setAddrProvince(idAddressProvince.getText().toString());
                        e.setAddrCity(idAddressCity.getText().toString());
                        e.setAddrDistrict(idAddressDistrict.getText().toString());
                        e.setAddrDetail(idAddressDetail.getText().toString());

                        RequestUtil.getRetrofit().create(AddressApi.class).addAddress(e).enqueue(
                                new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        try {
                                            String jstr = new String(response.body().bytes());
                                            JSONObject jsonObject = new JSONObject(jstr);

                                            if (jsonObject.getString("code").equals("200")) {
                                                Intent intent = new Intent(v.getContext(), UserAddressActivity.class);
                                                v.getContext().startActivity(intent);
                                            }
                                        } catch (IOException | JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        t.printStackTrace();
                                    }
                                }
                        );
                    }
                });
            }
            else if (intent.getStringExtra("action").equals("update")) {
                // 修改
                int addr_id = Integer.parseInt(intent.getStringExtra("addr_id"));
                RequestUtil.getRetrofit().create(AddressApi.class).getSingleAddress(addr_id).enqueue(
                        new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                try {
                                    /* 拿到Result的JSON串 取出其中数据data */
                                    String json = new String(response.body().bytes());
                                    JSONObject jsonObject = new JSONObject(json);
                                    String j = jsonObject.getString("data");
                                    /* 可解析 以下格式的 Date */
                                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                                    mAddr = gson.fromJson(j, AddressBean.class);
                                    updateViewText();
                                } catch (JSONException | IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                t.printStackTrace();
                            }
                        }
                );
                idSubmitAddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!idAddressUsername.getText().toString().trim().equals(""))
                            mAddr.setUsername(idAddressUsername.getText().toString().trim());
                        if (!idAddressPhone.getText().toString().trim().equals(""))
                            mAddr.setPhone(idAddressPhone.getText().toString().trim());
                        if (!idAddressProvince.getText().toString().trim().equals(""))
                            mAddr.setAddrProvince(idAddressProvince.getText().toString().trim());
                        if (!idAddressCity.getText().toString().trim().equals(""))
                            mAddr.setAddrCity(idAddressCity.getText().toString().trim());
                        if (!idAddressDistrict.getText().toString().trim().equals(""))
                            mAddr.setAddrDistrict(idAddressDistrict.getText().toString().trim());
                        if (!idAddressDetail.getText().toString().trim().equals(""))
                            mAddr.setAddrDetail(idAddressDetail.getText().toString().trim());
                        RequestUtil.getRetrofit().create(AddressApi.class).updateAddress(mAddr)
                                .enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        try {
                                            /* 拿到Result的JSON串 取出其中数据data */
                                            String json = new String(response.body().bytes());
                                            JSONObject jsonObject = new JSONObject(json);

                                            if (jsonObject.getString("code").equals("200")) {
                                                Toast.makeText(UserAddressEditActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                                                v.getContext().startActivity(
                                                        new Intent(v.getContext(), UserAddressActivity.class)
                                                );
                                            }
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
                });
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateViewText() {
        idAddressUsername.setText(mAddr.getUsername());
        idAddressPhone.setText(mAddr.getPhone());
        idAddressProvince.setText(mAddr.getAddrProvince());
        idAddressCity.setText(mAddr.getAddrCity());
        idAddressDistrict.setText(mAddr.getAddrDistrict());
        idAddressDetail.setText(mAddr.getAddrDetail());
    }

    private void initEvent() {
        idAddressProvince.setOnClickListener(getClickEvent());
        idAddressCity.setOnClickListener(getClickEvent());
        idAddressDistrict.setOnClickListener(getClickEvent());
    }

    private View.OnClickListener getClickEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加默认的配置，不需要自己定义，当然也可以自定义相关熟悉，详细属性请看demo
                CityConfig cityConfig = new CityConfig.Builder().build();
                mPicker.setConfig(cityConfig);

                //监听选择点击事件及返回结果
                mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
                    @Override
                    public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                        //省份province
                        //城市city
                        //地区district
                        idAddressProvince.setText(province.getName());
                        idAddressCity.setText(city.getName());;
                        idAddressDistrict.setText(district.getName());;
                    }

                    @Override
                    public void onCancel() {
                        ToastUtils.showLongToast(v.getContext(), "已取消");
                    }
                });

                //显示
                mPicker.showCityPicker();
            }
        };
    }

    @Override
    protected void onPause() {
        super.onPause();
//        finish();
    }

    private void initView() {
        //预先加载仿iOS滚轮实现的全部数据
        mPicker.init(this);
        idAddressUsername = findViewById(R.id.idAddressUsername);
        idAddressPhone = findViewById(R.id.idAddressPhone);
        idAddressProvince = findViewById(R.id.idAddressProvince);
        idAddressCity = findViewById(R.id.idAddressCity);
        idAddressDistrict = findViewById(R.id.idAddressDistrict);
        idAddressDetail = findViewById(R.id.idAddressDetail);
        idSubmitAddress = findViewById(R.id.idSubmitAddress);

        user = RequestUtil.getLoginUser(this);
    }
}
