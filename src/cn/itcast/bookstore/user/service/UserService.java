package cn.itcast.bookstore.user.service;

import cn.itcast.bookstore.user.dao.UserDao;
import cn.itcast.bookstore.user.domain.User;

public class UserService {

	private UserDao userDao = new UserDao();
	
	public void regist(User form) throws UserException {
		
		
		User user = userDao.findByUsername(form.getUsername());
		if(user!=null)throw new UserException("该用户名已被注册！");
		
		user = userDao.findByEmail(form.getEmail());
		if(user!=null)throw new UserException("Email已被注册！");
		
		userDao.add(form);
		
	}
	
	public User login(User form) throws UserException{
		
		User user = userDao.findByUsername(form.getUsername());
		if(user==null) throw new UserException("用户名不存在");
		
		if(!user.getPassword().equals(form.getPassword()))throw new UserException("密码错误");
		
		if(!user.isState())throw new UserException("尚未激活");
		
		
		return user ;
		
		
		
		
		
	}
}
