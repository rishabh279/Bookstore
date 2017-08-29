package com.adminportal.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.adminportal.domain.Book;
import com.adminportal.service.BookService;

@Controller
@RequestMapping("/book")
public class BookController {
	@Autowired
	BookService bookService;
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String addBook(Model model){
		Book book = new Book();
		model.addAttribute("book",book);
		return "addBook";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String addBookPost(
			@ModelAttribute("book")Book book,HttpServletRequest request
			){
		//the book that we retrieve from the post url contains every info about the book(that we define in the Book Entity
		// class) except the id so first we save the the book in the database so that we get the book id and 
		//and then save the image with the id name
		//here the first the image is converted into bytes then dtored in the database
		bookService.save(book);
		//https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/multipart/MultipartFile.html
		MultipartFile bookImage = book.getBookImage();
		try {
			byte[] bytes = bookImage.getBytes();
			String name = book.getId() + ".png";
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(new File("src/main/resources/static/image/book/" + name)));
			stream.write(bytes);
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:bookList";
	}
	
	@RequestMapping("/bookList")
	public String bookList(Model model){	
		List<Book> bookList = bookService.findALL();
		model.addAttribute(bookList);
		return "bookList";
		
	}
}
