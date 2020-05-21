package com.my.studio.adoTest.io.nio;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class NioTest {
    public static void main(String args[]) {
        int COUNT_BITS = Integer.SIZE - 3;
        int CAPACITY   = (1 << COUNT_BITS) - 1;
        int c = CAPACITY + 1;
        System.out.println(c & CAPACITY);
        System.out.println(Integer.toBinaryString(CAPACITY));
        System.out.println(Integer.toBinaryString((1 << (Integer.SIZE - 1)) -1));
    }

    private static void nio() throws IOException {
        FileChannel inChannel = new FileInputStream(new File("F:\\WOW函数.txt")).getChannel();
        /*File outFile = new File("F:\\1.txt");
        if (!outFile.exists()) {
            outFile.createNewFile();
        }*/
        FileChannel outChannel = new RandomAccessFile("F:\\1.txt", "rw").getChannel();
        int maxCount = 1 * 1024 * 1024;
        long size = inChannel.size();
        long position = 0;
        while (position < size) {
            position += inChannel.transferTo(position, maxCount, outChannel);
        }
        inChannel.close();
        outChannel.close();
    }
}
