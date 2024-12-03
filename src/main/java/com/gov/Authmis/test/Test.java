package com.gov.Authmis.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Test {

	public static void main(String[] args) {
		//find the longest string in a list of strings
		System.out.println("find the longest string in a list of strings");
		List<String> strln = List.of("apple", "banana", "cherry", "grapefruit");
		Optional<String> aa =  strln.stream().max(Comparator.comparingInt(String::length));
		System.out.println(aa.orElse(null));
		
		
		//remove duplicate from string
		System.out.println("remove duplicate from string  : ");
		List<Integer> numbers = List.of(1, 2, 2, 3, 4, 4, 5);
		numbers.stream().distinct().collect(Collectors.toList()).forEach(System.out::println);
		
		
		//factorial of a given number
		System.out.println("factorial of a given number  : ");
		int number = 5;
		System.out.println(LongStream.range(1, number).reduce(1, (a,b)-> a*b));
		
		
		//count the occurrences of a given character in a list of strings
		List<String> strn = List.of("apple", "banana", "cherry");
		char targetChar = 'a';
		System.out.println(strn.stream().flatMapToInt(CharSequence::chars).filter(ch -> ch == targetChar).count());
		
			
		//average length
		List<String> st= List.of("apple", "banana", "orange", "grape", "kiwi");
		System.out.println(st.stream().mapToInt(String :: length).average().orElse(0));
		
		
		//concatenate all string
		List<String> str = List.of("Hello", " ", "World", "!");
		System.out.println(str.stream().collect(Collectors.joining()));
		
		
		//Sum of all even number
		List<Integer> no = List.of(1,2,3,4,5,6,7,8);	
		int sum = no.stream().filter(n -> n%2 == 0).mapToInt(n -> n).sum();
		System.out.println("Sum of all even number : " + sum);
		
		
		String strs[] = { "tat", "tea", "bat", "nat", "tan", "ate" };
		/**
		Map<String, List<String>> groupedAnagrams = Arrays.stream(strs)
				.collect(Collectors.groupingBy(c -> {
					char[] chars = c.toCharArray();
					Arrays.sort(chars);
				return new String(chars);
		}));
		System.out.println(groupedAnagrams.values());
		**/
						
		long count = Arrays.stream(strs).collect(Collectors.groupingBy(c -> {
			char[] ch = c.toCharArray();
			Arrays.sort(ch);
			return new String(ch);
		})).size();
		System.out.println(count);

		
		List<Integer> list = Arrays.asList(1,2,3,4,5,6);
		
		//list.stream().filter(i -> i%2 == 0).collect(Collectors.toList()).forEach(System.out::print);
		
		//int a = list.stream().reduce(0, Integer::sum);
		//System.out.println(a);
		
		//list.stream().max(Integer::compareTo).ifPresent(System.out::println);
			
		//find target no 6
		int targetno =6;
		System.out.println(list.stream().anyMatch(n -> n == targetno)? "Success" : "");

		//even no sum
		list.stream().filter(n -> n%2 ==0).mapToInt(n -> n).sum();
	}
}


