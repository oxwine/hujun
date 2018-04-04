package com.gt.common;

public class TestGC {

//	-Xms20M -Xmx20M -Xmn10M  -XX:+PrintGCDetails -XX:SurvivorRatio=8 
//			-XX:PretenureSizeThreshold=3145728
	public static void main(String[] args) {
		
		byte[] a5 = new byte[4*_1MB];
		System.out.println("溢出打印");

	}
	
	private static final int _1MB = 1024*1024;
	
	public static void testAllocation() {
		byte[] a1,a2,a3,a4;
		
		a1 = new byte[2*_1MB];
		a2 = new byte[2*_1MB];
		a3 = new byte[2*_1MB];
		a4 = new byte[4*_1MB];
	}

}
