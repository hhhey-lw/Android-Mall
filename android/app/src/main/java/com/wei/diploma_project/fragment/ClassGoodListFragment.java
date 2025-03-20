package com.wei.diploma_project.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wei.diploma_project.R;
import com.wei.diploma_project.adapter.ClassGoodListAdapter;
import com.wei.diploma_project.bean.GoodBean;
import com.wei.diploma_project.bean.GoodTypeBeanExtend;

import java.util.List;

public class ClassGoodListFragment extends Fragment {
    private List<GoodTypeBeanExtend> goodTypeList;
    // 组件 与 适配器
    private RecyclerView goodListView;
    private ClassGoodListAdapter goodListAdapter;
    // 适配器数据
    private LinearLayout idTypeContainer;

    // 当前小类
    private Integer currentIndex;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.class_good_view, container, false);

        // 初始化
        currentIndex = 0;

        /* 右侧商品组件和适配器渲染 不填充数据 */
        goodListView = view.findViewById(R.id.goodListView);
        goodListAdapter = new ClassGoodListAdapter();
//        goodListAdapter.setContext(getContext());
        goodListView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        goodListView.setAdapter(goodListAdapter);


        idTypeContainer = view.findViewById(R.id.idTypeContainer);
        return view;
    }

    // 刷新右侧 商品小类名称 商品列表
    @SuppressLint({"NotifyDataSetChanged"})
    public void updateViewData(List<GoodBean> dataSet, List<GoodTypeBeanExtend> goodTypeList) {
        this.goodTypeList = goodTypeList;
        // 商品列表
        goodListAdapter.setLocalDataSet(dataSet);

        currentIndex = 0; // 重置一下 小类名称当前下标
        changeView();

        goodListAdapter.notifyDataSetChanged();
    }

    @SuppressLint({"ResourceType", "UseCompatLoadingForColorStateLists", "NotifyDataSetChanged"})
    private void changeView() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.setMargins(10, 10, 10, 10);
        // 追加商品小类名称到 容器中
        idTypeContainer.removeAllViews();
        for (int i = 0; i < goodTypeList.size(); i++) {
            TextView textView = new TextView(getContext());
            textView.setText(goodTypeList.get(i).getGtype_name());
            textView.setTextSize(16);
            textView.setClickable(true);
            textView.setEnabled(true);
            textView.setTextColor(getContext().getResources().getColorStateList(R.drawable.left_menu_text));
            textView.setLayoutParams(layoutParams);
            if (currentIndex == i)
                textView.setSelected(true);

            List<GoodBean> goodList = goodTypeList.get(i).getGoodList(); // i 传不进去
            int index = i;
            textView.setOnClickListener(v -> {
                goodListAdapter.setLocalDataSet(goodList);
                goodListAdapter.notifyDataSetChanged();

                currentIndex = index;
                changeView();
            });

            idTypeContainer.addView(textView);
        }
    }

}
