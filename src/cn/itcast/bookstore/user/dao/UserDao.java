package cn.itcast.bookstore.user.dao;

import java.sql.SQLException;

import javax.management.RuntimeErrorException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.itcast.bookstore.user.domain.User;
import cn.itcast.jdbc.TxQueryRunner;

public class UserDao {

	private QueryRunner qr = new TxQueryRunner();
	
	
	public User findByUsername(String username) {
		
		String sql =" select * from tb_user where username=?";
		try {
			return qr.query(sql, new BeanHandler<User>(User.class),username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		
	}
	
	
	public User findByEmail(String email){
		
		
		try {
			String sql  = "select * from tb_user where email=?";
			return qr.query(sql, new BeanHandler<User>(User.class),email);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		
	}
	

	public void add(User user){
		
		
		try {
			String sql  = "insert into tb_user values(?,?,?,?,?,?)";
			Object [] params ={user.getUid(),user.getUsername(),user.getPassword(),
					user.getEmail(),user.getCode(), user.isState()};
			qr.update(sql,params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		
	}
	
}
