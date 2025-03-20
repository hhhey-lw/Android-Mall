package com.wei.diploma_project.activity;


import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wei.diploma_project.R;
import com.wei.diploma_project.fragment.tabBar.CartFragment;
import com.wei.diploma_project.fragment.tabBar.ClassFragment;
import com.wei.diploma_project.fragment.tabBar.HomeFragment;
import com.wei.diploma_project.fragment.tabBar.MineFragment;
import com.wei.diploma_project.util.RequestUtil;
import com.wei.diploma_project.util.TransparentBar;

public class App extends AppCompatActivity implements View.OnClickListener {

    // TabBar 仨对象
    private TextView txt_home;
    private TextView txt_class;
    private TextView txt_cart;
    private TextView txt_mine;

    // TabBar对应着的Fragment页面
    private HomeFragment fg1;
    private ClassFragment fg2;
    private CartFragment fg3;
    private MineFragment fg4;
    private FragmentManager fManager;  // Fragment 管理器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransparentBar.transparent(this);
        setContentView(R.layout.app);
        TransparentBar.fillStatusBar(this);

        RequestUtil.setContext(this);
        fManager =getFragmentManager();
        bindTxts();
        txt_home.performClick();
    }

    // 重置所有文本的选中状态 这个用来切换TarBar Selected or Not 样式
    private void setSelectedToFalse(){
        txt_home.setSelected(false);
        txt_class.setSelected(false);
        txt_cart.setSelected(false);
        txt_mine.setSelected(false);
    }

    private void bindTxts() {
        // 获取对象
        txt_home = findViewById(R.id.txt_home);
        txt_class = findViewById(R.id.txt_class);
        txt_cart = findViewById(R.id.txt_cart);
        txt_mine = findViewById(R.id.txt_mine);
        // 绑定事件
        txt_home.setOnClickListener(this);
        txt_class.setOnClickListener(this);
        txt_cart.setOnClickListener(this);
        txt_mine.setOnClickListener(this);
    }

    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fg1 != null) fragmentTransaction.hide(fg1);
        if(fg2 != null) fragmentTransaction.hide(fg2);
        if(fg3 != null) fragmentTransaction.hide(fg3);
        if(fg4 != null) fragmentTransaction.hide(fg4);
    }

    @Override
    public void onClick(View v) {
        /* 一次性使用的事务对象 */
        FragmentTransaction fTransaction = fManager.beginTransaction();
        /* 隐藏所有Fragment */
        hideAllFragment(fTransaction);
        switch (v.getId()){
            case R.id.txt_home:
                /* 所有Selected置为False，选中的置为True */
                setSelectedToFalse();
                txt_home.setSelected(true);

                if(fg1 == null){
                    /* 初始化Fragment内容 可以编写多个不同的Fragment扩展类 */
                    fg1 = new HomeFragment();
                    fTransaction.add(R.id.fg_view, fg1);
                }else{
                    fTransaction.show(fg1);
                }
                break;

            case R.id.txt_class:
                setSelectedToFalse();
                txt_class.setSelected(true);

                if(fg2 == null){
                    fg2 = new ClassFragment();
                    fTransaction.add(R.id.fg_view, fg2);
                }else{
                    fTransaction.show(fg2);
                }
                break;

            case R.id.txt_cart:
                setSelectedToFalse();
                txt_cart.setSelected(true);
                if(fg3 == null){
                    fg3 = new CartFragment();
                    fTransaction.add(R.id.fg_view, fg3);
                }else{
                    fTransaction.show(fg3);
                }
                break;

            case R.id.txt_mine:
                setSelectedToFalse();
                txt_mine.setSelected(true);

                if(fg4 == null){
                    fg4 = new MineFragment();
                    fTransaction.add(R.id.fg_view, fg4);
                }else{
                    fTransaction.show(fg4);
                }
                break;
        }
        /* 提交Fragment事务 */
        fTransaction.commit();
    }
}