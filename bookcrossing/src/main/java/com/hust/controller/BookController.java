package com.hust.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.bcos.channel.client.Service;
import org.bcos.web3j.abi.datatypes.Address;
import org.bcos.web3j.abi.datatypes.Bool;
import org.bcos.web3j.abi.datatypes.Type;
import org.bcos.web3j.abi.datatypes.Utf8String;
import org.bcos.web3j.abi.datatypes.generated.Uint256;
import org.bcos.web3j.crypto.Credentials;
import org.bcos.web3j.crypto.ECKeyPair;
import org.bcos.web3j.crypto.Keys;
import org.bcos.web3j.protocol.Web3j;
import org.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.bcos.web3j.protocol.core.methods.response.EthBlockNumber;
import org.bcos.web3j.protocol.core.methods.response.TransactionReceipt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hust.contract.Book5;
import com.hust.contract.BookClient;
import com.hust.contract.BookFlow;
import com.hust.pojo.Book;
import com.hust.pojo.BorrowRecord;
import com.hust.pojo.Student;
import com.hust.pojo.Want_book;
import com.hust.service.BookService;
import com.hust.service.StudentService;
import com.hust.util.BookDetail;
import com.hust.util.BookInfo;
import com.hust.util.BookResultForSearch;
import com.hust.util.BorrowedBooksJson;
import com.hust.util.HomelistJson;
import com.hust.util.MyBorrowedBooks;
import com.hust.util.Page;
import com.hust.util.SearchResultJson;


@Controller
@RequestMapping(value = "/")
public class BookController {
	
	//图片访问根路径，部署项目时需要修改
	static String imageUrl="http://132.232.199.162:8080/staticimage/";
	static String school="计算机学院";
	static double secondsInADay=60*60*24; //秒*分*小时
	
	static Logger logger;
	public static Web3j web3j;
	// 初始化交易参数
	public static java.math.BigInteger gasPrice = new BigInteger("1");
	public static java.math.BigInteger gasLimit = new BigInteger("30000000");
	public static java.math.BigInteger initialWeiValue = new BigInteger("0");
	public static boolean is_init=false;
	public static ECKeyPair keyPair;
	public static Credentials credentials;
    public static String contractAddress = "0x7ea3487d9082671e8d9c297986d82200b138174a";
    public static Book5 bookContract;
    //区块链服务
    @Autowired
    public Service blockchainService;
	
