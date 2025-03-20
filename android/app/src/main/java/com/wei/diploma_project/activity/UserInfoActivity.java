package com.wei.diploma_project.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.wei.diploma_project.R;
import com.wei.diploma_project.api.UserApi;
import com.wei.diploma_project.bean.UserBean;
import com.wei.diploma_project.util.RequestUtil;
import com.wei.diploma_project.util.TransparentBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoActivity extends AppCompatActivity {

    private EditText idUserName;
    private EditText idPhone;
    private RadioGroup idGender;
    private Button idUpdateUserInfoBtn;

    private EditText idOldPassword;
    private EditText idPassword;
    private EditText idPasswordConfirm;
    private Button idModifyPwdBtn;

    private Button changeImgBtn;
    private Button submitAvatarBtn;
    private CircleImageView avatarImg; // 默认头像
    private ImageView testImg; // 更新后头像
    private Uri imgURI;

    private UserBean user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransparentBar.transparent(this);
        setContentView(R.layout.user_info);
        TransparentBar.fillStatusBar(this);

        initView();
        initInfo();
        initEvent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initInfo();
        clearPwd();
    }

    private void clearPwd() {
        idOldPassword.setText("");
        idPassword.setText("");
        idPasswordConfirm.setText("");
    }

    private void initInfo() {

        user = RequestUtil.getLoginUser(this);
        if (user == null) {
            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
            return;
        }

        idUserName.setText(user.getUsername());
        idPhone.setText(user.getPhone());
        switch (user.getGender()) {
            case 0: {
                idGender.check(R.id.idWoman);
                break;
            }
            case 1: {
                idGender.check(R.id.idMan);
                break;
            }
            default:
                idGender.check(R.id.idMan);
        }
        Glide.with(UserInfoActivity.this).load(RequestUtil.BaseURL + user.getAvatar()).into(avatarImg);
    }

    private void initEvent() {
        if (user == null) {
            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
            return;
        }
        // 修改信息
        idUpdateUserInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = idUserName.getText().toString().trim();
                String phone = idPhone.getText().toString().trim();
                int gender = 1;  // 0 女 1 男
                switch (idGender.getCheckedRadioButtonId()) {
                    case R.id.idMan: {
                        gender = 1;
                        break;
                    }
                    case R.id.idWoman: {
                        gender = 0;
                        break;
                    }
                }
//                Toast.makeText(UserInfoActivity.this, String.format("%s %s %d", username, phone, gender), Toast.LENGTH_SHORT).show();
                RequestUtil.getRetrofit().create(UserApi.class).updateUserInfo(user.getLoginname()
                ,username, phone, gender).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String jstr = new String(response.body().bytes());
                            JSONObject jsonObject = new JSONObject(jstr);

                            if (jsonObject.getString("code").equals("200"))
                                Toast.makeText(UserInfoActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();

//                            System.err.println(jsonObject.getString("data"));
                            /* Date类型为时间戳， 定义转换  !!!!!!*/
                            try {
                                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                                user = gson.fromJson(jsonObject.getString("data"), UserBean.class);
                            }catch (Exception e) {
                                GsonBuilder gsonBuilder = new GsonBuilder();
                                gsonBuilder.registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) -> new Date((json.getAsJsonPrimitive().getAsLong())));
                                Gson gson = gsonBuilder.create();

                                user = gson.fromJson(jsonObject.getString("data"), UserBean.class);
                            }

                            /* 存数据且提交 */
                            SharedPreferences.Editor edit = getSharedPreferences("loginInfo", MODE_PRIVATE).edit();
                            edit.putString("user", jsonObject.getString("data"));
                            edit.commit();

                            testImg.setVisibility(View.GONE);
                            avatarImg.setVisibility(View.VISIBLE);
                            initInfo();

                        } catch (IOException | JSONException e) {
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

        // 修改密码
        idModifyPwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd = idPassword.getText().toString().trim();
                String pwdConfirm = idPasswordConfirm.getText().toString().trim();
                String oldPwd = idOldPassword.getText().toString().trim();
                if (oldPwd.equals("")) {
                    Toast.makeText(UserInfoActivity.this, "请输入旧密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!pwd.equals(pwdConfirm)) {
                    Toast.makeText(UserInfoActivity.this, "密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                RequestUtil.getRetrofit().create(UserApi.class).modifyPwd(user.getLoginname(), oldPwd, pwd, pwdConfirm)
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                try {
                                    String jstr = new String(response.body().bytes());
                                    JSONObject jsonObject = new JSONObject(jstr);

                                    System.err.println(jsonObject);
                                    if (jsonObject.getString("code").equals("200")) {
                                        Toast.makeText(UserInfoActivity.this, "修改密码成功！", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(UserInfoActivity.this, "修改密码失败！", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    /* Date类型为时间戳， 定义转换  !!!!!!*/
                                    try {
                                        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                                        user = gson.fromJson(jsonObject.getString("data"), UserBean.class);
                                    }catch (Exception e) {
                                        GsonBuilder gsonBuilder = new GsonBuilder();
                                        gsonBuilder.registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) -> new Date((json.getAsJsonPrimitive().getAsLong())));
                                        Gson gson = gsonBuilder.create();

                                        user = gson.fromJson(jsonObject.getString("data"), UserBean.class);
                                    }

                                    /* 存数据且提交 */
                                    SharedPreferences.Editor edit = getSharedPreferences("loginInfo", MODE_PRIVATE).edit();
                                    edit.clear();
                                    edit.apply();

                                    Intent intent = new Intent(v.getContext(), UserLoginActivity.class);
                                    startActivity(intent);
                                } catch (IOException | JSONException e) {
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

        // 打开相册
        changeImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 99);
            }
        });

        submitAvatarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgURI == null) {
                    Toast.makeText(UserInfoActivity.this, "请选中更换的图片", Toast.LENGTH_SHORT).show();
                    return;
                }
                UserBean user = RequestUtil.getLoginUser(UserInfoActivity.this);
                if (user == null) {
                    Toast.makeText(UserInfoActivity.this, "请登录", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(UserInfoActivity.this, "正在上传", Toast.LENGTH_SHORT).show();
                //调用URL转file方法
                String filePath = getFilePathByFileUri(imgURI);
                System.err.println("路径 ： " + filePath);
                File file = new File(filePath);
//                return;
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part imageBodyPart = MultipartBody.Part.createFormData("imgFile", file.getName(), requestBody);
                RequestUtil.getRetrofit().create(UserApi.class).updateAvatar(imageBodyPart, user.getUid()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String j = new String(response.body().bytes());
                            JSONObject jsonObject = new JSONObject(j);

                            if (!jsonObject.getString("code").equals("200")) {
                                Toast.makeText(UserInfoActivity.this, "上传失败！", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Toast.makeText(UserInfoActivity.this, "上传成功！", Toast.LENGTH_SHORT).show();
                            UserBean user = null;
                            try {
                                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                                user = gson.fromJson(jsonObject.getString("data"), UserBean.class);
                            }catch (Exception e) {
                                GsonBuilder gsonBuilder = new GsonBuilder();
                                gsonBuilder.registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) -> new Date((json.getAsJsonPrimitive().getAsLong())));
                                Gson gson = gsonBuilder.create();

                                user = gson.fromJson(jsonObject.getString("data"), UserBean.class);
                            }

                            /* 存数据且提交 */
                            SharedPreferences.Editor edit = getSharedPreferences("loginInfo", MODE_PRIVATE).edit();
                            edit.putString("user", jsonObject.getString("data"));
                            edit.commit();

                            initInfo();
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

    /**
     * 根据文件Uri获取路径
     */
    @SuppressLint("Range")
    public String getFilePathByFileUri(Uri uri) {
        String filePath = null;
        Cursor cursor = getContentResolver().query(uri, null, null,
                null, null);
        if (cursor.moveToFirst()) {
            filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        }
        cursor.close();
        return filePath;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99) {
            Uri currentUri = data.getData();
            imgURI = currentUri;
//            File file = new File(UriToFile(currentUri));
//            testImg.setImageURI(currentUri);
            avatarImg.setVisibility(View.GONE);
            testImg.setVisibility(View.VISIBLE);
            Glide.with(UserInfoActivity.this).load(currentUri).circleCrop().into(testImg);
        }

    }

    private void initView() {
        idUserName = findViewById(R.id.idUserName);
        idPhone = findViewById(R.id.idPhone);
        idUpdateUserInfoBtn = findViewById(R.id.idUpdateUserInfoBtn);
        idGender = findViewById(R.id.idGender);

        idOldPassword = findViewById(R.id.idOldPassword);
        idPassword = findViewById(R.id.idPassword);
        idPasswordConfirm = findViewById(R.id.idPasswordConfirm);
        idModifyPwdBtn = findViewById(R.id.idModifyPwdBtn);

        changeImgBtn = findViewById(R.id.changeImgBtn);
        submitAvatarBtn = findViewById(R.id.submitAvatarBtn);
        avatarImg = findViewById(R.id.avatarImg);
        testImg = findViewById(R.id.testImg);
        testImg.setVisibility(View.GONE);
    }
}
