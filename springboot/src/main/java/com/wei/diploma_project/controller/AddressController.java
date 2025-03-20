package com.wei.diploma_project.controller;

import com.wei.diploma_project.bean.AddressBean;
import com.wei.diploma_project.service.AddressService;
import com.wei.diploma_project.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * User: 韦龙
 * Date: 2023/4/13
 * description:
 */
@RestController
@RequestMapping("/addr")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/add")
    public Result addAddress(@RequestBody AddressBean e) {
        System.err.println(e);
        if (addressService.addAddress(e)) {
            return Result.ok();
        }
        return Result.fail();
    }

    @GetMapping("/get/{uid}")
    public Result getAddress(@PathVariable("uid") int uid) {
        System.err.println(String.format("uid = %d", uid));
        if (uid < 0)
            return Result.fail();
        return Result.ok(addressService.getAddress(uid));
    }

    @DeleteMapping("/del/{addr_id}")
    public Result delAddress(@PathVariable("addr_id") int addr_id) {
        System.err.println(String.format("del addr addr_id = %d", addr_id));
        if (addr_id < 0)
            return Result.fail();
        return Result.ok(addressService.deleteAddress(addr_id));
    }

    @GetMapping("/find/{addr_id}")
    public Result getSingleAddress(@PathVariable("addr_id") int addr_id) {
        System.err.println(String.format("addr_id = %d", addr_id));
        if (addr_id < 0)
            return Result.fail();
        return Result.ok(addressService.findAddrByAddrId(addr_id));
    }

    @PostMapping("/update")
    public Result updateAddress(@RequestBody AddressBean e) {
        System.err.println(String.format("AddressBean = " + e));
        if (addressService.updateAddr(e)) {
            return Result.ok();
        }
        return Result.fail();
    }

    @GetMapping("/default/{uid}")
    public Result getDefaultAddr(@PathVariable("uid") int uid) {
        System.err.println(String.format("uid = " + uid));
        AddressBean defaultAddr = addressService.getDefaultAddr(uid);
        if (defaultAddr != null) {
            return Result.ok(defaultAddr);
        }
        return Result.fail();
    }

    @PostMapping("/default/{uid}-{addr_id}")
    public Result setDefaultAddr(@PathVariable("uid") int uid, @PathVariable("addr_id") int addr_id) {
        System.err.println(String.format("uid = " + addr_id));
        System.err.println(String.format("addr_id = " + addr_id));
        if (addressService.setDefaultAddr(uid, addr_id))
            return Result.ok();
        return Result.fail();
    }
}
