package com.hust.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hust.pojo.Book;
import com.hust.pojo.BookOwner;
import com.hust.pojo.Member;
import com.hust.pojo.School;
import com.hust.service.BookOwnerService;
import com.hust.service.BookService;
import com.hust.service.MemberService;
import com.hust.service.SchoolService;
import com.hust.util.BookDetail;
import com.hust.util.BookInfo;
import com.hust.util.HomelistJson;
import com.hust.util.Page;
import com.hust.contract.*;

@Controller
@RequestMapping(value = "/")
public class BookController {
	
	//图片访问根路径，部署项目时需要修改
	static String imageUrl="http://202.114.6.59:8080/staticimage/";
	
	@Autowired
	private BookService bookService;
	@Autowired
	private SchoolService schoolService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private BookOwnerService bookOwnerService;
	
//	/**
//	 * 测试框架跳转处理
//	 * @param request
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping(value = "test")
//    public String Index(HttpServletRequest request, Model model){
//        int bookId = Integer.parseInt(request.getParameter("id"));
//        Book book = bookService.getBookById(bookId);
//        model.addAttribute("book",book);
//         return "book";
//    }
	
	/**
	 *web 端代码
	 * 获取图书列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="booklist")
	public String bookList(HttpServletRequest request) {
		// 获取分页参数
        int start = 0;
        int count = 10;

        try {
            start = Integer.parseInt(request.getParameter("page.start"));
            count = Integer.parseInt(request.getParameter("page.count"));
        } catch (Exception e) {
        }

        Page page = new Page(start, count);

        List<Book> books = bookService.list(page.getStart(), page.getCount());
        List<School> schools= schoolService.list();
//        for(int i=0;i<schools.size();i++) {
//        	System.out.println("---"+schools.get(i).getSchoName());
//        }
        int total = bookService.getTotal();
        page.setTotal(total);

        request.setAttribute("books", books);
        request.setAttribute("schools", schools);
        request.setAttribute("page", page);

        return "booklist";
	}
	
	
	/**
	 * web 端代码
	 * 添加新图书
	 * @param request
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping(value="addBook")
	public String addBook(HttpServletRequest request, @RequestParam(value="picture",required=false) MultipartFile imageFile) throws IllegalStateException, IOException {
		String fileName = null;
        if(!imageFile.isEmpty()){
            //获取项目跟路径
            String filePath = request.getServletContext().getRealPath("/");
            //获取项目名
            String projectName = request.getContextPath();
            //将项目跟路劲下的项目名称置为空，因为图片需要在项目外的webapp下面存放,sub截取下标为1的字符
            filePath=filePath.replace(projectName.substring(1),"");
            //System.out.println(filePath);
            //重新生成文件名字
            fileName = UUID.randomUUID()+"."+imageFile.getOriginalFilename().split("\\.")[1];
            //将文件保存到指定目录
            imageFile.transferTo(new File(filePath+"staticimage/"+fileName));
        }
        //System.out.println(fileName);
        
        
        //新增图书
        Book book=new Book();
        book.setBookId(request.getParameter("bookid"));
        book.setAuthor(request.getParameter("author"));
        book.setBookName(request.getParameter("bookname"));
        book.setPress(request.getParameter("press"));
        book.setPicture(imageUrl+fileName);
        book.setBookDescription(request.getParameter("description"));
        System.out.println(book.toString());
        bookService.addBook(book);
        
        
        //TODO 区块链操作:1.如果不是会员需要新增-2.添加书籍属主
        //新增会员
//        Member exist = memberService.getMemberByStuId(request.getParameter("studentid"));
//        if(exist==null) {
//            Member member=new Member();
//            member.setBookId(request.getParameter("bookid"));
//            member.setEmailAddr(request.getParameter("email"));
//            member.setSchoId(request.getParameter("schoid"));
//            member.setStuId(request.getParameter("studentid"));
//            memberService.addMember(member);
//            //System.out.println(member.toString());
//        }
//
//        //添加书籍属主
//        BookOwner bo=new BookOwner();
//        bo.setBookId(request.getParameter("bookid"));
//        bo.setStuId(request.getParameter("studentid"));
//        bookOwnerService.addBookOwner(bo);
        
        
		return "redirect:/booklist";
	}
	
	
	/**
	 * web 端代码
	 * 编辑页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(value="editBook")
	public String editBook(HttpServletRequest request) {
		String bookId=request.getParameter("id");
		
		Book book = bookService.getBookById(bookId);
		request.setAttribute("book", book);
		
		return "editbook";
	}
	
	/**
	 * web 端代码
	 * 更新图书信息
	 * @param request
	 * @param imageFile
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping(value="updateBook")
	public String updateBook(HttpServletRequest request, @RequestParam(value="picture",required=false) MultipartFile imageFile) throws IllegalStateException, IOException {
		String fileName = null;
        if(!imageFile.isEmpty()){
            //获取项目跟路径
            String filePath = request.getServletContext().getRealPath("/");
            //获取项目名
            String projectName = request.getContextPath();
            //将项目跟路劲下的项目名称置为空，因为图片需要在项目外的webapp下面存放,sub截取下标为1的字符
            filePath=filePath.replace(projectName.substring(1),"");
            //System.out.println(filePath);
            //重新生成文件名字
            fileName = UUID.randomUUID()+"."+imageFile.getOriginalFilename().split("\\.")[1];
            //将文件保存到指定目录
            imageFile.transferTo(new File(filePath+"staticimage/"+fileName));
        }
        
      //更新图书
        Book book=new Book();

        if(fileName!=null) {
        	book.setPicture(imageUrl+fileName);
        }
        book.setBookId(request.getParameter("bookid"));
        book.setAuthor(request.getParameter("author"));
        book.setBookName(request.getParameter("bookName"));
        book.setPress(request.getParameter("press"));
        book.setBookDescription(request.getParameter("description"));
        
        bookService.updateBook(book);
        
        return "redirect:/booklist";
	}
	
	
	/**
	 * web 端代码
	 * 删除书籍
	 * @param request
	 * @return
	 */
	@RequestMapping(value="deleteBook")
	public String deleteBook(HttpServletRequest request) {
		String bookId = request.getParameter("id");
		bookService.deleteBookById(bookId);
		
		return "redirect:/booklist";
	}
	
	
	/**
	 * 安卓端代码
	 * 以Json格式给安卓端返回主页信息
	 * @return
	 */
	@RequestMapping(value="list")
	@ResponseBody
	public HomelistJson homelist() {
		HomelistJson homelistJson=new HomelistJson();
		
		List<Book> books = bookService.homeList();
		
		int total = bookService.getTotal();
		homelistJson.setCount(total);
		
		List<BookInfo> temp=new ArrayList<BookInfo>();
				
		for(Book b: books) {
			BookInfo bookInfo=new BookInfo();
			bookInfo.setBook_id(b.getBookId());
			bookInfo.setBook_name(b.getBookName());
			bookInfo.setPicture(b.getPicture());;
			temp.add(bookInfo);
		}
		homelistJson.setBooks(temp);
		
		return homelistJson;
	}
	
	
	
