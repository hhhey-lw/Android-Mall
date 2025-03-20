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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.wei.diploma_project.R;
import com.wei.diploma_project.activity.OrderDetailActivity;
import com.wei.diploma_project.adapter.CartListAdapter;
import com.wei.diploma_project.api.CartApi;
import com.wei.diploma_project.bean.CartBean;
import com.wei.diploma_project.bean.UserBean;
import com.wei.diploma_project.util.RequestUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CartFragment extends Fragment {
    // 适配器数据与这个一样
    private List<CartBean> cartData;

    private Button idPayment;
    private TextView idCartTotal;

    /* 视图与适配器 */
    private RecyclerView cartViewList;
    private CartListAdapter cartListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_cart, container, false);

        initView(view);
        initEvent();

        return view;
    }

    private void initEvent() {
        cartListAdapter = new CartListAdapter();
        cartListAdapter.setParent(this);
        cartViewList.setAdapter(cartListAdapter);

        idPayment.setOnClickListener(v -> {
            doPayment();
        });
    }
    /* 提交订单 */
    private void doPayment() {
        SharedPreferences loginInfo = getActivity().getSharedPreferences("loginInfo", MODE_PRIVATE);
        /* 有token信息，则判断为登录 */
        if (!loginInfo.getString("token", "").equals("")) {
            /* 拿去选中的 item */
            Set<CartBean> selectedCart = new HashSet<>();
            if (cartData != null && cartListAdapter.getSelectedCart() != null) {
                for (CartBean cartBean : cartData) {
                    for (Integer cid : cartListAdapter.getSelectedCart()) {
                        if (cartBean.getCid() == cid)
                            selectedCart.add(cartBean);
                    }
                }
            }
            /* 格式化时间 */
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            String json = gson.toJson(selectedCart);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),json);
            RequestUtil.getRetrofit().create(CartApi.class).submitCartToOrder(body).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String json = new String(response.body().bytes());
                        JSONObject jsonObject = new JSONObject(json);

                        if (!jsonObject.getString("code").equals("200")) {
                            Toast.makeText(getContext(), "生成订单失败！", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Intent intent = new Intent(getContext(), OrderDetailActivity.class);
                        intent.putExtra("oid", jsonObject.getString("data"));
                        startActivity(intent);

                        cartListAdapter.clearSelectedCart();
                    }catch (JSONException|IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    private void initView(View view) {
        cartViewList = view.findViewById(R.id.cartItemView);
        cartViewList.setLayoutManager(new LinearLayoutManager(view.getContext()));

        idPayment = view.findViewById(R.id.idPayment);
        idCartTotal = view.findViewById(R.id.idCartTotal);
    }

    /* GET 购物车信息 */
    public void getCartInfo() {
        UserBean user = RequestUtil.getLoginUser(this.getContext());
        if (user == null)
            return;
        RequestUtil.getRetrofit().create(CartApi.class).getCartList(user.getUid()).enqueue(new Callback<ResponseBody>() {
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

                    List<CartBean> data = new ArrayList<>();

                    /* 可解析 以下格式的 Date */
                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                    for (JsonElement e : array) {
                        data.add(gson.fromJson(e, CartBean.class));
                    }

                    cartData = data;
                    cartListAdapter.setLocalDataSet(cartData);
                    cartListAdapter.notifyDataSetChanged();

                    /* 更新金额 */
                    updateCartTotal();
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

    @Override
    public void onStart() {
        System.out.println(" Cart Fragment start ！！！");
        super.onStart();
        // 获取网络数据
        cartListAdapter.clearSelectedCart();
        getCartInfo();
    }

    // 更新 显示的总价
    public void updateCartTotal() {
        double total = 0;
//        System.err.println("pre : " + total);
        if (cartData != null && cartListAdapter.getSelectedCart() != null) {
            for (CartBean cartBean : cartData) {
                for (Integer cid : cartListAdapter.getSelectedCart()) {
                    if (cartBean.getCid() == cid)
                        if (cartBean.getGood().getGsaleStatus() == 1) {
                            total += cartBean.getGpurchaseNumber() * cartBean.getGood().getGdiscountPrice();
                        }else {
                            total += cartBean.getGpurchaseNumber() * cartBean.getGood().getGprimalPrice();
                        }
                }
            }
        }
//        System.err.println(String.format("post %.2f", total));
        idCartTotal.setText(String.format("%.2f", total));
    }
}
