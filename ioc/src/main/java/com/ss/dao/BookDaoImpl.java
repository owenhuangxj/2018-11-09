package com.ss.dao;

public class BookDaoImpl implements BookDao {
	private String name;
	private Integer age;
	
	public BookDaoImpl() {
		System.out.println("////////////////////////////////////////////////////////////////////////////////////");
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "BookDaoImpl [name=" + name + ", age=" + age + "]";
	}
	
}
