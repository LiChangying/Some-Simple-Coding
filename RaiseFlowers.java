package com.lcy;

/**
 * 
 * @author lichangying
 * @describe	假设有一个很长的花坛，一部分地块种植了花，另一部分却没有。花卉不能种植在相邻的地块上，否则会相互争夺水源，
 * 			两者都会死去。
 * 				给定一个花坛（表示为一个数组包含0和1，其中0表示没种植花，1表示种植了花），和一个数n。能否在不打破种植规则
 * 			的情况下种入n朵花。能则返回True，不能则返回False。 
 */

public class Property {

	public static void main(String[] args) {
		int[] arr = {0,0};        //花圃，1表示有花，0表示没花
		int n = 1;                //表示要种花的种子数量
		if (flowers(arr, n)) {
			System.out.println(true);
		} else {
			System.out.println(false);
		}
	}
	
  /*
  *判断能否完全种下
  */
	public static boolean flowers(int[] arr,int n) {
		if (n == 0) {   //如果种子数量为0，则一定可以种下
			return true;
		} else {        //种子数量不为0
			if (arr.length < 3) {
				switch (arr.length) {   //花圃数量少于3的情况下进行判断
				case 0:                 //花圃数量为0，种子数量不为0，则不能种下
					return false;
				case 1:
					if(n == 1 && arr[0] == 0){  //花圃数量为1，且当前无花，种子数量为1，能种下；否则不能种下
						return true;
					}
					return false;
				case 2:                       //花圃数量为2
					if (arr[0] == 1 || arr[1] == 1 || n > 1) { //花圃中有花，种子数量大于1，则不能种下
						return false;
					} 
					return true;
				default:
					break;
				}
			} else {          //花圃数量大于等于3，判断当前位置前后是否有花，无花便在此处种花，否则，跳过此位置
				for (int i = 1; i < arr.length - 1; i++) {
					if (arr[i] == 1) {
						continue;
					} else {
						if (arr[i - 1] == 0 && arr[i + 1] == 0) {
							n --;
							arr[i] = 1;
						}
					}
				}
			}
		}
		if (n == 0) {
			return true;
		} else {
			return false;
		}
	}

}
