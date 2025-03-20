package com.wei.diploma_project.service;


import com.wei.diploma_project.bean.AddressBean;

import java.util.List;

/**
* @author 韦龙
* @description 针对表【address】的数据库操作Service
* @createDate 2023-04-13 11:29:27
*/
public interface AddressService {
    boolean addAddress(AddressBean e);

    List<AddressBean> getAddress(int uid);

    boolean deleteAddress(int addr_id);

    boolean updateAddr(AddressBean e);

    AddressBean findAddrByAddrId(int addr_id);

    AddressBean getDefaultAddr(int uid);

    boolean setDefaultAddr(int uid, int addr_id);
}
