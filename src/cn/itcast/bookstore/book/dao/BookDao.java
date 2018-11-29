package cn.itcast.bookstore.book.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.bookstore.book.domain.Book;
import cn.itcast.bookstore.category.domain.Category;
import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

public class BookDao {

	 QueryRunner qr = new TxQueryRunner();
	 
	 
	 public List<Book> findAll(){
		 
		 String sql = "select * from book";
		 try {
			return qr.query(sql, new BeanListHandler<Book>(Book.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException();
		}
	 }


	public Book findByBid(String bid) {
		 String sql = "select * from book b ,category c  where b.cid=c.cid and bid= ?";
		 try {
			Map<String,Object>map = qr.query(sql, new MapHandler(),bid);
			Category category = CommonUtils.toBean(map, Category.class);
			Book book = CommonUtils.toBean(map, Book.class);
			book.setCategory(category);
			
			return book;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException();
		}
	}


	public int getCountByCid(String cid) {


		   String sql = "select count(*) from book where cid=?";
		   
		   try {
			   
		   Number cnt = (Number) qr.query(sql, new ScalarHandler(),cid);
			return cnt.intValue();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return (Integer) null;
		}
	}


	public void add(Book book) {


		
		try {

			String sql = "insert into book values(?,?,?,?,?,?)";
			Object []params ={book.getBid(),book.getBname(),book.getPrice()
					,book.getAuthor(),book.getImage(),book.getCategory().getCid()};
			qr.update(sql,params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


}
