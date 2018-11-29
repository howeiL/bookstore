package cn.itcast.bookstore.category.dao;

import java.sql.SQLException;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.itcast.bookstore.book.domain.Book;
import cn.itcast.bookstore.category.domain.Category;
import cn.itcast.jdbc.TxQueryRunner;

public class CategoryDao {

	 QueryRunner qr = new TxQueryRunner();

	public List<Category> findAll() {

       String sql = "select * from category" ;
       
       try {
		return qr.query(sql, new BeanListHandler<Category>(Category.class));
	} catch (SQLException e) {
		
		throw new RuntimeException(e);
	}
	}

	public List<Book> findByCategory(String cid) {

		 
		 String sql = "select * from book where cid=?";
		 try {
			return qr.query(sql, new BeanListHandler<Book>(Book.class),cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException();
		}
	}

	public void add(Category c) {

            String sql = "insert into category values(?,?)";
		
            try {
				qr.update(sql,c.getCid(),c.getCname());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public void delete(String cid) {
	     
		String sql = "delete from category where cid=?";
			
         try {
				qr.update(sql,cid);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public Category load(String cid) {

		  
				
					
		         try {
		        	 String sql = "select * from category where cid=? ";
						return qr.query(sql, new BeanHandler<Category>(Category.class),cid);
					} catch (SQLException e) {
						
						throw new RuntimeException();
					}
		
	}

	public void edit(Category category) {

		
        try {
       	 String sql = "update category set cname=? where cid=? ";
				qr.update(sql,category.getCname(),category.getCid());
			} catch (SQLException e) {
				
				throw new RuntimeException();
			}

		
	}
}
