package com.wei.diploma_project.mapper;


import com.wei.diploma_project.bean.OrderBean;
import com.wei.diploma_project.bean.OrderBrief;
import com.wei.diploma_project.bean.OrderItem;

import java.util.Date;
import java.util.List;

/**
* @author 韦龙
* @description 针对表【order】的数据库操作Mapper
* @createDate 2023-03-25 10:15:03
* @Entity com.wei.diploma_project.bean.Order
*/
public interface OrderMapper {
    int addOrder(OrderBean order);

    int getNewestOidByUid(int uid);

    int addOrderItem(OrderItem o);

    OrderBean getOrderByOid(int oid);

    List<OrderItem> getOrderItem(int oid);

    List<Integer> getAllOidByUid(int uid);

    boolean updateOrderStatus(int ostatus, String orderIndex);

    boolean updateOrderStatusByOid(int ostatus, int oid);

    boolean updateOrderItemStatus(int oi_status, int oitem_id);

    boolean updateOrderAddress(int oid, int addr_id);

    List<OrderBean> getWaitSendOrderList();

    boolean finishedSendGood(int oid, String freightIndex, double freightExpense);

    List<OrderBean> getAllOrder();

    List<Integer> getItemUserIDListByGID(int gid);

    boolean updateOrderPayTime(String out_trade_no, Date date);

    boolean updateOrderSendTime(int oid, Date date);

    List<OrderBean> calPriceWithWeek();
}
