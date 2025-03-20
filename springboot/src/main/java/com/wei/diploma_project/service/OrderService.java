package com.wei.diploma_project.service;


import com.github.pagehelper.PageInfo;
import com.wei.diploma_project.bean.OrderBean;
import com.wei.diploma_project.bean.OrderBrief;
import com.wei.diploma_project.bean.OrderItem;

import java.util.Date;
import java.util.List;

/**
* @author 韦龙
* @description 针对表【order】的数据库操作Service
* @createDate 2023-03-25 10:15:03
*/
public interface OrderService {
    int addOrder(OrderBean order);

    int getNewestOidByUid(int uid);

    int addOrderItem(OrderItem o);

    OrderBean getOrderByOid(int oid);

    List<OrderItem> getOrderItem(int oid);

    List<Integer> getAllOidByUid(int uid);

    boolean updateOrderStatus(int ostatus, String orderIndex);

    boolean updateOrderStatus(int ostatus, int oid);

    boolean updateOrderItemStatus(int oi_status, int oitem_id);

    boolean updateOrderAddress(int oid, int addr_id);

    PageInfo<OrderBean> getWaitSendOrderInfo(int pageNum, int pageSize);

    boolean finishedSendGood(int oid, String freightIndex, double freightExpense);

    PageInfo<OrderBean> getAllOrderInfo(int pageNum, int pageSize);

    boolean updateOrderPayTime(String out_trade_no, Date date);

    boolean updateOrderSendTime(int oid, Date date);

    List<OrderBean> calPriceWithWeek();
}
