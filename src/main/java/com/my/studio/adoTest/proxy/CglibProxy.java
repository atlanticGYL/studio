package com.my.studio.adoTest.proxy;

import com.my.studio.adoTest.proxy.interFace.GreenCar;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Cglib代理，也叫作子类代理，使用继承目标类以目标对象子类的方式实现代理
 * 在内存中构建一个子类对象从而实现对目标对象功能的扩展
 * Cglib是第三方提供的所以使用的时候需要导入相关的jar包：cglib.jar 和 asm.jar，这里使用 spring 框架中的类
 */
public class CglibProxy {
    public static void main(String[] args) {
        // 实例化一个增强器，也就是cglib中的一个class generator
        Enhancer enhancer = new Enhancer();
        // 设置目标类
        enhancer.setSuperclass(GreenCar.class);
        // 设置拦截对象
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                String methodName = method.getName();
                System.out.println(methodName + " show before");
                //此处一定要使用proxy的invokeSuper方法来调用目标类的方法
                methodProxy.invokeSuper(o, objects);
                System.out.println(methodName + " show after");
                return null;
            }
        });
        // 生成代理类并返回一个实例
        GreenCar greenCarProxy = (GreenCar) enhancer.create();
        greenCarProxy.showName("Koenigsegg");
        greenCarProxy.show();
    }
}
