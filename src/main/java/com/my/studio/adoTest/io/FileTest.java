package com.my.studio.adoTest.io;

import java.io.File;
import java.io.IOException;

public class FileTest {
    public static void main(String args[]) throws IOException {
        File file1 = new File(".\\1.txt");
        /*if (!file1.getParentFile().exists()) {
            file1.getParentFile().mkdirs();
        }
        if (!file1.exists()) {
            file1.createNewFile();
        }*/
        // .\1.txt
        System.out.println("file1.getPath():" + file1.getPath());
        // 1.txt
        System.out.println("file1.getName():" + file1.getName());
        // C:\work\IntelliJWork\studio\.\1.txt
        System.out.println("file1.getAbsolutePath():" + file1.getAbsolutePath());
        // C:\work\IntelliJWork\studio\1.txt
        System.out.println("file1.getCanonicalPath():" + file1.getCanonicalPath());
        System.out.println("file1.getFreeSpace():" + file1.getFreeSpace());
        System.out.println("file1.getTotalSpace():" + file1.getTotalSpace());
        System.out.println("file1.getUsableSpace():" + file1.getUsableSpace());

        /*File file2 = new File("F:\\aa", "1.txt");
        File file3 = new File(file1.getParent(),"2.txt");
        file3.createNewFile();*/

    }
}
