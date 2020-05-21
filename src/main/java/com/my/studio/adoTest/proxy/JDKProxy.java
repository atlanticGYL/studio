package com.my.studio.adoTest.proxy;

import com.my.studio.adoTest.proxy.interFace.GreenCar;
import com.my.studio.adoTest.proxy.interFace.GreenSubCar;
import com.my.studio.adoTest.proxy.interFace.ICar;
import com.my.studio.adoTest.proxy.interFace.RedCar;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK 动态代理（仅支持接口，不支持继承）
 * 特点：能够在程序运行时 JVM 才RedCar为被代理对象（RedCar）生成代理对象（CarProxy）
 * 代理对象（CarProxy）不需要实现接口，但是目标对象（RedCar）一定要实现接口
 * 如果被代理对象（RedCar）未实现任何接口，[1]处 java.lang.ClassCastException: com.sun.proxy.$Proxy0 cannot be cast to 被代理对象.class 异常
 */
public class JDKProxy {
    /**
     * 代理对象，实现 InvocationHandler
     */
    static class CarProxy implements InvocationHandler {
        // 被代理对象（RedCar）
        private Object target;

        public void setTarget(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println(args[0] + " show before");
            method.invoke(target, args);
            System.out.println(args[0] + " show after");
            return null;
        }
    }

    /**
     * 代理对象工厂
     */
    static class ProxyFactory {
        /**
         * @param target 被代理对象（RedCar）
         * @return
         */
        public static Object getProxy(Object target) {
            CarProxy handler = new CarProxy();
            handler.setTarget(target);
            Object proxy = Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), handler);
            return proxy;
        }
    }

    /**
     * 仅支持接口，不支持继承
     * @param args
     */
    public static void main(String[] args) {
        // 成功
        implementsTest();
        // 失败
        extendsTest();
    }

    private static void implementsTest() {
        ICar redCar = new RedCar();
        // [1]
        ICar carProxy = (ICar) ProxyFactory.getProxy(redCar);
        carProxy.showName("Lamborghini");
    }

    private static void extendsTest() {
        GreenCar greenCar = new GreenSubCar();
        // [1]
        GreenCar carProxy = (GreenCar) ProxyFactory.getProxy(greenCar);
        carProxy.showName("Lamborghini");
    }
}
