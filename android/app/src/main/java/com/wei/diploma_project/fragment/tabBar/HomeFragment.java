package com.wei.diploma_project.fragment.tabBar;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.wei.diploma_project.R;
import com.wei.diploma_project.activity.GoodListShowActivity;
import com.wei.diploma_project.activity.GoodSingleShowActivity;
import com.wei.diploma_project.activity.GoodSearchActivity;
import com.wei.diploma_project.adapter.BannerImageAdapter;
import com.wei.diploma_project.adapter.HomeGoodListAdapter;
import com.wei.diploma_project.api.BannerApi;
import com.wei.diploma_project.api.GoodApi;
import com.wei.diploma_project.bean.BannerBean;
import com.wei.diploma_project.bean.GoodBean;
import com.wei.diploma_project.bean.UserBean;
import com.wei.diploma_project.util.RequestUtil;
import com.wei.diploma_project.view.MyGridView;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    // 功能 imgBtn
    private CircleImageView imgBtn1;
    private CircleImageView imgBtn2;
    private CircleImageView imgBtn3;

    private EditText toSearchView;

    // Banner
    private List<BannerBean> bannerBeanList;
    private BannerImageAdapter bannerAdapter;
    // Hot Good
    private MyGridView gridView;
    private HomeGoodListAdapter homeGoodListAdapter;
    private ArrayList<GoodBean> hotGoodList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_home, container, false);

        initBanner(view);

        initHotGood(view);

        initImgBtn(view);

        initView(view);

        return view;
    }

    // 搜索栏
    private void initView(View view) {
        toSearchView = view.findViewById(R.id.toSearchView);
        toSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), GoodSearchActivity.class));
            }
        });
    }

    /* 功能栏未完成 应传商品大类过去 用于列表展示 */
    private void initImgBtn(View view) {
        imgBtn1 = view.findViewById(R.id.idImgBtn1);
        imgBtn1.setOnClickListener(toGoodListView(3));
        imgBtn2 = view.findViewById(R.id.idImgBtn2);
        imgBtn2.setOnClickListener(toGoodListView(1));
        imgBtn3 = view.findViewById(R.id.idImgBtn3);
        imgBtn3.setOnClickListener(toGoodListView(2));
    }
//    功能跳转
    private View.OnClickListener toGoodListView(int gid) {
        return v -> {
            Intent intent = new Intent(getContext(), GoodListShowActivity.class);
            intent.putExtra("gid", gid);
            startActivity(intent);
        };
    }

    /* 热销商品 */
    private void initHotGood(View view) {
        gridView = view.findViewById(R.id.idGridGood);
        hotGoodList = new ArrayList<>();
        UserBean user = RequestUtil.getLoginUser(view.getContext());
        int uid = -1;
        if (user != null)
            uid = user.getUid();
        RequestUtil.getRetrofit().create(GoodApi.class).getRecommendationList(uid).enqueue(new Callback<ResponseBody>() {
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

                    /* 可解析 以下格式的 Date */
                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                    for (JsonElement e : array) {
//                        System.err.println(e);
                        hotGoodList.add(gson.fromJson(e, GoodBean.class));
                    }

                    homeGoodListAdapter = new HomeGoodListAdapter();
                    homeGoodListAdapter.setDataSet(hotGoodList);
                    homeGoodListAdapter.setContext(getContext());
                    gridView.setAdapter(homeGoodListAdapter);

                    // item点击 应传商品id
                    gridView.setOnItemClickListener((parent, view1, position, id) -> {
                        Intent intent = new Intent(getContext(), GoodSingleShowActivity.class);
                        intent.putExtra("goodId", hotGoodList.get(position).getGid()+"");
                        startActivity(intent);
                    });
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
    // 初始化Banner
    private void initBanner(View view) {
        //自定义的图片适配器，也可以使用默认的BannerImageAdapter
        bannerAdapter = new BannerImageAdapter();
        Banner banner = view.findViewById(R.id.banner);
        banner.setAdapter(bannerAdapter)
                .setIndicator(new CircleIndicator(view.getContext()));//设置指示器
        getBannerRes();
    }
    /* 获取Banner资源 并绑定 */
    private void getBannerRes() {
        RequestUtil.getRetrofit().create(BannerApi.class).getBannerRes().enqueue(new Callback<List<BannerBean>>() {
            @Override
            public void onResponse(Call<List<BannerBean>> call, Response<List<BannerBean>> response) {
                bannerBeanList = response.body();
                bannerAdapter.updateData(bannerBeanList);
            }

            @Override
            public void onFailure(Call<List<BannerBean>> call, Throwable t) {
                // ...
                t.printStackTrace();
            }
        });
    }
}
