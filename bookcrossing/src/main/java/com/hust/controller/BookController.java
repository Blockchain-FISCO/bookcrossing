package com.hust.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hust.pojo.Book;

import com.hust.service.impl.BookServiceImpl;

@Controller
@RequestMapping(value = "/book")
public class BookController {
	
	@Autowired
	private BookServiceImpl bookServiceImpl;
	
	@RequestMapping(value = "/test")
    public String Index(HttpServletRequest request, Model model){
        int bookId = Integer.parseInt(request.getParameter("id"));
        Book book = bookServiceImpl.getBookById(bookId);
        model.addAttribute("book",book);
         return "book";
    }

}
