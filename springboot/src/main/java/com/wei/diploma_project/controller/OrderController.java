package com.wei.diploma_project.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.wei.diploma_project.bean.GoodBean;
import com.wei.diploma_project.bean.OrderBean;
import com.wei.diploma_project.bean.OrderBrief;
import com.wei.diploma_project.bean.OrderItem;
import com.wei.diploma_project.service.GoodService;
import com.wei.diploma_project.service.OrderService;
import com.wei.diploma_project.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * User: 韦龙
 * Date: 2023/3/25
 * description:
 */
@RestController
@RequestMapping("/order")
public class OrderController {

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

    private static final String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAj7/uwugtxOBgbCiOOFv8a6mI3BqphyCvLuUykO/oImEtSI5K3MhENqumKNMICEzTFrT9xcz88MBSOo4+hw8R8fMSLh4vgYZObJTeQIvoyTvtEjU7itxYMQEy11va7D21FUAyI2r5zrqsUJddYTI/VS3+O+2fLPOkF5BqNxheCsFNAfciVSjqsABVvDA0aHuJaqn9nOJA5m2N0ARKMUNcM9Dr/5e/mEMPmdzrzOms4myPu/p0qk76EMQfzz+qQZG/zMVuxotr8pjTijt7b+7cs2HBpJWDDXR4ncxbYR5Z9yQ4EjEMosuy4jvjxKrOOrV86vYiv9CEh/1FLF6oiGFDwwIDAQAB";

    @Autowired
    private OrderService orderService;
    @Autowired
    private GoodService goodService;

    @PostMapping("/info")
    public Result getOrderByOid(int oid) {
        System.err.println("info : " + oid);
        OrderBean order = orderService.getOrderByOid(oid);
        HashMap<String, Object> map = new HashMap<>();
        map.put("order", order);

        List<OrderItem> orderItem = orderService.getOrderItem(oid);
        ArrayList<GoodBean> goodList = new ArrayList<>();

        for (OrderItem item : orderItem) {
            goodList.add(goodService.getGoodById(item.getGid()));
        }

        map.put("item", orderItem);
        map.put("good", goodList);

        return Result.ok(map);
    }

    @PostMapping("/all")
    public Result getAllOrderByUid(int uid) {
        ArrayList<OrderBrief> data = new ArrayList<>();
        List<Integer> oids = orderService.getAllOidByUid(uid);
        // 遍历所有订单
        for (Integer oid : oids) {
            // 拿对应的订单和子项
            OrderBean order = orderService.getOrderByOid(oid);
            List<OrderItem> item = orderService.getOrderItem(oid);
            // 取子项对应的商品信息
            ArrayList<GoodBean> goodList = new ArrayList<>();
            for (OrderItem i : item) {
                goodList.add(goodService.getGoodById(i.getGid()));
            }
            // 填充需要的信息
            OrderBrief brief = new OrderBrief();
            brief.setOid(order.getOid());
            brief.setUid(order.getUid());
            brief.setAddr_id(order.getAddrId());
            brief.setOrderIndex(order.getOrderIndex());
            brief.setFreightIndex(order.getFreightIndex());
            brief.setOstatus(order.getOstatus());
            brief.setTotal(order.getPriceTotal()+order.getFreightExpense());
            brief.setCreateTime(order.getCreateTime());
            brief.setGood(goodList);
            brief.setItem(item);

            data.add(brief);
        }
        return Result.ok(data);
    }

    @GetMapping("/waitSend/{pageNum}-{pageSize}")
    public Result getWaitSendOrderInfo(@PathVariable int pageNum, @PathVariable int pageSize) {
        return Result.ok(orderService.getWaitSendOrderInfo(pageNum, pageSize));
    }

    @GetMapping("/all/{pageNum}-{pageSize}")
    public Result getAllOrder(@PathVariable int pageNum, @PathVariable int pageSize) {
        return Result.ok(orderService.getAllOrderInfo(pageNum, pageSize));
    }

    /* 查询订单付款状态 并判断 是否 修改支付状态 待付款 =>(付款) 待发货  */
    @PostMapping("/query/{out_trade_no}")
    public Result queryOrderStatus(@PathVariable("out_trade_no") String out_trade_no) {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do",APPID,RSA2_PRIVATE,"json","UTF-8",alipay_public_key,"RSA2");
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", out_trade_no);
        //bizContent.put("trade_no", "2014112611001004680073956707");
        request.setBizContent(bizContent.toString());
        AlipayTradeQueryResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if(response.isSuccess()){
            System.out.println(orderService.updateOrderStatus(2, out_trade_no)); // 修改为待发货(2)
            orderService.updateOrderPayTime(out_trade_no, new Date(System.currentTimeMillis()));
            System.out.println("调用成功" + response.getBody());
            return Result.ok(response.getBody());
        } else {
            System.out.println("调用失败" + response.getBody());
            return Result.fail(response.getBody());
        }
    }

    // 订单 收货
    @PostMapping("/received/{orderIndex}")
    public Result receivedGood(@PathVariable("orderIndex") String orderIndex) {
        // 点击待收货 => 4为待评价
        if (orderService.updateOrderStatus(4, orderIndex))
            return Result.ok();
        else
            return Result.fail();
    }

    // 订单 发货
    @PostMapping("/sendOut")
    public Result sendOutGood(int oid, String freightIndex, double freightExpense) {
        // 点击待收货 => 4为待评价
        if (orderService.finishedSendGood(oid, freightIndex, freightExpense)) {
            // 转3 待收货
            orderService.updateOrderSendTime(oid, new Date(System.currentTimeMillis()));
            return Result.ok();
        }
        else
            return Result.fail();
    }

    // 订单 更改地址
    @PostMapping("/updateAddr/{oid}-{addr_id}")
    public Result updateAddr(@PathVariable("oid") int oid, @PathVariable("addr_id") int addr_id) {
        System.err.println("修改订单地址");
        System.err.println(oid + " : " + addr_id);
        // 点击待收货 => 4为待评价
        if (orderService.updateOrderAddress(oid, addr_id))  // 转3 待收货
            return Result.ok();
        else
            return Result.fail();
    }

    @PostMapping("/calPriceWithWeek")
    public Result calPriceWithWeek() {
        return Result.ok(orderService.calPriceWithWeek());
    }
}
