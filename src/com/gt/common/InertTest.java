package com.gt.common;

public class InertTest {
	public static int[] sort(int[] array) {
		int j;
		// 从下标为1的元素开始选择合适的位置插入，因为下标为0的只有一个元素，默认是有序的
		for (int i = 1; i < array.length; i++) {
			int tmp1 = array[i];// 记录要插入的数据
			j = i;

			while (j > 0) {// 从已经排序的序列最右边的开始比较，找到比其小的数
				//增加一个标志，如果没有交换，就下次循环
				boolean flag = false;
//				flag=((tmp1 < array[j - 1])?(false):true);
				if (tmp1 < array[j - 1]) {
					flag = true;
					array[j] = array[j - 1];// 向后挪动
				}else {
					//前面已经排序，如果没有交换，可以直接下一次循环
					break;
				}
				j--;
			}
			array[j] = tmp1;// 存在比其小的数，插入
		}
		return array;
	}

	// 遍历显示数组
	public static void toString(int[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		int[] array = { 9, 7, 8, 6, 5, 4, 3, 2, 1};
		// 未排序数组顺序为
		System.out.println("未排序数组顺序为：");
		toString(array);
		System.out.println("-----------------------");
		array = sort(array);
		System.out.println("-----------------------");
		System.out.println("经过插入排序后的数组顺序为：");
		toString(array);
	}

}