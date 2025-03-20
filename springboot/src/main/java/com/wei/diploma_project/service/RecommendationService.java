package com.wei.diploma_project.service;

import com.wei.diploma_project.bean.GoodBean;
import com.wei.diploma_project.util.Result;

import java.util.List;

/**
 * User: 韦龙
 * Date: 2023/4/7
 * description:
 */
public interface RecommendationService {
    List<GoodBean> getRecommendationList(int uid, int recNum);

    List<GoodBean> getSaleBestList(int num);
}
