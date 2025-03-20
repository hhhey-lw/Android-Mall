package com.wei.diploma_project.service.impl;

import com.wei.diploma_project.bean.GoodBean;
import com.wei.diploma_project.bean.ShoppingCartBeanExtend;
import com.wei.diploma_project.bean.ShoppingcartBean;
import com.wei.diploma_project.mapper.GoodMapper;
import com.wei.diploma_project.mapper.ShoppingcartMapper;
import com.wei.diploma_project.service.ShoppingcartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author 韦龙
* @description 针对表【shoppingcart】的数据库操作Service实现
* @createDate 2023-03-19 19:29:38
*/
@Service
public class ShoppingcartServiceImpl implements ShoppingcartService{

    @Autowired
    private ShoppingcartMapper shoppingcartMapper;
    @Autowired
    private GoodMapper goodMapper;

    @Override
    public boolean addShoppingCart(ShoppingcartBean shoppingcartBean) {
        ShoppingcartBean bean = shoppingcartMapper.searchIsExist(shoppingcartBean.getGid());
        /* 无则插入 */
        if (bean == null)
            return shoppingcartMapper.addShoppingCart(shoppingcartBean);
        else {
            /* 有则 更新 */
            bean.setGpurchaseNumber(bean.getGpurchaseNumber() + shoppingcartBean.getGpurchaseNumber());
            return shoppingcartMapper.updateShoppingCart(bean);
        }
    }

    @Override
    public List<ShoppingCartBeanExtend> getShoppingCartListByUID(int uid) {
        ArrayList<ShoppingCartBeanExtend> list = new ArrayList<>();
        for (ShoppingcartBean bean : shoppingcartMapper.getShoppingCartListByUID(uid)) {
            ShoppingCartBeanExtend ex = new ShoppingCartBeanExtend();
            ex.setCid(bean.getCid());
            ex.setUid(bean.getUid());
            ex.setGid(bean.getGid());
            ex.setGstatus(bean.getGstatus());
            ex.setGpurchaseNumber(bean.getGpurchaseNumber());

            GoodBean g = goodMapper.getGoodById(bean.getGid());
            ex.setGood(g);
            list.add(ex);
        }
        return list;
    }

    @Override
    public int deleteCartByCid(int cid) {
        return shoppingcartMapper.deleteCartByCid(cid);
    }

    @Override
    public int updateCartGoodNumber(int cid, int purchaseNumber) {
        return shoppingcartMapper.updateCartGoodNumber(cid, purchaseNumber);
    }
}
