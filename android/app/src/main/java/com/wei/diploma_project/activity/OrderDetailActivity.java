package com.wei.diploma_project.activity;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.wei.diploma_project.R;
import com.wei.diploma_project.adapter.OrderDetailListAdapter;
import com.wei.diploma_project.adapter.OrderListAdapter;
import com.wei.diploma_project.api.AddressApi;
import com.wei.diploma_project.api.OrderApi;
import com.wei.diploma_project.bean.AddressBean;
import com.wei.diploma_project.bean.CartBean;
import com.wei.diploma_project.bean.GoodBean;
import com.wei.diploma_project.bean.OrderBean;
import com.wei.diploma_project.bean.OrderBrief;
import com.wei.diploma_project.bean.OrderItem;
import com.wei.diploma_project.util.OrderInfoUtil2_0;
import com.wei.diploma_project.util.PayResult;
import com.wei.diploma_project.util.RequestUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailActivity extends AppCompatActivity {

    /**
     * 用于支付宝支付业务的入参 app_id。
     */
    public static final String APPID = "2021000122676302";

    /**
     * 用于支付宝账户登录授权业务的入参 pid。
     */
    public static final String PID = "2088721011279365";

    /**
     * 用于支付宝账户登录授权业务的入参 target_id。
     */
    public static final String TARGET_ID = "";

    /**
     *  pkcs8 格式的商户私钥。
     *
     * 	如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个，如果两个都设置了，本 Demo 将优先
     * 	使用 RSA2_PRIVATE。RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议商户使用
     * 	RSA2_PRIVATE。
     *
     * 	建议使用支付宝提供的公私钥生成工具生成和获取 RSA2_PRIVATE。
     * 	工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public static final String RSA2_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCzeC3ujgofu+XdiiugNnVX6cfpzi2ky5kJ2RAWUPl8dX6HMTpp91msRZ96b495v4igE0h7AhqMVhWLR0sZE2a20/fpIRzqdsLxOrxo4ZXkiojYVG400L2cf2yFeWyE7NhXUiD8bn1n6MpMy+4dwIdAzW5JZWG+na0i/kSEY2pP38ufYw4CDFeUDWOVV0Fom18JL19DnBtcWkn8eVI4o8wYSlih3okAs/4ouG4sFCCNGp0X2QNxNF1WjOlp+NHOvEIfMYGZTzXUdUTs59lhzFoL4adiS0KbbK+u0Y8QOgOnz1V+SYpfn3AXxBPtuGEn480tr8MSK84SfidNAMSpppPzAgMBAAECggEAO7FPZbcsut1eWU9pr0ItyW3ipmuDVvq5DkN6TFHmiXTy/rhq+2/gDaKiprCQgigoo0WGqBNNDTxcshSmb9K6KTlbjdkLqxiRQncMz72og3lohrueZEbBqlnfmI2IgLXeRzzz58JAR91v3BdAPfkZg35QnmzwE9ySB7+WvonqhGIUzh0waHm1JhewZQlQFZBPq8NGPtyuhNIqhtzneXL7y8RCwX85fZAt0ZR/n+CT0nUkZh1HlulwxrOLCLPVYnRIk3Tbf2XZXtasAn//RrpszwaW6aoADeHEx6EcGk8j8WHr62m7KfSadRY8nNIZo6B7hnpN63jFaGIhq6Al8a1NIQKBgQD97wv0qISUkCErltbxGxRBKSJpaCHK5Xb/yNShZEYJyxR6I887oXkz6Yd6lIi7AwE7FoMlQZfCTYbK3GOz2wscp5iGBmpxD9V/FCK+atJY33lBDgltyt11yaQ5XKlVR4MCMwpGdk1t6E4Ul4znWSJ8XIye0jn4iSdwWf5Xw35JCwKBgQC07gVUbU5iUOJlFVXbZAxsHToO2O4ejrJxEBywYQ5sj2Zp2hZoWdTJhYMBfiCxQP3wNnJK106IqmiuBGxvH9qjZDnv/bP5ZcEFvrKfYkBnnOCXLpgzkXjDc0h9kWwDR9MoLVO3hmPU1Tvd/oC1tMyNl/fxGD29AEQQcWEQr4hBuQKBgQCPCH5QgjejwwecGOvbhWhER9pFS011jwkXjNUQT34P+B8BqXgKW6r8rbcNOetGg6vGz6hxbOABp0+cpsVWQrSgoFqOa8C7DLMyUHI9lcgEHWScWz6+ZD2YIktpsCMFkTFfJMbN58c3eFm6yZ6plZCSQkNHFp1mvELFyzszzj8s/wKBgHyVVRgweAPc3DdsfIUoHCGp8ltc5oImEhsmVArjmfIIwCVtJXbPPGVTSMUTW2GSdgkdBJAiN5KFfLUumJfgIEXX8skdMfsuJL5W9FopZl2yOTgAvo7rwXMcA7NrGiwcHfsRUw8RO9bGyYVwBKKaDywEk5bw8ToeX7/Owl6h77oJAoGBAMJ4L17aeaSJCmL6QFNjr45rBz1w622vpwNJAGSUJjmoH0TCu9hSXQUVlBM/aLKbTFJl6y6GbZ64qmC0ACP4cQxalW5rSIpbwX/qjYsfCmxBPKDt1g37LRKKaLpLhxF3krWu5k0KhS04OWqEekAj9+JHQRB9F6CevXiQ1eD1d422";
    public static final String RSA_PRIVATE = "";

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    /* 支付宝 处理支付结果 */
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        showAlert(OrderDetailActivity.this, "成功啦！ ： " + getString(R.string.pay_success) + payResult);
                        RequestUtil.getRetrofit().create(OrderApi.class).queryOrderByTradeNo(orderBean.getOrderIndex()).enqueue(new retrofit2.Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                showAlert(OrderDetailActivity.this, "付款成功！");
                                setInfo(orderBean.getOid());
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showAlert(OrderDetailActivity.this, getString(R.string.pay_failed) + payResult);
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    /* 组件 */
    private TextView idOrderIndex;
    private TextView idOrderCreateTime;
    private TextView idOrderSendTime;
    private TextView idOrderPayTime;
    private TextView idGoodTotal;
    private TextView idFreightExpense;
    private TextView idOrderTotal;
    private RecyclerView idOrderDetailItemView;
    private Button idOrderStatus;
    // 地址信息
    private TextView idModifyAddr;
    private TextView idUserName;
    private TextView idPhone;
    private TextView idAddress;

    OrderBean orderBean;
    int oid;
    /* 数据 */
    List<OrderItem> itemList;
    List<GoodBean> goodList;

    ActivityResultLauncher<Intent> resultLauncher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX); // 沙箱环境
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail);

        initView();
        initEvent();

        Intent intent = getIntent();
        String oid = intent.getStringExtra("oid");

        if (oid == null || oid.equals(""))
            return;
        System.err.println("订单详情 ：oid " + oid);
        this.oid = Integer.parseInt(oid);

        // 拿回传的数据 使用的地址ID
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            try {
                Log.e("choose addr_id ", "" + result.getData().getIntExtra("addr_id", -1));
                setOrderAddrID(result.getData().getIntExtra("addr_id", -1));
            }catch (Exception e) {
                e.printStackTrace();
            }
        });

        setInfo(this.oid);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        setInfo(this.oid);
        if (orderBean!= null && orderBean.getOstatus() == 4) // 待评价时，刷刷
            setInfo(this.oid);
    }

    /* 显示页面信息 */
    public void setInfo(Integer oid) {
        RequestUtil.getRetrofit().create(OrderApi.class).getOrderInfo(oid).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String json = new String(response.body().bytes());
                    JSONObject jsonObject = new JSONObject(json);
                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

                    JSONObject data = jsonObject.getJSONObject("data");
                    /* 订单对象 */
                    orderBean = gson.fromJson(data.getString("order"), OrderBean.class);

                    JsonParser parser = new JsonParser();
                    JsonArray item = parser.parse(data.getString("item")).getAsJsonArray();
                    JsonArray good = parser.parse(data.getString("good")).getAsJsonArray();
                    /* 列表对象 */
                    itemList = new ArrayList<>();
                    goodList = new ArrayList<>();

                    /* 可解析 以下格式的 Date */
                    for (JsonElement e : item) {
                        itemList.add(gson.fromJson(e, OrderItem.class));
                    }
                    for (JsonElement e : good) {
                        goodList.add(gson.fromJson(e, GoodBean.class));
                    }

                    /* 格式化时间 */
                    String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(orderBean.getCreateTime());
//                    String payTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(orderBean.getPaymentTime());
                    idOrderIndex.setText("订单编号：" + orderBean.getOrderIndex());
                    idOrderCreateTime.setText("下单时间：" + createTime);
                    if (orderBean.getPaymentTime() != null) {
                        String payTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(orderBean.getPaymentTime());
                        idOrderPayTime.setText("支付时间：" + payTime);
                    }
                    if (orderBean.getDeliveryTime() != null) {
                        String getTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(orderBean.getDeliveryTime());
                        idOrderSendTime.setText("发货时间：" + getTime);
                    }
//                    idOrderPayTime.setText("付款时间：" + payTime);
                    idGoodTotal.setText("￥" + orderBean.getPriceTotal());
                    idFreightExpense.setText("￥" + orderBean.getFreightExpense());
                    idOrderTotal.setText(String.format("%.2f", orderBean.getPriceTotal() + orderBean.getFreightExpense()));

                    OrderDetailListAdapter adapter = new OrderDetailListAdapter();
                    adapter.setGoodList(goodList);
                    adapter.setItemList(itemList);
                    idOrderDetailItemView.setAdapter(adapter);
                    idOrderDetailItemView.setLayoutManager(new LinearLayoutManager(idOrderDetailItemView.getContext()));

                    initAddrData(orderBean.getAddrId());
                    idModifyAddr.setVisibility(View.GONE);

                    /* 订单状态显示 */
                    switch (orderBean.getOstatus()) {
                        case 1: {
                            idOrderStatus.setText("待付款");
                            idModifyAddr.setVisibility(View.VISIBLE);
                            idOrderStatus.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(OrderDetailActivity.this, "支付", Toast.LENGTH_SHORT).show();
                                    payV2(v);
                                }
                            });
                            idModifyAddr.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(OrderDetailActivity.this, "修改地址", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(OrderDetailActivity.this, UserAddressActivity.class);
                                    intent.putExtra("action", "choose");
                                    resultLauncher.launch(intent);
                                }
                            });
                            break;
                        }
                        case 2: {
                            idOrderStatus.setText("等待发货");
                            Toast.makeText(OrderDetailActivity.this, "请等待商品发货~", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case 3: {
                            idOrderStatus.setText("确认收货");
                            idOrderStatus.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new AlertDialog.Builder(v.getContext())
                                            .setTitle("是否确认收货？")
                                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    System.err.println("确认收货");
                                                    // 更改Order 为 收货状态
                                                    RequestUtil.getRetrofit().create(OrderApi.class).receivedGood(orderBean.getOrderIndex()).enqueue(new Callback<ResponseBody>() {
                                                        @Override
                                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                            try {
                                                                /* 转json */
                                                                String json = new String(response.body().bytes());
                                                                JSONObject jsonObject = new JSONObject(json);

                                                                if (jsonObject.getString("code").equals("200")) {
                                                                    Toast.makeText(OrderDetailActivity.this, "确认收货成功！", Toast.LENGTH_SHORT).show();
                                                                    setInfo(orderBean.getOid());
                                                                }
                                                            } catch (JSONException | IOException e) {
                                                                e.printStackTrace();
                                                                Toast.makeText(OrderDetailActivity.this, "确认收货失败！", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                            t.printStackTrace();
                                                            Toast.makeText(OrderDetailActivity.this, "确认收货失败！", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                }
                                            })
                                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    System.err.println("取消");
                                                }
                                            })
                                            .show();
                                }
                            });
                            break;
                        }
                        case 4: {
                            idOrderStatus.setText("点击评价");
                            idOrderStatus.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(OrderDetailActivity.this, GoodRatingActivity.class);
                                    intent.putExtra("oid", oid);
                                    startActivity(intent);
                                }
                            });
                            break;
                        }
                        default: {
                            // ...
                            idOrderStatus.setText("交易完成");
                        }
                    }
                }catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void initAddrData(int addr_id) {
        if (addr_id == -1)
            return;
        RequestUtil.getRetrofit().create(AddressApi.class).getSingleAddress(addr_id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    /* 转json */
                    String json = new String(response.body().bytes());
                    JSONObject jsonObject = new JSONObject(json);

                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                    AddressBean addr = gson.fromJson(jsonObject.getString("data"), AddressBean.class);

                    System.err.println("使用的地址ID ***" + addr.getAddrId());

                    idUserName.setText(addr.getUsername());
                    idPhone.setText(addr.getPhone());
                    idAddress.setText(String.format("%s%s%s%s",
                            addr.getAddrProvince(),
                            addr.getAddrCity(),
                            addr.getAddrDistrict(),
                            addr.getAddrDetail()));

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

    // 差 更改预付款订单地址信息，and订单中的地址信息
    public void setOrderAddrID(int addr_id) {
        if (addr_id == -1)
            return;
        System.err.println("传来的 地址ID *** " + addr_id);
        RequestUtil.getRetrofit().create(OrderApi.class)
                        .updateAddr(oid, addr_id).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        // ...
                        System.err.println("使用的地址ID ***" + orderBean.getAddrId());
                        initAddrData(addr_id);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    private void initEvent() {
    }

    private void initView() {
        idOrderIndex = findViewById(R.id.idOrderIndex);
        idOrderCreateTime = findViewById(R.id.idOrderCreateTime);
        idOrderPayTime = findViewById(R.id.idOrderPayTime);
        idOrderSendTime = findViewById(R.id.idOrderSendTime);
        idGoodTotal = findViewById(R.id.idGoodTotal);
        idFreightExpense = findViewById(R.id.idFreightExpense);
        idOrderTotal = findViewById(R.id.idOrderTotal);
        idOrderDetailItemView = findViewById(R.id.idOrderDetailItemView);
        idOrderStatus = findViewById(R.id.idOrderStatus);

        idModifyAddr = findViewById(R.id.idModifyAddr);
        idUserName = findViewById(R.id.idUserName);
        idPhone = findViewById(R.id.idPhone);
        idAddress = findViewById(R.id.idAddress);
    }

    // 以下为 支付宝 ***************

    /**
     * 支付宝支付业务示例
     */
    public void payV2(View v) {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            showAlert(this, getString(R.string.error_missing_appid_rsa_private));
            return;
        }

        /*
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo 的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0
                .buildOrderParamMap(APPID, rsa2, orderBean.getPriceTotal(), "热销商品", orderBean.getOrderIndex()); // 订单参数列表
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params); // 订单参数信息

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2); // 签名
        final String orderInfo = orderParam + "&" + sign;

        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(OrderDetailActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private static void showAlert(Context ctx, String info) {
        showAlert(ctx, info, null);
    }

    private static void showAlert(Context ctx, String info, DialogInterface.OnDismissListener onDismiss) {
        new AlertDialog.Builder(ctx)
                .setMessage(info)
                .setPositiveButton(R.string.confirm, null)
                .setOnDismissListener(onDismiss)
                .show();
    }
}
