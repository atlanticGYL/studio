package com.my.studio.adoTest.proxy.interFace;

public class RedCar implements ICar {
    @Override
    public void show() {
        System.out.println("I`m red");
    }

    @Override
    public void showName(String name) {
        System.out.println("I`m red" + name);
    }
}
