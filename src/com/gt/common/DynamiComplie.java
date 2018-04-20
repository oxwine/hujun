package com.gt.common;

import sun.misc.Launcher;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class DynamiComplie {
    public static void main(String[] arg) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, MalformedURLException {
        JavaCompiler compiler =  ToolProvider.getSystemJavaCompiler();
        int result = compiler.run(null,null,null,"E:/TestJava.java");
        System.out.println(result==0?"success":"failed");
       // Launcher.AppClassLoader.getSystemClassLoader();
     //Class cl = ClassLoader.getSystemClassLoader().loadClass("TestJava");
//        cl.getMethod("main",String[].class).invoke(null,(Object) new String[]{});

        URL[] urls = new URL[]{new URL("file://"+"E:/")};
        Class cl = new URLClassLoader(urls).loadClass("TestJava");
        cl.getMethod("main",String[].class).invoke(null,(Object) new String[]{});

        System.out.println(cl.getClassLoader().getResource("/"));
        System.out.println(cl.getClassLoader().getParent());

    }
}
