package com.owen.proxy.dynamic.jdk.example1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/***
 * 实现方法调用前后，做其他事情
 * @author owen
 *
 */
public class HelloWorldHandler implements InvocationHandler{

    //代理原始对象
    private Object obj;
    
    public HelloWorldHandler(Object obj){
        super();
        this.obj = obj;
    }
    
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        
        Object result = null;
        
        //调用之前
        this.doBefore();
        System.out.println("00000000000");
        result = method.invoke(obj, args);
        System.out.println("111111111111");
        
        //调用之后
        this.doAfter();
        
        
        return result;
    }
    
    private void doBefore(){
        System.out.println("before method invoke");
    }

    
    private void doAfter(){
        System.out.println("after method invoke");
    }
}
