package com.wei.diploma_project.mapper;


import com.wei.diploma_project.bean.ShoppingcartBean;

import java.util.List;

/**
* @author 韦龙
* @description 针对表【shoppingcart】的数据库操作Mapper
* @createDate 2023-03-19 19:29:38
* @Entity com.wei.diploma_project.bean.Shoppingcart
*/
public interface ShoppingcartMapper {

    boolean addShoppingCart(ShoppingcartBean shoppingcartBean);

    List<ShoppingcartBean> getShoppingCartListByUID(int uid);

    ShoppingcartBean searchIsExist(int gid);

    boolean updateShoppingCart(ShoppingcartBean shoppingcartBean);

    int deleteCartByCid(int cid);

    int updateCartGoodNumber(int cid, int purchaseNumber);

}
