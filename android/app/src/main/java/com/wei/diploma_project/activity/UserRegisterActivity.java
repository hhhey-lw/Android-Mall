package com.wei.diploma_project.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wei.diploma_project.R;
import com.wei.diploma_project.api.UserApi;
import com.wei.diploma_project.util.RequestUtil;

import org.json.JSONObject;

import java.io.File;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRegisterActivity extends AppCompatActivity {

    private Button loginBtn;
    private Button codeBtn;

    private boolean ableSend = true;
    private int waitMill = 60;

    private EditText phoneNumberView;
    private EditText codeView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_register);

        initView();
        initEvent();
    }

    private void initEvent() {
        codeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ableSend == false) {
                    Toast.makeText(UserRegisterActivity.this, "请等待"+waitMill+"后再点击发送", Toast.LENGTH_SHORT).show();
                    return;
                }

                String phoneNumber = phoneNumberView.getText().toString().trim();
                if (!verifyPhoneNumber(phoneNumber)) {
                    Toast.makeText(UserRegisterActivity.this, "请输入正确的手机号！", Toast.LENGTH_SHORT).show();
                    phoneNumberView.setText("");
                    return;
                }

//                disableBtn();
                RequestUtil.getRetrofit().create(UserApi.class).sendCode(phoneNumber).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String jstr = new String(response.body().bytes());
                            JSONObject jsonObject = new JSONObject(jstr);

                            if (jsonObject.getString("code").equals("200")) {
                                Toast.makeText(UserRegisterActivity.this, "发送成功！", Toast.LENGTH_SHORT).show();

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        System.err.println(t.getMessage());
                    }
                });
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = phoneNumberView.getText().toString().trim();
                if (!verifyPhoneNumber(phoneNumber)) {
                    Toast.makeText(UserRegisterActivity.this, "请输入正确的手机号！", Toast.LENGTH_SHORT).show();
                    phoneNumberView.setText("");
                    return;
                }
                String code = codeView.getText().toString().trim();
                if (code.equals("") || code.length() != 4) {
                    Toast.makeText(UserRegisterActivity.this, "请输入正确的验证码！", Toast.LENGTH_SHORT).show();
                    return;
                }
                RequestUtil.getRetrofit().create(UserApi.class).loginByCode(code, phoneNumber).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String json = new String(response.body().bytes());
                            JSONObject jsonObject = new JSONObject(json);

                            if (!jsonObject.getString("code").equals("200")) {
                                Toast.makeText(UserRegisterActivity.this, "登录失败！", Toast.LENGTH_SHORT).show();
                                return;
                            }

//                                System.err.println("token : " + jsonObject.getJSONObject("data").getString("token"));
                            File file = new File(getFilesDir(), "loginInfo.txt");
                            if (!file.exists()) {
                                file.createNewFile();
                            }

                            /* 存数据且提交 */
                            SharedPreferences.Editor edit = getSharedPreferences("loginInfo", MODE_PRIVATE).edit();
                            edit.putString("token", jsonObject.getJSONObject("data").getString("token"));
                            edit.putString("user", jsonObject.getJSONObject("data").getString("user"));
                            edit.commit();
                            Toast.makeText(UserRegisterActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UserRegisterActivity.this, App.class);
                            startActivity(intent);
                            /* 取数据  */
//                                SharedPreferences loginInfo = getSharedPreferences("loginInfo", MODE_PRIVATE);
                        } catch (Exception e) {
                            System.err.println(e);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void disableBtn() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ableSend = false;
                // 线程需要执行的任务代码
                System.out.println("子线程开始启动....");
                for (int i = 60; i > 0; i--) {
                    System.err.println("循环 " + i);
                    waitMill = i;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                ableSend = true;
            }
        };
        new Thread(runnable).start();
    }

    private void initView() {
        loginBtn = findViewById(R.id.loginBtn);
        codeBtn = findViewById(R.id.codeBtn);
        phoneNumberView = findViewById(R.id.phoneNumber);
        codeView = findViewById(R.id.codeView);
    }

    private boolean verifyPhoneNumber(String phoneNumber) {
        if ((phoneNumber != null) && (!phoneNumber.isEmpty())) {
            return Pattern.matches("^1[3-9]\\d{9}$", phoneNumber);
        }
        return false;
    }
}
