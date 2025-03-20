package com.wei.diploma_project.util;

import java.util.*;

// 初始化用户-项目评分矩阵和项目之间的相似度矩阵。
//
// 根据用户-项目评分矩阵，计算项目之间的相似度，使用余弦相似度计算方法。
//
// 为用户生成推荐列表，计算用户对未评分项目的评分预测值，使用加权平均方法计算预测评分。
//
// 对预测评分进行排序，生成推荐列表。
public class ItemBasedCF {
    // 用户-项目评分矩阵  itemId : { userId : rating }
    private Map<Integer, Map<Integer, Integer>> itemUserRatingMatrix;
    // 项目之间的相似度矩阵 itemId : { itemId : similarity }
    private Map<Integer, Map<Integer, Double>> itemSimilarityMatrix;

    // 构造函数，初始化用户-项目评分矩阵和项目之间的相似度矩阵
    public ItemBasedCF(Map<Integer, Map<Integer, Integer>> itemUserRatingMatrix) {
        this.itemUserRatingMatrix = itemUserRatingMatrix;
        this.itemSimilarityMatrix = new HashMap<>();
        calculateItemSimilarity();
    }

    // 计算项目之间的相似度
    private void calculateItemSimilarity() {
        for (int i : itemUserRatingMatrix.keySet()) {   // user-Item 评分表 <itemId:<userId:rating>>
            for (int j : itemUserRatingMatrix.keySet()) {
                if (i == j) {   // 同项目
                    continue;
                }
                // 计算项目i和项目j之间的相似度
                double similarity = calculateSimilarity(i, j);
                // 将相似度存储到相似度矩阵中
                if (!itemSimilarityMatrix.containsKey(i)) {
                    itemSimilarityMatrix.put(i, new HashMap<>());
                }
                itemSimilarityMatrix.get(i).put(j, similarity);
            }
        }
    }

    // 计算项目i和项目j之间的相似度
    private double calculateSimilarity(int i, int j) {
        // 获取评分项目i和项目j的用户集合
        Set<Integer> userSet = new HashSet<>();
        userSet.addAll(itemUserRatingMatrix.get(i).keySet());
        userSet.addAll(itemUserRatingMatrix.get(j).keySet());

        // 计算项目i和项目j的评分向量
        double[] ratingVectorI = new double[userSet.size()];
        double[] ratingVectorJ = new double[userSet.size()];
        int index = 0;
        // 填充评分向量，若用户评分未评分过，则补充0
        for (int user : userSet) {
            ratingVectorI[index] = itemUserRatingMatrix.get(i).containsKey(user) ? itemUserRatingMatrix.get(i).get(user) : 0;
            ratingVectorJ[index] = itemUserRatingMatrix.get(j).containsKey(user) ? itemUserRatingMatrix.get(j).get(user) : 0;
            index++;
        }

        // 计算项目i和项目j之间的余弦相似度
        double dotProduct = 0;
        double normI = 0;
        double normJ = 0;
        for (int k = 0; k < ratingVectorI.length; k++) {
            dotProduct += ratingVectorI[k] * ratingVectorJ[k];
            normI += ratingVectorI[k] * ratingVectorI[k];
            normJ += ratingVectorJ[k] * ratingVectorJ[k];
        }
        double similarity = dotProduct / (Math.sqrt(normI) * Math.sqrt(normJ));

        return similarity;
    }

    // 为用户生成推荐列表
    public List<Integer> recommendItems(int userId, int num) {
        // 获取用户 评分过 的项目集合
        Set<Integer> ratedItems = new HashSet<>();
        for (Integer itemID : itemUserRatingMatrix.keySet()) {
            if (itemUserRatingMatrix.get(itemID).containsKey(userId))
                ratedItems.add(itemID);
        }

        // 计算用户对未评分项目的评分预测值
        Map<Integer, Double> predictedRatings = new HashMap<>();
        for (int i : itemSimilarityMatrix.keySet()) {
            if (ratedItems.contains(i)) {
                continue;
            }
            double predictedRating = predictRating(userId, i);
            predictedRatings.put(i, predictedRating);
        }

        // 对预测评分进行排序，生成推荐列表
        List<Integer> recommendedItems = new ArrayList<>();
        predictedRatings.entrySet().stream()
                .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
                .limit(num)
                .forEach(entry -> recommendedItems.add(entry.getKey()));

        return recommendedItems;
    }

    // 预测用户对项目i的评分
    private double predictRating(int userId, int i) {
        double numerator = 0;
        double denominator = 0;
        for (int j : itemUserRatingMatrix.keySet()) { // 所有项目
            // 同项目 或者 用户对项目j未评价过 则跳过
            if (j == i || !itemUserRatingMatrix.get(j).containsKey(userId)) {
                continue;
            }
            // 不同项目且用户对项目j评价过，利用已知推未知
            double similarity = itemSimilarityMatrix.get(i).get(j);
            double rating = itemUserRatingMatrix.get(j).get(userId);
            // 项目j的平均评价分数
            double ratingJMean = calculateItemMeanRating(j);
            numerator += similarity * (rating - ratingJMean);
            denominator += Math.abs(similarity);
        }
        // 项目i的平均评价分数
        double ratingIMean = calculateItemMeanRating(i);
        return denominator == 0 ? 0 : (ratingIMean + numerator / denominator);
    }

    // 目标项目的平均得分
    private Double calculateItemMeanRating(Integer itemId) {
        double ratingSum = 0;
        int count = 0;
        // 遍历目标项目的用户ID
        for (Integer userId : itemUserRatingMatrix.get(itemId).keySet()) {
            ratingSum += itemUserRatingMatrix.get(itemId).get(userId);
            count++;
        }
        return ratingSum / count;
    }
}
