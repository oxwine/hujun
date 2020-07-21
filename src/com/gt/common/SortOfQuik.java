package com.gt.common;

import java.util.Arrays;

public class SortOfQuik {

	public static void main(String[] args) {
		int[] num = {6,7,2,3,9,10,1,12,5};
		SortOfQuik.sortOfs(0, num.length-1, num);
//		System.out.print(target);
//		System.out.println(Arrays.toString(num));
//		int target1 = SortOfQuik.sort(0, target-1, num);
//		System.out.print(target1);
		System.out.print(Arrays.toString(num));

	}
	public static void sortOfs(int start,int end,int[] num) {
		if(start >= end) {
			return;
		}
		int pivot=SortOfQuik.sort(start, num.length-1, num);
		sortOfs(0,pivot-1,num);
		sortOfs(pivot+1,end,num);
		
	}
	public static int sort(int start,int end,int[] num) {
		int len = num.length;
		
		int pivot =num[start];
		int left = start;
		int right=end;
		while(left<right) {
			
			while(left<right&&num[right]>pivot) {
				right--;
				//System.out.println(right);
			}
			
			while(left<right&&num[left]<=pivot) {
				left++;
				//System.out.println(left);
			}
			int tmp = num[right];
			num[right] = num[left];
			num[left] = tmp;
			//System.out.println(num[left]+":"+num[right]);
		}
		int tmp = num[left];
		num[left] = pivot;
		num[start] = tmp;

		
		return left;	
	}
}
