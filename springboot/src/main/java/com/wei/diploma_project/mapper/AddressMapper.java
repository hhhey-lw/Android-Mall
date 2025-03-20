package com.wei.diploma_project.mapper;

import com.wei.diploma_project.bean.AddressBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 韦龙
* @description 针对表【address】的数据库操作Mapper
* @createDate 2023-04-13 11:29:27
* @Entity com.wei.diploma_project.bean.AddressBean
*/
@Mapper
public interface AddressMapper {

    boolean addAddress(AddressBean e);

    List<AddressBean> getAddress(int uid);

    boolean deleteAddress(int addr_id);

    boolean updateAddr(AddressBean e);

    AddressBean findAddrByAddrId(int addr_id);

    AddressBean getDefaultAddr(int uid);

    boolean updateAddrDefaultStatus(int addr_id, int addr_default_status);
}
