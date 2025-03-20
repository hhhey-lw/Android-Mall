package com.wei.diploma_project.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wei.diploma_project.bean.*;
import com.wei.diploma_project.mapper.OrderMapper;
import com.wei.diploma_project.service.GoodService;
import com.wei.diploma_project.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
* @author 韦龙
* @description 针对表【order】的数据库操作Service实现
* @createDate 2023-03-20 20:41:15
*/
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private GoodService goodService;

    @Override
    public int addOrder(OrderBean order) {
        return orderMapper.addOrder(order);
    }

    @Override
    public int getNewestOidByUid(int uid) {
        return orderMapper.getNewestOidByUid(uid);
    }

    @Override
    public int addOrderItem(OrderItem o) {
        return orderMapper.addOrderItem(o);
    }

    @Override
    public OrderBean getOrderByOid(int oid) {
        return orderMapper.getOrderByOid(oid);
    }

    @Override
    public List<OrderItem> getOrderItem(int oid) {
        return orderMapper.getOrderItem(oid);
    }

    @Override
    public List<Integer> getAllOidByUid(int uid) {
        return orderMapper.getAllOidByUid(uid);
    }

    @Override
    public boolean updateOrderStatus(int ostatus, String orderIndex) {
        return orderMapper.updateOrderStatus(ostatus, orderIndex);
    }

    @Override
    public boolean updateOrderStatus(int ostatus, int oid) {
        return orderMapper.updateOrderStatusByOid(ostatus, oid);
    }

    @Override
    public boolean updateOrderItemStatus(int oi_status, int oitem_id) {
        return orderMapper.updateOrderItemStatus(oi_status, oitem_id);
    }

    @Override
    public boolean updateOrderAddress(int oid, int addr_id) {
        return orderMapper.updateOrderAddress(oid, addr_id);
    }

    @Override
    public PageInfo<OrderBean> getWaitSendOrderInfo(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<OrderBean> waitSendOrderList = orderMapper.getWaitSendOrderList();
        return new PageInfo<>(waitSendOrderList);
    }

    @Override
    public boolean finishedSendGood(int oid, String freightIndex, double freightExpense) {
        return orderMapper.finishedSendGood(oid, freightIndex, freightExpense);
    }

    @Override
    public PageInfo<OrderBean> getAllOrderInfo(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<OrderBean> OrderList = orderMapper.getAllOrder();
        return new PageInfo<>(OrderList);
    }

    @Override
    public boolean updateOrderPayTime(String out_trade_no, Date date) {
        return orderMapper.updateOrderPayTime(out_trade_no, date);
    }

    @Override
    public boolean updateOrderSendTime(int oid, Date date) {
        return orderMapper.updateOrderSendTime(oid, date);
    }

    @Override
    public List<OrderBean> calPriceWithWeek() {
        return orderMapper.calPriceWithWeek();
    }
}
