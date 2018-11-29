package cn.itcast.bookstore.category.web.servlet.admin;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.bookstore.category.domain.Category;
import cn.itcast.bookstore.category.service.CategoryService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

@WebServlet("/admin/AdminCategoryServlet")
public class AdminCategoryServlet extends BaseServlet{

	private CategoryService categoryService = new CategoryService();
	
	
	
	
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		request.setAttribute("categoryList", categoryService.findAll());
		return "/adminjsps/admin/category/list.jsp";
	}
	

     public String add (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    	Category c = CommonUtils.toBean(request.getParameterMap(), Category.class);
    	c.setCid(CommonUtils.uuid());
    	
    	categoryService.add(c);
    	
    	return findAll(request,response);
	}

     public String delete (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	 String cid = request.getParameter("cid");
    	 
    	 try{
    	 categoryService.delete(cid);
    	 
    	 return findAll(request,response);
     }catch(CategoryException e){
    	 
    	 request.setAttribute("msg", e.getMessage());
    	 
    	 return "/adminjsps/msg.jsp";
     }
     }
     
     public String editPre(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	 
    	 String cid =request.getParameter("cid");
    	
    	
    	 request.setAttribute("category", categoryService.load(cid));
    	 
    	
    	 
    	 return "f:/adminjsps/admin/category/mod.jsp";
     }
     
     public String edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	 Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
    	 
    	 categoryService.edit(category);
    	 
    	 return findAll(request,response);
    	 
     }
}
