package com.wei.diploma_project.util;

import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.wei.diploma_project.R;

/* 透明状态栏 工具 */
public class TransparentBar {
    public static int statusBarHeight;
    /* 透明化状态栏 字体变黑 */
    public static void transparent(AppCompatActivity app) {
        app.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = app.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            /* SYSTEM_UI_FLAG_LIGHT_STATUS_BAR 状态栏字体变黑色 LIGHT 浅状态 深字体 */
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            /* ↓ 设置顶部状态栏 底部导航栏 背景透明 */
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        statusBarHeight = app.getResources().getDimensionPixelSize(app.getResources().getIdentifier(
                "status_bar_height", "dimen", "android"
        ));
    }
    /* 填充状态栏 */
    public static void fillStatusBar(AppCompatActivity app) {
        // 填充一个状态栏高度的透明墙
        View space = app.findViewById(R.id.space);
        ViewGroup.LayoutParams params = space.getLayoutParams();
        params.height = statusBarHeight;
        space.setLayoutParams(params);
    }
}