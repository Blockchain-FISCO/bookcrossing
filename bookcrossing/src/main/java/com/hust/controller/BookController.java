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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hust.contract.BookClient;
import com.hust.contract.BookContract;
import com.hust.pojo.Book;
import com.hust.pojo.School;
import com.hust.pojo.Student;
import com.hust.service.BookService;
import com.hust.service.SchoolService;
import com.hust.service.StudentService;
import com.hust.util.BookDetail;
import com.hust.util.BookInfo;
import com.hust.util.HomelistJson;
import com.hust.util.Page;
import com.hust.util.SearchResultJson;


@Controller
@RequestMapping(value = "/")
public class BookController {
	
	//图片访问根路径，部署项目时需要修改
	static String imageUrl="http://202.114.6.59:8080/staticimage/";
	
	static Logger logger;
	public static Web3j web3j;
	// 初始化交易参数
	public static java.math.BigInteger gasPrice = new BigInteger("1");
	public static java.math.BigInteger gasLimit = new BigInteger("30000000");
	public static java.math.BigInteger initialWeiValue = new BigInteger("0");
	public static ECKeyPair keyPair;
	public static Credentials credentials;
    public static String contractAddress = "0x0de201480dd54011f0a7dad5a7b840d614b7993f";
    public static BookContract bookContract;
    //区块链服务
    @Autowired
    public Service blockchainService;
	
	@Autowired
	private BookService bookService;
	@Autowired
	private SchoolService schoolService;
	@Autowired
	private StudentService studentService;
	
	
	/**
	 * 区块链服务信息初始化
	 * @throws Exception
	 */
	@PostConstruct
	public void init() throws Exception {
		logger = Logger.getLogger(BookClient.class);
		blockchainService.run(); // run the daemon service
		// init the client keys
		keyPair = Keys.createEcKeyPair();
		credentials = Credentials.create(keyPair);
		ChannelEthereumService channelEthereumService = new ChannelEthereumService();
		channelEthereumService.setChannelService(blockchainService);

		// init webj client base on channelEthereumService
		web3j = Web3j.build(channelEthereumService);
		bookContract = BookContract.load(contractAddress, web3j, credentials, gasPrice, gasLimit);
		EthBlockNumber ethBlockNumber = web3j.ethBlockNumber().sendAsync().get();
	    int startBlockNumber  =ethBlockNumber.getBlockNumber().intValue();
//	    logger.info("====================================================================================");
		logger.info("-->Got ethBlockNumber:{"+startBlockNumber+"}");
		System.out.println("=========>初始化成功");
	}


	
	/**
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
        
        
        //TODO 区块链操作:1.新增会员-2.添加书籍属主
        String stuId = request.getParameter("studentid");
        String bookId = request.getParameter("bookid");
        String bookName = request.getParameter("bookname");
        String email = request.getParameter("email");
        
        Utf8String _stuId= new Utf8String(stuId);
        Utf8String _bookId= new Utf8String(bookId);
        Utf8String _emailAddr= new Utf8String(bookName);
        Utf8String _bookName= new Utf8String(email);
        
        bookContract.registerStudent(_stuId, _bookId, _emailAddr, _bookName);
        
		return "redirect:/booklist";
	}
	
	
	/**
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
				Future<Utf8String> school = bookContract.getSchoolOfAddress((Address)statusResult.get(2));
				String schoolName = school.get().getValue();
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
		List<Book> searchResult = bookService.searchBookByName(book_name);
		SearchResultJson result=new SearchResultJson();
		result.setBooks(searchResult);
		result.setCount(searchResult.size());
		
		return result;
	}
	
	
	/**
	 * 借书功能
	 * @param request
	 * @return
	 */
	@RequestMapping(value="borrow")
	@ResponseBody
	public String borrowBook(HttpServletRequest request) {
		//进行合约类型的实例化
	    BookContract bookContract = new BookContract(contractAddress,web3j,credentials,gasPrice,gasLimit);
		Utf8String bookId = new Utf8String(request.getParameter("book_id"));
		Utf8String stuId = new Utf8String(request.getParameter("stu_id"));
		
		bookContract.checkBookStatus((bookId));
		//Book book = bookService.getBookById(bookId);
		String status="false";
		
		return status;
	}
	
	
	/**
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

