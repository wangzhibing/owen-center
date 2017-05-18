package com.owen.proxy.dynamic.jdk.example1;

import java.io.FileOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import sun.misc.ProxyGenerator;

/***
 * 
 *
 *
 1.什么是动态代理? 答：动态代理可以提供对另一个对象的访问，同时隐藏实际对象的具体事实。 代理一般会实现它所表示的实际对象的接口。代理可以访问实际对象，但是延迟实现实际对象的部分功能， 实际对象实现系统的实际功能，代理对象对客户隐藏了实际对象。客户不知道它是与代理打交道还是与实际对象打交道。
 * 
 * 2.为什么使用动态代理? 答：因为动态代理可以对请求进行任何处理
 * 
 * 3.使用它有哪些好处? 答：因为动态代理可以对请求进行任何处理
 * 
 * 4.哪些地方需要动态代理? 答：不允许直接访问某些类；对访问要做特殊处理等
 * 
 * 
 * 一个典型的动态代理创建对象过程可分为以下四个步骤：

1、通过实现InvocationHandler接口创建自己的调用处理器 IvocationHandler handler = new InvocationHandlerImpl(...);
2、通过为Proxy类指定ClassLoader对象和一组interface创建动态代理类
Class clazz = Proxy.getProxyClass(classLoader,new Class[]{...});
3、通过反射机制获取动态代理类的构造函数，其参数类型是调用处理器接口类型
Constructor constructor = clazz.getConstructor(new Class[]{InvocationHandler.class});
4、通过构造函数创建代理类实例，此时需将调用处理器对象作为参数被传入
Interface Proxy = (Interface)constructor.newInstance(new Object[] (handler));
为了简化对象创建过程，Proxy类中的newInstance方法封装了2~4，只需两步即可完成代理对象的创建。
生成的ProxySubject继承Proxy类实现Subject接口，实现的Subject的方法实际调用处理器的invoke方法，而invoke方法利用反射调用的是被代理对象的的方法（Object result=method.invoke(proxied,args)）


*******自己梳理********
1、创建自己的调用处理器。
2、通过Proxy类，指定的classLoader,interface创建动态代理类。
3、通过反射机制获取动态代理类的构造函数。
4、通过构造函数创建代理类实例。
 * 
 * 
 *
 *
 */
public class HelloWordTest {

    public static void main(String[] args) {
        HelloWorldInter inter = new HelloWorldImpl();
        InvocationHandler handler = new HelloWorldHandler(inter);

        // 创建动态代理对象
        HelloWorldInter proxy = (HelloWorldInter) Proxy
                .newProxyInstance(inter.getClass().getClassLoader(), inter.getClass().getInterfaces(), handler);
        proxy.doSqyHelloWord();
        createProxyClassFile();
        System.out.println("ccc ");
    }

    public static void createProxyClassFile() {
        String name = "ProxyHelloWorldInter";
        byte[] data = ProxyGenerator.generateProxyClass(name, new Class[] { HelloWorldInter.class });
        try {
            FileOutputStream out = new FileOutputStream(name + ".class");
            out.write(data);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
