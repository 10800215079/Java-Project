package com.gov.Authmis.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Employee implements Comparable<Employee> {

	 private int id;
	    private String name;
	    private double salary;

	    public Employee(int id, String name, double salary) {
	        this.id = id;
	        this.name = name;
	        this.salary = salary;
	    }

	    public int getId() {
	        return id;
	    }

	    public void setId(int id) {
	        this.id = id;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public double getSalary() {
	        return salary;
	    }

	    public void setSalary(double salary) {
	        this.salary = salary;
	    }

	    @Override
	    public String toString() {
	        return "Employee [id=" + id + ", name=" + name + ", salary=" + salary + "]";
	    }

	    @Override
	    public int compareTo(Employee o) {
	        return Double.compare(this.salary, o.salary); // Compare by id
	    }

	    public static void main(String[] args) {
	        
	        List<Employee> employees = new ArrayList<>();
	        
	        employees.add(new Employee(3, "Alica", 7000));
	        employees.add(new Employee(1, "Jon", 12000));
	        employees.add(new Employee(13, "Bob", 1000));
	        employees.add(new Employee(34, "Charlie", 27000));
	        employees.add(new Employee(30, "Abhi", 55000));
	        
	        Collections.sort(employees);
	        
	        employees.forEach(System.out::println);
	    }
}
