package com.gt.common;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyClassLoader extends ClassLoader {

    private byte[] results;

    public MyClassLoader(String pathName) {
        //拿到class转成的字节码文件
        results = loadClassFile(pathName);
    }

    public static void main(String[] args) {
        //初始化我们的classloader，同时拿到class所转成的字节码文件
        MyClassLoader classLoader = new MyClassLoader("F:\\Test.class");
        try {
            //这里要把包路径传入进去
            Class<?> clazz = classLoader.loadClass("com.ljx.yyy.Test");
            Object o = clazz.newInstance();

            //通过反射机制调用我们的Test.java中的printToString方法
            Method method = clazz.getMethod("printToString");
            method.invoke(clazz.newInstance());
            System.out.println(o.getClass().getClassLoader().toString());

            Method[] methods = clazz.getMethods();
            for (int i = 0; i < methods.length; i++) {
                //获取类中的方法名字
                String methodName = methods[i].getName();
                System.out.println("MethodName : " + methodName);
                Class<?>[] params = methods[i].getParameterTypes();
                for (int j = 0; j < params.length; j++) {
                    //获取方法中的参数类型
                    System.out.println("ParamsType : " + params[j].toString());
                }
            }

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    //把我们的class文件转成字节码，用于classloader动态加载
    private byte[] loadClassFile(String classPath) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            FileInputStream fi = new FileInputStream(classPath);
            BufferedInputStream bis = new BufferedInputStream(fi);
            byte[] data = new byte[1024 * 256];
            int ch = 0;
            while ((ch = bis.read(data, 0, data.length)) != -1) {
                bos.write(data, 0, ch);
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return bos.toByteArray();

    }

    @Override
    protected Class<?> loadClass(String arg0, boolean arg1)
            throws ClassNotFoundException {
        Class<?> clazz = findLoadedClass(arg0);
        if (clazz == null) {
            if (getParent() != null) {
                try {
                    //这里我们要用父加载器加载如果加载不成功会抛异常
                    clazz = getParent().loadClass(arg0);
                } catch (Exception e) {
                    //我们自定义的类加载器的父类 sun.misc.Launcher$AppClassLoader@c387f44
                    System.out.println("getParent : " + getParent());
                    //父类的父类 sun.misc.Launcher$ExtClassLoader@659e0bfd
                    System.out.println("getParent.getparent : " + getParent().getParent());
                    //父类的父类的父类 为null 也就是我们的Bootstrap ClassLoader 因为它是JVM生成的由C++实现；
                    //所以拿到的是空
                    System.out.println("getParent.getparent.getparent : " + getParent().getParent().getParent());
                    System.out.println("父类ClassLoader加载失败！");
                }
            }

            if (clazz == null) {
                clazz = defineClass(arg0, results, 0, results.length);
            }
        }

        return clazz;
    }
}
