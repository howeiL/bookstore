package cn.itcast.bookstore.user.web.servlet;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.bookstore.cart.domain.Cart;
import cn.itcast.bookstore.user.domain.User;
import cn.itcast.bookstore.user.service.UserException;
import cn.itcast.bookstore.user.service.UserService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
import cn.itcast.servlet.BaseServlet;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends BaseServlet {

	private UserService userService  =new UserService();

	
	/*
	 * 激活
	 */ 
	public String active(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
		
	System.out.println("你激活了");
	return null ; 
	}
	
	public String regist(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
		
		
		
		User form = CommonUtils.toBean(request.getParameterMap(), User.class);
		
		form.setUid(CommonUtils.uuid());
		form.setCode(CommonUtils.uuid()+CommonUtils.uuid());
		
		Map<String,String> errors = new HashMap<String,String>();
		
		String username = form.getUsername();
		
		if(username==null ||username.trim().isEmpty()){
			errors.put("username", "用户名不能为空");
		}else if(username.length()<3 ||username.length()>10){
			errors.put("username", "用户名长度必须在3~10之间");
		}
		
		String password = form.getPassword();
		if(password==null ||password.trim().isEmpty()){
			errors.put("password", "密码不能为空");
		}else if(password.length()<3 ||password.length()>10){
			errors.put("password", "密码长度必须在3~10之间");
		}
		
		String email = form.getEmail();
		if(email==null ||email.trim().isEmpty()){
			errors.put("email", "email不能为空");
		}else if(!email.matches("\\w+@\\w+\\.\\w+")){
			errors.put("email", "email格式错误！");
		}

		if(errors.size()>0){
			
			request.setAttribute("errors", errors);
			request.setAttribute("form", form);
			
			return "f:/jsps/user/regist.jsp"; 
		}
		
		try {
			userService.regist(form);
			
			
			/*
			 * 发邮件
			 */
			
			Properties props = new Properties();
			props.load(this.getClass().getClassLoader()
					.getResourceAsStream("email_template.properties"));
			
			String host =props.getProperty("host");
			String uname =props.getProperty("uname");
			String pwd =props.getProperty("pwd");
			String from =props.getProperty("from");
			String to =form.getEmail(); 
			String subject=props.getProperty("subject");
			String content =props.getProperty("content");
			content = MessageFormat.format(content, form.getCode());
			
			 Session session = MailUtils.createSession(host, uname, pwd);//得到session	
			 Mail mail = new Mail(from,to,subject,content);//创建邮件对象
			 
			 try {
				MailUtils.send(session, mail);
				
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//发邮件
		
			request.setAttribute("msg", "恭喜，注册成功！马上到邮箱激活");
			
			return "f://jsps/msg.jsp";
		} catch (UserException e) {
			
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("form", form);
			return "f:/jsps/user/regist.jsp"; 
			
		}
		
		
	}
	
	public String login(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{

		User form = CommonUtils.toBean(request.getParameterMap(), User.class);
		
		
		try {
			User user = userService.login(form);
			request.getSession().setAttribute("session_user", user);
			
			/*
			 * 给用户添加购物车
			 */
			request.getSession().setAttribute("cart", new Cart());
			
			return "r:/index.jsp";
			
		} catch (UserException e) {
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("form", form);
			return "f:/jsps/user/login.jsp";
		}
		
		
		
		
	}
	public String quit(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
	
	   
	     request.getSession().invalidate();
	
	    return "f:/index.jsp";
	
	}
}
