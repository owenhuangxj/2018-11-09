package com.ss.simulation;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.ss.dao.BookDaoImpl;

public class ClassPathXMLApplicationContext implements ApplicationContext {
	private Map<String,Object> container = new HashMap<>();
	
	public Map<String, Object> getContainer() {
		return container;
	}
	public void setContainer(Map<String, Object> container) {
		this.container = container;
	}
	@Override
	public Object getBean(String id) {
		return container.get(id);
	}
	//1.使用jdom读取xml文件
	//2.获取xml标签
	//3.使用反射创建对象，并且初始化，然后存入容器中去
	public ClassPathXMLApplicationContext(String path){
		SAXBuilder builder = new SAXBuilder();
		Document document = null;
		try {
			document = builder.build(ClassPathXMLApplicationContext.class.getClassLoader().getResourceAsStream(path));
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Element root = document.getRootElement();
		List<Element> children = root.getChildren("bean");
		for(Element child : children){
			String key = child.getAttributeValue("id");
			String classStr = child.getAttributeValue("class");
			
			try {
				Object value = Class.forName(classStr).newInstance();
				container.put(key, value);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		try{
			for(Element child : children){
				Object obj = container.get(child.getAttributeValue("id"));
				List<Element> properties = child.getChildren("property");
				//处理property标签，对应对象时被注入的对象
				for(Element property : properties){
					String ref = property.getAttributeValue("ref");
					String setStr = "";
					Object value = null;
					Method setter = null;
					if(ref != null){
						setStr = "set".concat(ref.substring(0,1).toUpperCase()).concat(ref.substring(1));
						value = container.get(ref);
						setter = obj.getClass().getMethod(setStr, value.getClass().getInterfaces()[0]);
					}else{
						String name = property.getAttributeValue("name");
						setStr = "set".concat(name.substring(0,1).toUpperCase()).concat(name.substring(1));
						Field field = obj.getClass().getDeclaredField(name);
						
						String stringValue = property.getAttributeValue("value");
						setter = obj.getClass().getDeclaredMethod(setStr, field.getType());
						System.out.println("type : " + field.getType().getName());
						if(field.getType().getName().endsWith("Integer")) {
							Integer v = Integer.parseInt(stringValue);
							setter.invoke(obj, v);
							
						}else if(field.getGenericType().getTypeName().endsWith("String")) {
							setter.invoke(obj, stringValue);
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}