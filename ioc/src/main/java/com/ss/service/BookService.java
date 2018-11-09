package com.ss.service;

import com.ss.dao.BookDao;

public class BookService {
	private BookDao bookDao;

	
	public BookDao getBookDao() {
		return bookDao;
	}


	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	public BookService(){
	}

	public BookService(BookDao bookDao) {
		super();
		this.bookDao = bookDao;
	}
	

}
