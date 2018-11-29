package cn.itcast.bookstore.book.service;

import java.util.List;

import cn.itcast.bookstore.book.dao.BookDao;
import cn.itcast.bookstore.book.domain.Book;

public class BookService {

	 private BookDao bookDao = new  BookDao();
	 
	 
	 public List<Book> findAll(){
		 
		 return bookDao.findAll();
	 }


	


	public Book load(String bid) {
		// TODO Auto-generated method stub
		return bookDao.findByBid(bid);
	}





	public void add(Book book) {
		
		bookDao.add(book);
		
	}
}
