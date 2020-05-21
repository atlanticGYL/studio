package com.my.studio.adoTest.algorithm.maxProfit;

/**
 * 模拟买入-卖出获取最大收益问题
 * priceArr 按时间顺序，可以进行买入-卖出操作顺序
 * O(n) 复杂度
 */
public class MaxProfit {
    // 交易价格样本，索引即时间顺序
    private static int[] priceArr = {8,6,9,5,3,6,4,2,9,4,3,6,4,20,4,5,7,30,26,4,2};
    public static void main(String[] args) {
        // 当前最小元素索引，小于 curMaxIndex，即实际用来计算最大收益的索引
        int curMinIndex = 0;
        // 当前最大元素索引
        int curMaxIndex = 0;
        // 当前最大收益
        int curMaxProfit = 0;
        // 临时最小元素索引，可以大于 curMaxIndex，其元素小于 curMinIndex 的元素，留待计算更大收益
        int tempMinIndex = 0;
        // 按索引即时间顺序遍历
        for (int index = 0; index < priceArr.length; index++) {
            System.out.println("step0:index:" + index);
            if(priceArr[index] <= priceArr[curMinIndex]) {
                // 只要元素更小（价格更低），即更新临时最小元素索引
                tempMinIndex = index;
                System.out.println("step1:tempMinIndex:" + tempMinIndex);
            }
            if (priceArr[index] >= priceArr[curMaxIndex]) {
                // 因为是按时间顺序，所以最大元素索引实时更新
                curMaxIndex = index;
                System.out.println("step2:curMaxIndex:" + curMaxIndex);
            }
            if (curMaxIndex > tempMinIndex && (priceArr[curMaxIndex] - priceArr[tempMinIndex] > curMaxProfit)) {
                // 当临时最小索引可以产生更大收益时，置换当前最小元素索引
                curMinIndex = tempMinIndex;
                System.out.println("step3:curMinIndex:" + curMinIndex);
            }
            // 计算当前最大收益，当遍历结束时，即最终的最大收益
            curMaxProfit = priceArr[curMaxIndex] - priceArr[curMinIndex];
            System.out.println("step4:curMaxProfit:" + curMaxProfit);
        }
    }
}
