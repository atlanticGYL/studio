package com.my.studio.adoTest.algorithm.topKandSo;
// 设计一个“GiveMeK”类，用来返回在一个无穷的整数流(int stream)中第k大的数。
// 1. 请在GiveMeK类中加入需要的成员。
// 2. 实现类的构造方法 : 输入为k的值和数据流中一开始的数据，构造一个GiveMeK实例。
// 3. 实现showMe方法 : 输入一个新的数据流元素，返回当前流的第k大的数。
// 假设k >= 1，并且一开始的数据流中已经有至少k-1个元素。
// 注意: 数据流为无穷大，showMe方法可被调用任意多次。请注意空间使用情况。

// 例子：
// int k = 2;							// 第2大的元素
// int[] s = {7, 3, 5, 8};				// 一开始数据流中存在的4个元素，都为整数
// GiveMeK gmk = new GiveMeK(k, s);	    // 构造一个新实例
// gmk.showMe(2);		        // 往数据流中加入新元素2，当前数据流为{7, 3, 5, 8, 2}， 返回第2大元素7
// gmk.showMe(9);		        // 往数据流中加入新元素9，当前数据流为{7, 3, 5, 8, 2, 9}， 返回第2大元素8
// gmk.showMe(9);		        // 往数据流中加入新元素9，当前数据流为{7, 3, 5, 8, 2, 9, 9}， 返回第2大元素9
// gmk.showMe(1);		        // 往数据流中加入新元素1，当前数据流为{7, 3, 5, 8, 2, 9, 9, 1}， 返回第2大元素9

// 请在以下代码中完成

// 不允许使用JDK提供的排序、集合实现。
// 不允许使用JDK提供的排序、集合实现。
// 不允许使用JDK提供的排序、集合实现。

import sun.security.util.Length;

// GiveMeK defines a class with a single method “showMe” that shows the kth largest element in a unbounded int stream
public class GiveMeK {
    
    public static void main(String[] args)
    {
        int[] s = {7, 3, 5, 8};
        int k =2;
        int k_val;
        GiveMeK giveMeK = new GiveMeK(k, s);
        //giveMeK.quickSort(giveMeK.stream, 0, giveMeK.stream.length-1);
        
        System.out.println(giveMeK.k_val);
        System.out.println(giveMeK.showMe(2));
        System.out.println(giveMeK.showMe(9));
        System.out.println(giveMeK.showMe(9));
        System.out.println(giveMeK.showMe(1));
    }
    // 1.add necessary fields
    int[] stream;
    int k;
    int k_val;
    
    // 2.construct an instance of GiveMeK class
    public GiveMeK(int k, int[] stream) {
        this.stream = stream;
        this.k = k;
        this.quickSort(this.stream, 0, this.stream.length-1);
        this.k_val = stream[k];
    }
    
    // 3.showMe adds a new value to the stream and returns the Kth largest element seen so far
    public int showMe(int value) {
//        if(value > k_val)
//        {
//            // 复制数组。并将value插入到对应位置
//            // 将新数组赋值给stream
//            int[] temp = new int[stream.length + 1];
//            System.arraycopy(stream, 0, temp,0, stream.length);
//            temp[temp.length-1] = value;
//            for(int a = stream.length-1-k; k< temp.length-1; k++)
//            {
//                int c = temp[temp.length -1];
//                if(temp[a] > c)
//                {
//                    temp[temp.length-1] = temp[a];
//                    temp[a] = c;
//                }
//            }
//        }
//        else
//        {
//            // 复制数组。并将value插入到对应位置
//            // 将新数组赋值给stream
//            int[] temp = new int[stream.length + 1];
//
//            System.arraycopy(stream, stream.length-k, temp,temp.length-k, k);
//            //temp[temp.length-1] = value;
//            for(int a = stream.length-1-k; k< temp.length-1; k++)
//            {
//                int c = temp[temp.length -1];
//                if(temp[a] > c)
//                {
//                    temp[temp.length-1] = temp[a];
//                    temp[a] = c;
//                }
//            }
//        }
        // 更新对应倒数k位的数字
        
        // 先实现再说----
        int[] temp = new int[stream.length + 1];
        System.arraycopy(stream, 0, temp,0, stream.length);
        temp[stream.length] = value;
        this.stream = temp;
        this.quickSort(stream,0,stream.length-1);
        k_val = stream[stream.length-k];
        return k_val;
    }
    
    private void quickSort(int[] arr, int low, int high) {
        
        if (low < high) {
            // 找寻基准数据的正确索引
            int index = getIndex(arr, low, high);
            
            // 进行迭代对index之前和之后的数组进行相同的操作使整个数组变成有序
            quickSort(arr, 0, index - 1);
            quickSort(arr, index + 1, high);
        }
        
    }
    
    private static int getIndex(int[] arr, int low, int high) {
        // 基准数据
        int tmp = arr[low];
        while (low < high) {
            // 当队尾的元素大于等于基准数据时,向前挪动high指针
            while (low < high && arr[high] >= tmp) {
                high--;
            }
            // 如果队尾元素小于tmp了,需要将其赋值给low
            arr[low] = arr[high];
            // 当队首元素小于等于tmp时,向前挪动low指针
            while (low < high && arr[low] <= tmp) {
                low++;
            }
            // 当队首元素大于tmp时,需要将其赋值给high
            arr[high] = arr[low];
            
        }
        // 跳出循环时low和high相等,此时的low或high就是tmp的正确索引位置
        // 由原理部分可以很清楚的知道low位置的值并不是tmp,所以需要将tmp赋值给arr[low]
        arr[low] = tmp;
        return low; // 返回tmp的正确位置
    }
}
