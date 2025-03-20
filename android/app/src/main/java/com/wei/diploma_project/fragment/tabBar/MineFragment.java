package com.wei.diploma_project.fragment.tabBar;

import static android.content.Context.MODE_PRIVATE;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.wei.diploma_project.R;
import com.wei.diploma_project.activity.UserAddressActivity;
import com.wei.diploma_project.activity.UserInfoActivity;
import com.wei.diploma_project.activity.UserLoginActivity;
import com.wei.diploma_project.activity.OrderActivity;
import com.wei.diploma_project.bean.UserBean;
import com.wei.diploma_project.util.RequestUtil;

import java.util.Date;


public class MineFragment extends Fragment {
    // 标记是否登录过 用于仅刷新一次页面组件数据 刷新后不再重复
    private boolean isLogin = false;

    private Button loginBtn;
    private Button idLogout;
    private TextView idUserName;
    private ImageView profile_image;
    private String avatar = ""; // 判断前后是否一致

    private LinearLayout idUserInfo;
    private LinearLayout idUserAddress;
    private TextView idAllOrder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_mine, container, false);

        initBind(view);
        initEvent();

        return view;
    }
//    每次页面显示时调用!
    @Override
    public void onStart() {
        System.out.println("Mine Fragment start !");
        super.onStart();
        initInfo();
    }

    //    登录后 初始化页面信息
    private void initInfo() {
        SharedPreferences loginInfo = getActivity().getSharedPreferences("loginInfo", MODE_PRIVATE);
        /* 若登录 */
        if (!loginInfo.getString("token", "").equals("")) {
            idLogout.setVisibility(View.VISIBLE);
            UserBean user = RequestUtil.getLoginUser(this.getContext());
            /* 未初始化组件数据 */
            if (!isLogin || !idUserName.getText().toString().equals(user.getUsername()) || !avatar.equals(user.getAvatar())) {
                try {
                    /* 赋值给组件 */
                    loginBtn.setVisibility(View.GONE);
                    Glide.with(profile_image).load(RequestUtil.BaseURL + user.getAvatar()).into(profile_image);
                    avatar = user.getAvatar();
                    idUserName.setText(user.getUsername());
                    idUserName.setVisibility(View.VISIBLE);
                    // 不再刷新
                    isLogin = true;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        else
            idLogout.setVisibility(View.INVISIBLE);
    }

    private void initBind(View view) {
        loginBtn = view.findViewById(R.id.loginBtn);
        idLogout = view.findViewById(R.id.idLogout);
        profile_image = view.findViewById(R.id.profile_image);
        idUserName = view.findViewById(R.id.idUserName);

        idAllOrder = view.findViewById(R.id.idAllOrder);
        idUserInfo = view.findViewById(R.id.idUserInfo);
        idUserAddress = view.findViewById(R.id.idUserAddress);
    }

    private void initEvent() {
        idAllOrder.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), OrderActivity.class);
            startActivity(intent);
        });
//        登录 跳转页面
        loginBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), UserLoginActivity.class);
            startActivity(intent);
        });
//        登出 清除数据
        idLogout.setOnClickListener(v -> {
            idLogout.setVisibility(View.INVISIBLE);
            System.err.println("Log out");
            SharedPreferences loginInfo = getActivity().getSharedPreferences("loginInfo", MODE_PRIVATE);
            // 登录则清除
            if (isLogin) {
                SharedPreferences.Editor edit = loginInfo.edit();
                edit.clear();
                edit.commit();
                isLogin = false;

                /* 赋值给组件 */
                loginBtn.setVisibility(View.VISIBLE);
                Glide.with(profile_image).load(R.drawable.goodshow1).into(profile_image);
                idUserName.setVisibility(View.GONE);
            }
        });

        // 用户信息 个人信息 + 地址 ！
        idUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), UserInfoActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        // 用户信息 个人信息 + 地址 ！
        idUserAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), UserAddressActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

}
