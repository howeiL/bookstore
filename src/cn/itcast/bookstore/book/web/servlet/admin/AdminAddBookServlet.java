package cn.itcast.bookstore.book.web.servlet.admin;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.itcast.bookstore.book.domain.Book;
import cn.itcast.bookstore.book.service.BookService;
import cn.itcast.bookstore.category.domain.Category;
import cn.itcast.commons.CommonUtils;

/**
 * Servlet implementation class AdminAddBookServlet
 */
@WebServlet("/admin/AdminAddBookServlet")
public class AdminAddBookServlet extends HttpServlet {
	
	private BookService bookService = new BookService();
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
        
        try {
        	
        	  DiskFileItemFactory factory  = new DiskFileItemFactory(15*1024,new File("F:/f/temp"));
              
              ServletFileUpload sfu = new ServletFileUpload(factory);
              sfu.setFileSizeMax(15*1024);
              
			  List<FileItem> fileItemList=sfu.parseRequest(request);
		      Map<String,String> map = new HashMap<String,String >();
		      
		      for(FileItem fileItem : fileItemList){
		    	  
		    	  if(fileItem.isFormField()){
		    		  
		    		  map.put(fileItem.getFieldName(),fileItem.getString("UTF-8"));
		    	  }
		      }
                
		      Book book= CommonUtils.toBean(map, Book.class);
	            
		      book.setBid(CommonUtils.uuid());
	          Category category = CommonUtils.toBean(map, Category.class);
	          
	          book.setCategory(category);
	            
	            String savepath = this.getServletContext().getRealPath("/book_img");
	            System.out.println(savepath);
	            String filename = CommonUtils.uuid()+"_"+ fileItemList.get(1).getName();
	            
	            

	            if(!filename.toLowerCase().endsWith("jpg")){
	            	
	            	request.setAttribute("msg", "你上床的图片不是JPG文件");
	            	request.getRequestDispatcher("/adminjsps/admin/book/add.jsp").forward(request, response);
	            	return;
	            }
	            
	            
	            
	            File destFile = new File(savepath,filename);
        
	            
	            fileItemList.get(1).write(destFile);
            
            
	            book.setImage("book_img/"+filename);
	            
       
	            bookService.add(book);
               
	            Image image = new ImageIcon(destFile.getAbsolutePath()).getImage();
	            if(image.getWidth(null)>200 || image.getHeight(null)>200){
	            	
	            	request.setAttribute("msg", "上传图片尺寸超出200*200");
	            	request.getRequestDispatcher("/adminjsps/admin/book/add.jsp").forward(request, response);
	            	
	            	return ;
	            }
	            
	            
	            
	            
	            request.getRequestDispatcher("/admin/AdminBookServlet?method=findAll").forward(request, response);
        } catch (Exception e) {
			if(e instanceof FileUploadBase.FileSizeLimitExceededException){
				
				request.setAttribute("msg","你上传的文件超过15kb");
				
				request.getRequestDispatcher("/adminjsps/admin/msg.jsp").forward(request, response);
				  
			}
		}
		
	}

}
