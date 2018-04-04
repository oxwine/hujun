package com.gt.common;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class GcTest {
	public Object instance = null;
	
	private static final int mb = 1024*1024;
	
	private byte[] bigsize = new byte[2 * mb];

	public static void main(String[] args) {
		GcTest obA = new GcTest();
		GcTest obB = new GcTest();
		
		obA.instance = obB;
		obB.instance = obA;
		
		obA = null;
		obB = null;
//		System.gc();
		Map<Thread,StackTraceElement[]> treads = Thread.getAllStackTraces();
//		for(StackTraceElement se :Thread.getAllStackTraces().get(Thread.currentThread().getName())){
//			System.out.println("\t"+se);
//		}
		Set<Entry<Thread, StackTraceElement[]>> thread = treads.entrySet();
		for(Entry<Thread, StackTraceElement[]> t:thread){
			
			Thread th = (Thread)t.getKey();
			if(th.equals(Thread.currentThread()))
				continue;
			
			for(StackTraceElement e : t.getValue()) {
				System.out.println("\t"+e);
			}
			
		}
	}

}
