package com.gt.common;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

public class Person extends People{

    public static void main(String[] args) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Person p = new Person();
        for(Field f :p.getClass().getDeclaredFields()){
        	System.out.println(f);
        }
        Field field = p.getClass().getDeclaredField("NAME");
        Field modifiers = field.getClass().getDeclaredField("modifiers");
        modifiers.setAccessible(true);
        modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        //field.setAccessible(true);
        field.set(p,"Hello");
        System.out.println(field.get(p));
        p.getClass().getDeclaredMethod("printName", null).invoke(p, null);
        //p.printName();
    }

    final static String NAME ="Clive"; /*(null!=null?"Clive":"Clive");*/
    public Person() {

    }
    public void printName() {
        System.out.println(NAME);
        StringBuilder a = new StringBuilder();
    }
}

class People {
	final  String FNAME ="hu";
}