	/**
	 * 安卓端代码
	 * 查看书籍详情(安卓端)
	 * @param request
	 * @return
	 */
	@RequestMapping(value="book")
	@ResponseBody
	public BookDetail bookDetail(HttpServletRequest request) {
		String bookId=request.getParameter("book_id");
		Book book = bookService.getBookById(bookId);
		
		BookDetail bookDetail = new BookDetail();
		bookDetail.setBook_id(bookId);
		bookDetail.setBook(book);
		
		//TODO 根据书籍id,从区块链中获取位置信息
		bookDetail.setAvailable("1");
		bookDetail.setLocation("where");
		
		return bookDetail;
	}
	
	
	/**
	 * 安卓端代码
	 * 借书功能
	 * @param request
	 * @return
	 */
	@RequestMapping(value="borrow")
	@ResponseBody
	public String borrowBook(HttpServletRequest request) {
		String status="false";
		String bookId = request.getParameter("book_id");
		String stuId = request.getParameter("stu_id");
		
		return status;
	}
	
	
	/**
	 * 安卓端代码
	 * 还书功能
	 * @param request
	 * @return
	 */
	@RequestMapping(value="return")
	@ResponseBody
	public String returnBook(HttpServletRequest request) {
		String status="false";
		
		return status;
		
	}

}

