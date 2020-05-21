package com.my.exam;

public class test2 {

    String str = new String("tarena");
    char[] ch = {'a', 'b', 'c'};

    public void change(String str1, char ch1[]) {
        //引用类型变量，传递的是地址，属于引用传递。
        str1 = "test ok";
        ch1[0] = 'g';
    }

    public static void main(String args[]) {
        test2 ex = new test2();
        ex.change(ex.str, ex.ch);
        System.out.print(ex.str + " and ");
        System.out.print(ex.ch);
    }

}
