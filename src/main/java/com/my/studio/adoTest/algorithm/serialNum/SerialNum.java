package com.my.studio.adoTest.algorithm.serialNum;

import java.util.Scanner;

/**
 * 输入一组数字
 * 识别其中的连续部分，并转换为 start-end 的格式
 * 例-输入：1,3,5,2,3,4,5,6,6,7,8,9,8
 * 例-输出：1,3,5,2-6,6-9,8
 * O(n) 复杂度
 * 也可以通过递归实现，但是栈深增加，对内存的使用应该会增大
 */
public class SerialNum {
    private static int beginNum = 0;
    private static int lastNum = 0;

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String inStr = scanner.nextLine();
            String[] numStrs = inStr.replaceAll(" ", "").split(",");
            System.out.println(exec(numStrs));
        }
    }
    private static String exec(String[] inArr) {
        StringBuilder sb = new StringBuilder();
        if (null != inArr && inArr.length > 0) {
            for (int index = 0; index < inArr.length; index++) {
                int curNum = Integer.parseInt(inArr[index].trim());
                if (index == 0) {
                    // 首次执行
                    beginNum = curNum;
                    lastNum = curNum;
                    sb.append(beginNum);
                    continue;
                }
                if (curNum - lastNum == 1) {
                    // 数字连续
                    lastNum = curNum;
                    continue;
                }
                // 数字不连续
                if (lastNum != beginNum) {
                    // 之前的数字是一段连续的
                    sb.append("-").append(lastNum);
                }
                // 这里有两种情况
                // 非最后一个数字，拼接的 curNum 作为开始，如果下一数字不连续，下一迭代中会再次执行，如果下一数字连续，会在遇到不连续数字后执行上一行
                // 最后一个数字，执行后为 ****,end，此时 beginNum==lastNum，循环体后的代码不执行，已满足需求
                sb.append(",").append(curNum);
                beginNum = curNum;
                lastNum = curNum;
            }
            if (lastNum != beginNum) {
                sb.append("-").append(lastNum);
            }
        }
        return sb.toString();
    }
}
