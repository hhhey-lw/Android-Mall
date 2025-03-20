package com.wei.diploma_project.controller;

import com.wei.diploma_project.bean.GoodBean;
import com.wei.diploma_project.service.RecommendationService;
import com.wei.diploma_project.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * User: 韦龙
 * Date: 2023/4/7
 * description:
 */
@RestController
@RequestMapping("/rec")
public class RecommondationController {

    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/list/{uid}")
    public Result getRecommendationList(@PathVariable Integer uid) {
        if (uid == null || uid == -1) {
            return Result.ok(recommendationService.getSaleBestList(6));
        }
        int recNum = 5;
        return Result.ok(recommendationService.getRecommendationList(uid, recNum));
    }

    @GetMapping("/sale/best")
    public Result getSaleBestList() {
        return Result.ok(recommendationService.getSaleBestList(6));
    }
}
