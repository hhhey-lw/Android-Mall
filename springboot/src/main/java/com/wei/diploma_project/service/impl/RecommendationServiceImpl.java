package com.wei.diploma_project.service.impl;

import com.wei.diploma_project.bean.CommentBean;
import com.wei.diploma_project.bean.GoodBean;
import com.wei.diploma_project.mapper.GoodMapper;
import com.wei.diploma_project.mapper.OrderMapper;
import com.wei.diploma_project.service.CommentService;
import com.wei.diploma_project.service.RecommendationService;
import com.wei.diploma_project.util.ItemBasedCF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: 韦龙
 * Date: 2023/4/7
 * description:
 */
@Service
public class RecommendationServiceImpl implements RecommendationService {

    @Autowired
    private GoodMapper goodMapper;
    @Autowired
    CommentService commentService;
    @Autowired
    OrderMapper orderMapper;

    @Override
    public List<GoodBean> getRecommendationList(int uid, int recNum) {
        List<Integer> gidList = getRecList(uid, recNum);
        List<GoodBean> goodList = new ArrayList<>();
        for (Integer gid : gidList) {
            goodList.add(goodMapper.getGoodById(gid));
        }
        goodList.addAll(getSaleBestList(6));
        return goodList;
    }

    @Override
    public List<GoodBean> getSaleBestList(int num) {
        return goodMapper.getSaleBestList(num);
    }

    private List<Integer> getRecList(int targetID, int recNum) {
        // 构造用户-项目评分矩阵
        Map<Integer, Map<Integer, Integer>> itemUserRatingMatrix = new HashMap<>();
        // 全部商品的ID
        List<Integer> goodID = goodMapper.getAllGoodID();
        for (Integer gid : goodID) {
            Map<Integer, Integer> userRatings = new HashMap<>();
            List<CommentBean> comments = commentService.getAllCommentByGid(gid);
            // 某商品的全部评论，uid只能唯一，由于用户多次评论，需合并
            for (CommentBean comment : comments) {
                if (!userRatings.containsKey(comment.getUid()))
                    userRatings.put(comment.getUid(), comment.getCDegree());
                else {
                    userRatings.replace(comment.getUid(), (userRatings.get(comment.getUid()) + comment.getCDegree()) / 2);
                }
            }
            itemUserRatingMatrix.put(gid, userRatings);
        }
        // 下单但未评价的商品默认为5星好评
        for (Integer gid : goodID) {
            // 下单该商品但未评价的uid
            List<Integer> uids = orderMapper.getItemUserIDListByGID(gid);
            for (Integer uid : uids) {
                // 用户下单了但未评价
                if (!itemUserRatingMatrix.get(gid).containsKey(uid)) {
                    itemUserRatingMatrix.get(gid).put(uid, 5);
                }
            }
        }

        // 构造基于项目的协同过滤算法对象
        ItemBasedCF itemBasedCF = new ItemBasedCF(itemUserRatingMatrix);

        // 测试推荐功能
        List<Integer> recommendedItems;
        recommendedItems = itemBasedCF.recommendItems(targetID, recNum);
        // System.out.println("用户ID 13 recommended items: " + recommendedItems);
        return recommendedItems;
    }
}