	@Autowired
	private BookService bookService;
	@Autowired
	private StudentService studentService;
	
	
	/**
	 * 区块链服务信息初始化
	 * @throws Exception
	 */
	@PostConstruct
	public void init() throws Exception {
		if(!is_init) {
			logger = Logger.getLogger(BookClient.class);
			blockchainService.run(); // run the daemon service
			// init the client keys
			keyPair = Keys.createEcKeyPair();
			credentials = Credentials.create(keyPair);
			ChannelEthereumService channelEthereumService = new ChannelEthereumService();
			channelEthereumService.setChannelService(blockchainService);

			// init webj client base on channelEthereumService
			web3j = Web3j.build(channelEthereumService);
			bookContract = Book5.load(contractAddress, web3j, credentials, gasPrice, gasLimit);
			EthBlockNumber ethBlockNumber = web3j.ethBlockNumber().sendAsync().get();
		    int startBlockNumber  =ethBlockNumber.getBlockNumber().intValue();
//		    logger.info("====================================================================================");
			logger.info("-->Got ethBlockNumber:{"+startBlockNumber+"}");
			System.out.println("=========>初始化成功");
			is_init=true;
		}
	}


	
	/**
	 * 获取图书列表（Web前端请求）
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
//        List<School> schools= schoolService.list();
//        for(int i=0;i<schools.size();i++) {
//        	System.out.println("---"+schools.get(i).getSchoName());
//        }
        int total = bookService.getTotal();
        page.setTotal(total);

        request.setAttribute("books", books);
//        request.setAttribute("schools", schools);
        request.setAttribute("page", page);

        return "booklist";
	}
	
	
	/**
	 * 添加新图书（Web前端请求）
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
        
        
        //TODO 区块链操作:1.新增会员-2.添加书籍属主
        String stuId = request.getParameter("studentid");
        String bookId = request.getParameter("bookid");
        String bookName = request.getParameter("bookname");
        String email = request.getParameter("email");
        //以下字段后台需要提供
        String bookCategory=request.getParameter("category");

        
        Utf8String _stuId= new Utf8String(stuId);
        Utf8String _bookId= new Utf8String(bookId);
        Utf8String _emailAddr= new Utf8String(email);
        Utf8String _bookName= new Utf8String(bookName);
        Utf8String _schoolName=new Utf8String(school);
        Utf8String _bookCategory = new Utf8String(bookCategory);
        
        
        //在区块链上添加书籍
        bookContract.registerStudent(_stuId, _bookId, _emailAddr, _bookName, _schoolName,_bookCategory);
        
		return "redirect:/booklist";
	}
	
	
	/**
	 * 编辑页面跳转（Web前端请求）
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
	 * 更新图书信息（Web前端请求）
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
	 * 删除书籍（Web前端请求）
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
	 * 以Json格式给安卓端返回主页信息
	 * @return
	 */
	@RequestMapping(value="list")
	@ResponseBody
	public HomelistJson homelist(HttpServletRequest request) {
		HomelistJson homelistJson=new HomelistJson();
		// 获取分页参数
        int start = 0;
        int count = 5;

        try {
            start = Integer.parseInt(request.getParameter("start"));
            count = Integer.parseInt(request.getParameter("count"));
        } catch (Exception e) {
        }
		
		//List<Book> books = bookService.homeList();
        List<Book> books = bookService.list(start, count);
		
		int total = books.size();
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
	 * 查看书籍详情(安卓端)
	 * @param request
	 * @return
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	@RequestMapping(value="book")
	@ResponseBody
	public BookDetail bookDetail(HttpServletRequest request) throws InterruptedException, ExecutionException {
		String bookId=request.getParameter("book_id");
		Book book = bookService.getBookById(bookId);
		String available="0";
		String location="";
		String stuName="";
		String email="";
		
		BookDetail bookDetail = new BookDetail();
		bookDetail.setBook_id(bookId);
		bookDetail.setBook(book);
		
		//TODO 根据书籍id,从区块链中获取位置信息
		Utf8String _bookId = new Utf8String(bookId);
		Future<List<Type>> checkBookStatus = bookContract.checkBookStatus(_bookId);
		List<Type> statusResult = checkBookStatus.get();
		String stuId=((Utf8String)statusResult.get(0)).getValue();
		Boolean avail=((Bool)statusResult.get(1)).getValue();
		
		if(!(stuId.equals(""))) {
			//学生id不为空，则获取其信息
			Future<List<Type>> studentInfo = bookContract.getStudent(new Utf8String(stuId));
			List<Type> stuResult = studentInfo.get();
			email=((Utf8String)stuResult.get(2)).getValue();
			stuName=studentService.getStudentById(stuId).getStuName();
		}
		
		
		if(avail) {
			//表示当前书籍可借阅
			available="1";
			if(stuId.equals("")) {
				//书籍在学院
				String schoolName = ((Utf8String)statusResult.get(2)).getValue();
				location=schoolName;
			}else {
				//书籍在学生手上
				location=stuName+" "+email;
			}
		}else {
			//当前书籍不可借,在学生手里
			location=stuName+" "+email;
		}
		
		bookDetail.setAvailable(available);
		bookDetail.setLocation(location);
		
		
		return bookDetail;
	}
	
	
	/**
	 * 搜索结果（返回给安卓端）
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="search")
	@ResponseBody
	public SearchResultJson search(HttpServletRequest request) throws UnsupportedEncodingException {
		//解决中文乱码
		String book_name = new String(request.getParameter("book_name").getBytes("ISO-8859-1"),"UTF-8");
		// 获取分页参数
        int start = 0;
        int count = 5;

        try {
            start = Integer.parseInt(request.getParameter("start"));
            count = Integer.parseInt(request.getParameter("count"));
        } catch (Exception e) {
        	
        }

        
		List<Book> searchResult = bookService.searchBookByName(book_name,start,count);
		//格式转换
		List<BookResultForSearch> books=new ArrayList<BookResultForSearch>();
		for(Book b:searchResult) {
			BookResultForSearch temp = new BookResultForSearch();
			temp.setBookResult(b);
			books.add(temp);
		}
		SearchResultJson result=new SearchResultJson();
		result.setBooks(books);
		result.setCount(searchResult.size());
		
		return result;
	}
	
	
	/**
	 * 已借书籍
	 * @param request
	 * @return
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	@RequestMapping(value="borrowed")
	@ResponseBody
	public BorrowedBooksJson myBorrowedBooks(HttpServletRequest request) throws InterruptedException, ExecutionException {
		String stuId = request.getParameter("stu_id");
		//获得借阅图书的id集合
		List<String> booksID = bookService.getBorrowedBooksId(stuId);
		List<MyBorrowedBooks> books=new ArrayList<MyBorrowedBooks>();
		Book tempBook;
		
		for(String b_id: booksID) {
			tempBook=bookService.getBookById(b_id);
			MyBorrowedBooks tempResult = new MyBorrowedBooks();
			tempResult.setBookResult(tempBook);
						
			//从链上获取书籍状态
			Utf8String _bookId = new Utf8String(b_id);
			Future<List<Type>> checkOverdue = bookContract.checkOverdue(_bookId);
			List<Type> checkresult = checkOverdue.get();
			Boolean isOutOfDays = ((Bool)checkresult.get(0)).getValue();
			
			
			//还在借阅时间内,则计算出剩余天数
			if(!isOutOfDays) {
				BigInteger leftTimeInSeconds = ((Uint256)checkresult.get(1)).getValue();
				//取上界的整数
				int leftDays=(int)Math.ceil(leftTimeInSeconds.doubleValue()/secondsInADay);
				tempResult.setLeftDays(leftDays);
			}
			
			
			books.add(tempResult);
		}
		
		BorrowedBooksJson result=new BorrowedBooksJson();
		result.setBooks(books);
		result.setCount(booksID.size());
		return result;
	}
	
	
	
	/**
	 * 查看书籍是否存在
	 * @return
	 */
	@RequestMapping(value="book_exist")
	@ResponseBody
	public Boolean check_book_exist(HttpServletRequest request) {
		Boolean exist=false;
		String book_id = request.getParameter("book_id");
		Book book = bookService.getBookById(book_id);
		if(book!=null) {
			exist=true;
		}
		
		return exist;
	}
	
	/**
	 * 借书功能
	 * @param request
	 * @return
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	@RequestMapping(value="borrow")
	@ResponseBody
	public Boolean borrowBook(HttpServletRequest request) throws InterruptedException, ExecutionException {
		Boolean status=false;
		
		//从安卓端网络请求中获取基本的请求数据信息
		Utf8String bookId = new Utf8String(request.getParameter("book_id"));		
		Utf8String stuId = new Utf8String(request.getParameter("stu_id"));
		
		//TODO 根据书籍id,查询书籍状态
		Future<List<Type>> checkBookStatus = bookContract.checkBookStatus(bookId);
		List<Type> statusResult = checkBookStatus.get();
		Boolean _avail = ((Bool)statusResult.get(1)).getValue();

		//在书籍可借阅的情况下进行借阅，同时返回借阅成功信息
		if(_avail) {
			//表示当前书籍可借阅
			bookContract.borrowBook(bookId, stuId);
			//添加借阅记录
			BorrowRecord b_record=new BorrowRecord();
			b_record.setBookId(bookId.getValue());
			b_record.setStuId(stuId.getValue());
			bookService.addBorrowedRecord(b_record);;
			status = true;
		}
		
		return status;
	}
	
	
	/**
	 * 还书功能
	 * @param request
	 * @return
	 */
	@RequestMapping(value="return")
	@ResponseBody
	public Boolean returnBook(HttpServletRequest request) {		
		//从安卓端网络请求中获取基本的请求数据信息
		Utf8String bookId = new Utf8String(request.getParameter("book_id"));		
		
		bookContract.returnBook(bookId);
		//删除还书记录
		bookService.deleteBorrowedRecord(bookId.getValue());
		Boolean status=true;
		
		return status;
		
	}
	
	
	
	/**
	 * 点击想看书籍
	 * @param request
	 * @return
	 */
	@RequestMapping(value="like")
	@ResponseBody
	public Boolean likeBook(HttpServletRequest request) {
		Boolean status=true;
		//获取请求数据
		String book_id = request.getParameter("book_id");
		String stu_id = request.getParameter("stu_id");
		
		Utf8String _bookId = new Utf8String(book_id);		
		Utf8String _stuId = new Utf8String(stu_id);
		
		//检查该用户是否点击过想看
		Want_book result = bookService.getWant_bookBySIdABId(book_id, stu_id);
		if(result==null) {
			String wantId = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
			Want_book record=new Want_book();
			record.setWantId(wantId);
			record.setBookId(book_id);
			record.setStuId(stu_id);
			bookService.insert(record);
			//区块链操作
			bookContract.wantBook(_bookId, _stuId);
		}else {
			//已经点击过想看
			status=false;
		}
		
		
		return status;
	}
	
	
	/**
	 * 根据标签返回书籍列表,返回格式与搜索相同
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	@RequestMapping(value="tagBooks")
	@ResponseBody
	public SearchResultJson tagBookList(HttpServletRequest request) throws UnsupportedEncodingException, InterruptedException, ExecutionException {
		//解决中文乱码
		String tag_name = new String(request.getParameter("tag_name").getBytes("ISO-8859-1"),"UTF-8");
		
		// 获取分页参数
        int start = 0;
        int count = 5;

        try {
            start = Integer.parseInt(request.getParameter("start"));
            count = Integer.parseInt(request.getParameter("count"));
        } catch (Exception e) {
        	
        }
		
		//根据标签从区块链上获取书籍id列表
		Utf8String _bookCategory = new Utf8String(tag_name);
		Future<Utf8String> booksOfCategory = bookContract.getBooksOfCategory(_bookCategory);
		String bookList = ((Utf8String)booksOfCategory.get()).getValue();
		String book_id_list =bookList.replace(" ", "|");
		List<Book> booksInTag = bookService.getBooksByTag(book_id_list, start, count);
		
		//格式转换
		List<BookResultForSearch> books=new ArrayList<BookResultForSearch>();
		for(Book b:booksInTag) {
			BookResultForSearch temp = new BookResultForSearch();
			temp.setBookResult(b);
			books.add(temp);
		}
		SearchResultJson result=new SearchResultJson();
		result.setBooks(books);
		result.setCount(booksInTag.size());
		
		return result;
	}
}