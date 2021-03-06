package com.itheima.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author kpwang
 * @create 2020-07-08 16:02
 * 模拟一个消费者
 */
public class Client {
    public static void main(String[] args) {
        final Producer producer=new Producer();
        //producer.saleProduct(10000f);
        /*
        * 动态代理：
        *    特点：字节码随用随创建，随用随加载
        *    作用：不修改源码的基础上对方法加强
        *    分类：
        *        基于接口的动态代理
        *        基于子类的动态代理
        *    基于接口的动态代理：
        *        涉及的类：Proxy
        *        提供者：JDK官方
        *     如何创建代理对象
        *         使用Proxy类中的newProxyInstance方法
        *     创建代理对象的要求
        *         被代理类最少实现一个接口，如果没有则不能使用
        *     newProxyInstance 方法的参数
        *         ClassLoader:类加载器
        *             它是用于加载代理对象字节码的，和被代理对象使用相同的类加载器
        *         Class[] :字节码数组
        *             它是用于让代理对象和被代理对象有相同的方法
        *         InvocationHandler:用于提供增强的代码
        *             让我们写如何代理。一般都是些该接口的实现类，通常情况下都是匿名内部类，但不是必须的
        *             此接口的实现类都是谁用谁写
        * */
        Iproducer proxyProducer = (Iproducer) Proxy.newProxyInstance(producer.getClass().getClassLoader(),
                producer.getClass().getInterfaces(),
                //执行被代理对象的任何接口方法都会经过该方法
                //proxy  代理对象的引用
                //method 当前执行的方法
                //args 当前执行方法所需要的参数
                //return    和被代理对象方法有相同返回值

                new InvocationHandler() {
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Object returnValue=null;
                        //提供增强的代码
                        //1获取方法执行的参数
                        Float money= (Float) args[0];
                        if ("saleProduct".equals(method.getName())){
                            returnValue=method.invoke(producer,money*0.8f);
                        }
                        return returnValue;
                    }
                });
        proxyProducer.saleProduct(10000f);
    }
}
