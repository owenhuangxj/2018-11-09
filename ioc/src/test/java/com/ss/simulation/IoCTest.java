package com.ss.simulation;

import org.junit.Test;

public class IoCTest {
	@Test
	public void testIoC() throws NoSuchFieldException, SecurityException {
		ClassPathXMLApplicationContext context =  new ClassPathXMLApplicationContext("beans.xml");
		System.out.println(context.getBean("bookDao"));
	}
}
