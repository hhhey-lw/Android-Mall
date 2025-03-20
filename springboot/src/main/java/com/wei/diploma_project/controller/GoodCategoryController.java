package com.wei.diploma_project.controller;

import com.wei.diploma_project.service.GoodCategoryService;
import com.wei.diploma_project.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * User: 韦龙
 * Date: 2023/5/2
 * description:
 */
@RestController
@RequestMapping("/category")
public class GoodCategoryController {
    @Autowired
    private GoodCategoryService goodCategoryService;

    @GetMapping("/all/{pageNum}-{pageSize}")
    public Result getAllCategory(@PathVariable int pageNum, @PathVariable int pageSize) {
        return Result.ok(goodCategoryService.getAllCategory(pageNum, pageSize));
    }

    @PostMapping("/add")
    public Result addCategory(@RequestParam("gcategory_name") String categoryName) {
        if (goodCategoryService.addCategory(categoryName))
            return Result.ok();
        return Result.fail();
    }

    @PostMapping("/update")
    public Result updateCategoryByID(@RequestParam("gcategory_id") int id, @RequestParam("gcategory_name") String newName) {
        if (goodCategoryService.updateCategoryByID(id, newName))
            return Result.ok();
        return Result.fail();
    }

    @PostMapping("/status")
    public Result changeCategoryStatusByID(@RequestParam("gcategory_id") int id) {
        if (goodCategoryService.getStatusByID(id) == 0) { // 0 false 禁用 =》 启用
            if (goodCategoryService.ableCategoryByID(id))
                return Result.ok();
        } else {
            if (goodCategoryService.disableCategoryByID(id))
                return Result.ok();
        }
        return Result.fail();
    }

}
