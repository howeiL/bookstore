package cn.itcast.bookstore.book.web.servlet.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.bookstore.book.service.BookService;
import cn.itcast.bookstore.category.service.CategoryService;
import cn.itcast.servlet.BaseServlet;

/**
 * Servlet implementation class AdminBookServlet
 */
@WebServlet("/admin/AdminBookServlet")
public class AdminBookServlet extends BaseServlet {

	private BookService bookService = new BookService();
	private CategoryService categoryService = new CategoryService();
	
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		request.setAttribute("bookList", bookService.findAll());
		
		return "/adminjsps/admin/book/list.jsp";
	}
	
	public String load(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	request.setAttribute("book", bookService.load(request.getParameter("bid")));
	request.setAttribute("categoryList", categoryService.findAll());	
	
	
	return "/adminjsps/admin/book/desc.jsp";
			
	}
	public String addPre(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		request.setAttribute("categoryList", categoryService.findAll());
		
		return "/adminjsps/admin/book/add.jsp";
	}
}
