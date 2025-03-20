package com.wei.diploma_project.fragment.tabBar;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.wei.diploma_project.R;
import com.wei.diploma_project.adapter.FragmentCollectionAdapter;
import com.wei.diploma_project.adapter.ClassMenuListAdapter;
import com.wei.diploma_project.api.GoodApi;
import com.wei.diploma_project.bean.GoodCategoryBeanExtend;
import com.wei.diploma_project.fragment.ClassGoodListFragment;
import com.wei.diploma_project.util.RequestUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ClassFragment extends Fragment {
    /* 左右侧组件对象 */
    private RecyclerView classView;
    private ViewPager2 goodListView;

    /* 右侧商品列表Fragment */
    private ClassGoodListFragment goodListFragment;
    /* 左侧 适配器 */
    private ClassMenuListAdapter classAdapter;

    /* 本地数据 */
    private Integer currentPosition;
    // 包括大类小类商品列表
    private List<GoodCategoryBeanExtend> goodCategoryList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_class, container, false);
        /* 请求Api拿数据信息 */
        getGoodCategory();

        /* 左侧菜单栏与适配器 */
        classView = view.findViewById(R.id.mRecyclerView);
        classAdapter = new ClassMenuListAdapter(this);
        classView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        classView.setAdapter(classAdapter);

        /* 右侧商品列表Fragment */
        goodListView = view.findViewById(R.id.mViewPager2);
        ArrayList<androidx.fragment.app.Fragment> fragments = new ArrayList<>();
        goodListFragment = new ClassGoodListFragment();
        fragments.add(goodListFragment);
        FragmentCollectionAdapter adapter = new FragmentCollectionAdapter((FragmentActivity) getActivity(), fragments);
        goodListView.setAdapter(adapter);

        return view;
    }
    /* 请求Api拿数据信息 */
    private void getGoodCategory() {
        GoodApi goodApi = RequestUtil.getRetrofit().create(GoodApi.class);
        goodApi.getGoodCategory().enqueue(new Callback<List<GoodCategoryBeanExtend>>() {
            @Override
            public void onResponse(Call<List<GoodCategoryBeanExtend>> call, Response<List<GoodCategoryBeanExtend>> response) {
                goodCategoryList = response.body();
                /* 更新左侧商品大类 */
                classAdapter.setLocalDataSet(goodCategoryList);
                classAdapter.notifyDataSetChanged();
                /* 更新右侧具体商品 默认更新第一栏 position:0 */
                setPositionToUpdateView(0);
            }

            @Override
            public void onFailure(Call<List<GoodCategoryBeanExtend>> call, Throwable t) {
                // 此处得显示 ERROR 重新加载
                t.printStackTrace();
            }
        });
    }
    /* 更新右侧具体商品 */
    public void setPositionToUpdateView(int position) {
        // 类型更具LIST来的 不会越界
        currentPosition = position;
        /* 更新商品大类对应的右侧商品  get(0) 是因为有多个商品小类，只显示一种 切换功能未写！！！  */
        if (goodCategoryList.get(position).getGoodTypeList().size() > 0)
            goodListFragment.updateViewData(goodCategoryList.get(position).getGoodTypeList().get(0).getGoodList(), goodCategoryList.get(position).getGoodTypeList());

    }

}
