package com.gov.Authmis.test;

public class Test003 {
	private String Name;
	
	public void setName(String name) {
		Name = name;
	}
	public String getName() {
		return Name;
	}
	
	public static void main(String arg[]) {
		Test003 obj = new Test003();
		obj.setName("<---------Test------->");
		
		System.out.println(obj.getName());
	}
	
}
