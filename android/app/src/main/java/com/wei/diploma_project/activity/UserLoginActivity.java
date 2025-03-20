package com.wei.diploma_project.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wei.diploma_project.R;
import com.wei.diploma_project.api.UserApi;
import com.wei.diploma_project.util.RequestUtil;
import com.wei.diploma_project.util.TransparentBar;

import org.json.JSONObject;

import java.io.File;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLoginActivity extends AppCompatActivity {

    private EditText loginname;
    private EditText password;
    private Button loginBtn;

    private TextView toRegisterBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransparentBar.transparent(this);
        setContentView(R.layout.user_login);
        TransparentBar.fillStatusBar(this);

        initView();
        initEvent();
    }

    private void initEvent() {
        loginBtn.setOnClickListener(v -> {
            RequestUtil.getRetrofit().create(UserApi.class).doLogin(loginname.getText().toString(), password.getText().toString())
                    .enqueue(new Callback<ResponseBody>() {
                        @SuppressLint("CommitPrefEdits")
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                String json = new String(response.body().bytes());
                                JSONObject jsonObject = new JSONObject(json);

                                if (!jsonObject.getString("code").equals("200")) {
                                    Toast.makeText(UserLoginActivity.this, "登录失败！", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(UserLoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                                /* 取数据  */
//                                SharedPreferences loginInfo = getSharedPreferences("loginInfo", MODE_PRIVATE);
                            } catch (Exception e) {
                                System.err.println(e);
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
        });

        toRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserLoginActivity.this, UserRegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        loginname = findViewById(R.id.loginname);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        toRegisterBtn = findViewById(R.id.toRegisterBtn);
        toRegisterBtn.getPaint().setFlags( Paint.UNDERLINE_TEXT_FLAG );
    }
}
