package cn.itcast.bookstore.category.service;

import java.util.List;


import cn.itcast.bookstore.book.dao.BookDao;
import cn.itcast.bookstore.book.domain.Book;
import cn.itcast.bookstore.category.dao.CategoryDao;
import cn.itcast.bookstore.category.domain.Category;
import cn.itcast.bookstore.category.web.servlet.admin.CategoryException;

public class CategoryService {

	private CategoryDao  categoryDao = new  CategoryDao();
    private BookDao bookDao = new BookDao ();
	
	public List<Category> findAll() {
		
		
		
		
		return categoryDao.findAll();
	}

	public List<Book> findByCategory(String cid) {
		
		return categoryDao.findByCategory(cid);
		
	}

	public void add(Category c) {
		
		categoryDao.add(c);
		
	}

	public void delete(String cid) throws CategoryException {
		
		int count = bookDao.getCountByCid(cid);
		if(count>0)throw new CategoryException("该类下还有图书，不能删除！");
		
		categoryDao.delete(cid);
		
	}


	public Category load(String cid) {
		return categoryDao.load(cid);
	}

	public void edit(Category category) {


		categoryDao.edit(category);
		
	}
}
