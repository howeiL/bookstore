package cn.itcast.bookstore.book.web.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.bookstore.book.service.BookService;
import cn.itcast.servlet.BaseServlet;


@WebServlet("/BookServlet")
public class BookServlet extends BaseServlet {

	private BookService bookService = new BookService();
	
	
	  public String findAll(HttpServletRequest request,HttpServletResponse response){
		  
		  request.setAttribute("bookList", bookService.findAll());
		  
		  return "f:/jsps/book/list.jsp";
	  }
	  
	 
	  
	  public String load(HttpServletRequest request,HttpServletResponse response){
	   
		  String bid = request.getParameter("bid");
	       request.setAttribute("book", bookService.load(bid));
	       return "f:/jsps/book/desc.jsp";
	       
	  
	  }
	  
	  
}
