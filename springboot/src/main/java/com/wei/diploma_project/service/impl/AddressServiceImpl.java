package com.wei.diploma_project.service.impl;

import com.wei.diploma_project.bean.AddressBean;
import com.wei.diploma_project.mapper.AddressMapper;
import com.wei.diploma_project.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 韦龙
* @description 针对表【address】的数据库操作Service实现
* @createDate 2023-04-13 11:29:27
*/
@Service
public class AddressServiceImpl
implements AddressService {

    private AddressMapper addressMapper;

    @Autowired
    public void setAddressMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    @Override
    public boolean addAddress(AddressBean e) {
        if (e == null || e.getUid() == null)
            return false;
        return addressMapper.addAddress(e);
    }

    @Override
    public List<AddressBean> getAddress(int uid) {
         return addressMapper.getAddress(uid);
    }

    @Override
    public boolean deleteAddress(int addr_id) {
        return addressMapper.deleteAddress(addr_id);
    }

    @Override
    public boolean updateAddr(AddressBean e) {
        return addressMapper.updateAddr(e);
    }

    @Override
    public AddressBean findAddrByAddrId(int addr_id) {
        return addressMapper.findAddrByAddrId(addr_id);
    }

    @Override
    public AddressBean getDefaultAddr(int uid) {
        return addressMapper.getDefaultAddr(uid);
    }

    @Override
    public boolean setDefaultAddr(int uid, int addr_id) {
        // 拿用户默认地址！ 万一没有默认地址！！！
        AddressBean defaultAddr = addressMapper.getDefaultAddr(uid);
        if (defaultAddr != null)
            addressMapper.updateAddrDefaultStatus(defaultAddr.getAddrId(), 0);
        // 先让默认置0 再将目标置1

        if (addressMapper.updateAddrDefaultStatus(addr_id, 1))
            return true;

        return false;
    }


}
