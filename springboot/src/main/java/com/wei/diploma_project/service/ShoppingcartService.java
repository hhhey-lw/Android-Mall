package com.wei.diploma_project.service;


import com.wei.diploma_project.bean.ShoppingCartBeanExtend;
import com.wei.diploma_project.bean.ShoppingcartBean;

import java.util.List;

/**
* @author 韦龙
* @description 针对表【shoppingcart】的数据库操作Service
* @createDate 2023-03-19 19:29:38
*/
public interface ShoppingcartService {
    boolean addShoppingCart(ShoppingcartBean shoppingcartBean);

    List<ShoppingCartBeanExtend> getShoppingCartListByUID(int uid);

    int deleteCartByCid(int cid);

    int updateCartGoodNumber(int cid, int purchaseNumber);
}
