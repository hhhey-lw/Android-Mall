package com.wei.diploma_project.controller;

import com.wei.diploma_project.bean.*;
import com.wei.diploma_project.service.AddressService;
import com.wei.diploma_project.service.OrderService;
import com.wei.diploma_project.service.ShoppingcartService;
import com.wei.diploma_project.util.Result;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * User: 韦龙
 * Date: 2023/3/19
 * description:
 */
@RestController
@RequestMapping("/cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingcartService cartService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private AddressService addressService;

    @PostMapping("/add")
    public Result addShoppingCart(@RequestBody ShoppingcartBean e) {
        System.out.println(e);
        System.out.println("ADD !!!");
        boolean b = cartService.addShoppingCart(e);
        if (b)
            return Result.ok();
        else return Result.fail();
    }

    @PostMapping("/get")
    public Result getShoppingCart(@Param("uid") int uid) {
        System.out.println(uid);
        return Result.ok(cartService.getShoppingCartListByUID(uid));
    }

    // 购物车 => 订单 (未付款)
    @PostMapping("/submit")
    public Result submitCartToOrder(@RequestBody List<ShoppingCartBeanExtend> cartList) {
        double total = .0;
        for (ShoppingCartBeanExtend c : cartList) {
            total += c.getGpurchaseNumber() * c.getGood().getGprimalPrice();
        }
        OrderBean orderBean = new OrderBean();
        orderBean.setUid(cartList.get(0).getUid());
        AddressBean defaultAddr = addressService.getDefaultAddr(cartList.get(0).getUid());
        if (defaultAddr != null)
            orderBean.setAddrId(defaultAddr.getAddrId());
        else
            orderBean.setAddrId(-1); // 还未有默认地址！
        // orderBean.setAddrId(1); // 地址定死了  得改
        orderBean.setOrderIndex(getOutTradeNo()); // 订单号
        orderBean.setFreightIndex("###No2023"); // 快递号 定死 得改
        orderBean.setOstatus(1);
        orderBean.setCreateTime(new Date());
        orderBean.setPriceTotal(total);
        orderBean.setFreightExpense(.0);
        orderBean.setPaymentMode(1);
        /* 创建订单！ */
        if (orderService.addOrder(orderBean) > 0) {
            /* 拿订单号 */
            int oid = orderService.getNewestOidByUid(orderBean.getUid());
            for (ShoppingCartBeanExtend c : cartList) {
                /* 构建订单子项 */
                OrderItem item = new OrderItem();
                item.setOid(oid);
                item.setGid(c.getGid());
                item.setGpurchasePrice(c.getGood().getGprimalPrice());
                item.setGpurchaseNumber(c.getGpurchaseNumber());
                /* 插入子项 */
                orderService.addOrderItem(item);
                /* 购物车删除子项 */
                cartService.deleteCartByCid(c.getCid());
            }
            return Result.ok(oid);
        }

        return Result.fail();
    }

    @PostMapping("/update")
    public Result updateGoodNumber(@RequestParam("cid") int cid, @RequestParam("purchaseNumber")int purchaseNumber) {
        if (cartService.updateCartGoodNumber(cid, purchaseNumber) > 0)
            return Result.ok();
        else
            return Result.fail();
    }

    @DeleteMapping("/delete/{cid}")
    public Result deleteCartGood(@PathVariable("cid") int cid) {
        if (cartService.deleteCartByCid(cid) > 0)
            return Result.ok();
        return Result.fail();
    }
    /**
     * 要求外部订单号必须唯一。
     * @return
     */
    public static String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 17);
        return key;
    }
}
