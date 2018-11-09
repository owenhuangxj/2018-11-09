package com.ss.service;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class BookServiceTest {
	@Test
	public void testIoC(){
		ApplicationContext context = new ClassPathXmlApplicationContext("container.xml");
		System.out.println(context.getBean("bookDao2"));
	}
}
