package com.my.studio.adoTest.proxy;

import com.my.studio.adoTest.proxy.interFace.ICar;
import com.my.studio.adoTest.proxy.interFace.RedCar;

/**
 * 静态代理（支持接口和继承）
 * 被代理对象（RedCar）与代理对象（CarProxy）需要一起实现相同的接口（ICar）或者是继承相同父类
 * 优点：可以做到在不修改目标对象（RedCar）的功能前提下，对目标功能扩展
 * 缺点：因为代理对象（CarProxy）需要与目标对象（RedCar）实现一样的接口，所以会有很多代理类，类太多；
 * 同时，一旦接口（ICar）增加方法（showName）,目标对象与代理对象都要维护
 */
public class StaticProxy {
    static class CarProxy implements ICar {
        private ICar iCar;

        public CarProxy(ICar iCar) {
            this.iCar = iCar;
        }

        @Override
        public void show() {
            System.out.println("show before");
            iCar.show();
            System.out.println("show after");
        }

        @Override
        public void showName(String name) {
            System.out.println("show before");
            iCar.showName(name);
            System.out.println("show after");
        }
    }

    public static void main(String[] args) {
        ICar redCar = new RedCar();
        CarProxy carProxy = new CarProxy(redCar);
        carProxy.show();
    }

}
