package com.gov.Authmis.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public class Test002 {
	
	public static void main(String[] args) {
				
        int arr[] = {23, 45, 65, 11, 2, 99, 40, 0, 5};
        
        Arrays.sort(arr);        
        System.out.println("second loest element : " + arr[2]);
        System.out.println("second highest element : " + arr[arr.length-2]);
        
        String str ="Spriha Sharma";
        Map<Character, Long> hashmap = str.chars().mapToObj(c -> (char)c).filter(c -> c != ' ')
        							.collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));     
       System.out.println(hashmap);
        
        //print distinct character
       str.chars().mapToObj(c -> (char)c).filter(c -> c!= ' ').distinct().forEach(System.out::println);
       
       //total count of distinct character
       long letscount = str.chars().mapToObj(c -> (char)c).distinct().count();
       System.out.println(letscount);
       
       //find and print duplicate number
       List<Integer> list = Arrays.asList(23, 45, 65, 11, 2, 99, 40, 23, 45);
       List<Integer> duplicate = list.stream().collect(Collectors.groupingBy(n->n , Collectors.counting()))
    		   .entrySet().stream().filter(c -> c.getValue()>1).map(Map.Entry :: getKey).collect(Collectors.toList());		   
    		   
	}

}
