package com.gov.Authmis.test;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;



public class RemoveDuplicateFromArrayList {

		public static void main(String arg[]) {
		 String str= "spriha sharma";
		 Map<Character, Long> str1 = str.chars().mapToObj(c -> (char)c).filter(c -> c != ' ')
				 .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
		 str1.forEach((key,value) -> System.out.println(key + " : " + value));
		
			
		}
}
 