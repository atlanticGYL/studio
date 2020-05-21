package com.my.studio.adoTest.math;

import java.math.BigDecimal;

public class BigDecimalTest {

    private final static String ORG_NUM = "0.0123456789";

    public static void main(String args[]) {
        BigDecimal bigDecimal = StrTo2Double(ORG_NUM);
        format8(new BigDecimal(ORG_NUM));
        System.out.println(bigDecimal.negate());
        System.out.println(new BigDecimal("0.00"));
    }

    /**
     * 转小数，保留2位
     * @param num
     * @return
     */
    public static BigDecimal StrTo2Double(String num) {
        BigDecimal rst = new BigDecimal(num).setScale(2, java.math.BigDecimal.ROUND_HALF_UP);
        System.out.println(rst);
        return rst;
    }

    /**
     * 格式化8位小数

     * @param num
     * @return
     */
    public static String format8(BigDecimal num) {
        String rst = null;
        if (num == null) {
            rst = "0.00000000";
        }
        rst = num.setScale(8, java.math.BigDecimal.ROUND_HALF_UP).toPlainString();
        System.out.println(rst);
        return rst;
    }

}
