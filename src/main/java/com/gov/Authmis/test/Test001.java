package com.gov.Authmis.test;

import java.util.HashMap;
import java.util.Map;

public class Test001 {
		
	//remove space from string
	public static void main(String arg[]) {
	int num [] = {3,4,7,9,1,2};
	int target = 9;
	
	Map<Integer, Integer> numMap = new HashMap<>();
	
		for(int i=0; i<num.length; i++) {
			int no = target - num[i];
			
			if(numMap.containsKey(no)) {
				System.out.println("Here combintion is : " + numMap.get(no) + "," + i);
			}
			
			numMap.put(num[i], i);
		}     
	}
}
