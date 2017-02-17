package com.gt.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * ����
 * 
 * @author pan
 * 
 */
public class RefletUtil {

	/**
	 * �������ĳ��ĳ����
	 * 
	 * @param className
	 *            ��·��
	 * @param methodName
	 *            ��������
	 * @param args
	 *            ����
	 * @return
	 * @throws Exception
	 */
	public static String invokeMethod(String className, String methodName,
			Object[] args) {

		String result = null;
		try {
			Class ownerClass = Class.forName(className);
			Class[] argsClass = null;
			if (args != null) {
				argsClass = new Class[args.length];
				for (int i = 0, j = args.length; i < j; i++) {
					argsClass[i] = args[i].getClass();
				}
			}
			Method method = ownerClass.getMethod(methodName, argsClass);
			result = (String) method.invoke(ownerClass.newInstance(), args);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return result;
	}

}
