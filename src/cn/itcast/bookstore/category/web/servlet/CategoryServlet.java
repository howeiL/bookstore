package cn.itcast.bookstore.category.web.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.bookstore.category.service.CategoryService;
import cn.itcast.servlet.BaseServlet;


@WebServlet("/CategoryServlet")
public class CategoryServlet extends BaseServlet{

	 private  CategoryService categoryService = new CategoryService();
	 
	 
	 
	 public String findAll(HttpServletRequest request, HttpServletResponse response){
		 
		 request.setAttribute("categoryList", categoryService.findAll());
		 
	
		 
		 return "f:/jsps/left.jsp";
	 }
	 
	 
	 
	 public String findByCategory(HttpServletRequest request,HttpServletResponse response){
		  
		    String cid=request.getParameter("cid");
		    
		    request.setAttribute("bookList", categoryService.findByCategory(cid));
		    return "f:/jsps/book/list.jsp";
		   
		  
		  
		  }
}
