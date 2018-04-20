package com.gt.common;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class test1 {
    public static void main(String[] ar) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class<?> c = methodClass.class;
        //Class b = Class.forName(java.test1.java.methodClass);
        Object object = c.newInstance();
        Method[] methods = c.getMethods();
        Method[] declaredMethods = c.getDeclaredMethods();
        //获取methodClass类的add方法
        Method method = c.getMethod("add", int.class,int.class);
        //getMethods()方法获取的所有方法
        System.out.println("getMethods获取的方法：");
        for(Method m:methods)
            System.out.println(m);
        //getDeclaredMethods()方法获取的所有方法
        System.out.println("getDeclaredMethods获取的方法：");
        for(Method m:declaredMethods)
            System.out.println(m);

        Method n = c.getDeclaredMethod("sub1",int.class,int.class);
        n.setAccessible(true);
        int adddd = (int) n.invoke(object,1,2);
        System.out.println(adddd);

        methodClass ob = (methodClass)c.getConstructor(null).newInstance(null);

        System.out.println( ob.sub(1,4));
//数组
        Object arry = Array.newInstance(String.class,10);
        Array.set(arry,0,"10");
        System.out.println(Array.get(arry,0));

        String[] aa = new String[5];
        aa.getClass().newInstance();

    }
}
class methodClass {
    public final int fuck = 3;
    public  methodClass(){}
    public int add(int a,int b) {
        return a+b;
    }
    public int sub(int a,int b) {
        return a+b;
    }

    private  int sub1(int a ,int b) {
        return a+b;
    }
}