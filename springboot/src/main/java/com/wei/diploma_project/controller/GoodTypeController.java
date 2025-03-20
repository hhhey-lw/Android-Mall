package com.wei.diploma_project.controller;

import com.wei.diploma_project.service.GoodTypeService;
import com.wei.diploma_project.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * User: 韦龙
 * Date: 2023/5/2
 * description:
 */
@RestController
@RequestMapping("/type")
public class GoodTypeController {
    @Autowired
    private GoodTypeService goodTypeService;

    @GetMapping("/all/{gcategory_id}/{pageNum}-{pageSize}")
    public Result getAllCategory(@PathVariable int gcategory_id, @PathVariable int pageNum, @PathVariable int pageSize) {
        return Result.ok(goodTypeService.getAllTypeByCategoryID(gcategory_id, pageNum, pageSize));
    }

    @PostMapping("/add")
    public Result addCategory(@RequestParam("gcategory_id") int gcategory_id, @RequestParam("gtype_name") String gtype_name) {
        if (goodTypeService.addType(gcategory_id, gtype_name))
            return Result.ok();
        return Result.fail();
    }

    @PostMapping("/update")
    public Result updateCategoryByID(@RequestParam("gcategory_id") int gcategory_id, @RequestParam("gtype_id") int gtype_id, @RequestParam("newName") String newName) {
        if (goodTypeService.updateTypeByID(gtype_id, gcategory_id, newName))
            return Result.ok();
        return Result.fail();
    }

    @PostMapping("/status")
    public Result changeCategoryStatusByID(@RequestParam("gtype_id") int gtype_id) {
        if (goodTypeService.getStatusByID(gtype_id) == 0) { // 0 false 禁用 =》 启用
            if (goodTypeService.ableTypeByID(gtype_id))
                return Result.ok();
        } else {
            if (goodTypeService.disableTypeByID(gtype_id))
                return Result.ok();
        }
        return Result.fail();
    }

}
