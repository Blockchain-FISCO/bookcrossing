package com.hust.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
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
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hust.contract.Book6;
import com.hust.contract.BookClient;
import com.hust.contract.BookFlow;
import com.hust.mail.MailSender;
import com.hust.mail.MailSenderInfoBean;
import com.hust.pojo.Book;
import com.hust.pojo.BorrowRecord;
import com.hust.pojo.Student;
import com.hust.pojo.Want_Book_Hot;
import com.hust.pojo.Want_book;
import com.hust.service.BookService;
import com.hust.service.StudentService;
import com.hust.util.BookDetail;
import com.hust.util.BookInfo;
import com.hust.util.BookResultForSearch;
import com.hust.util.BorrowedBooksJson;
import com.hust.util.HomelistJson;
import com.hust.util.MessageBean;
import com.hust.util.MyBorrowedBooks;
import com.hust.util.Page;
import com.hust.util.SearchResultJson;


@EnableScheduling
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
	public static ECKeyPair keyPair;
	public static Credentials credentials;
    public static String contractAddress = "0x5c1c0fcfb3820199e28afbd248c73f353300e6f2";
    public static Book6 bookContract;
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

		logger = Logger.getLogger(BookClient.class);
		blockchainService.run(); // run the daemon service
		// init the client keys
		keyPair = Keys.createEcKeyPair();
		credentials = Credentials.create(keyPair);
		ChannelEthereumService channelEthereumService = new ChannelEthereumService();
		channelEthereumService.setChannelService(blockchainService);

		// init webj client base on channelEthereumService
		web3j = Web3j.build(channelEthereumService);
		bookContract = Book6.load(contractAddress, web3j, credentials, gasPrice, gasLimit);
		EthBlockNumber ethBlockNumber = web3j.ethBlockNumber().sendAsync().get();
		int startBlockNumber = ethBlockNumber.getBlockNumber().intValue();
//		    logger.info("====================================================================================");
		logger.info("-->Got ethBlockNumber:{" + startBlockNumber + "}");
		System.out.println("=========>初始化成功");

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
	 * 热门书籍（返回给安卓端）
	 * @return
	 */
	@RequestMapping(value="hotlist")
	@ResponseBody
	public HomelistJson hotlist(HttpServletRequest request) {
		//获取热门书籍的ID以及相应的want次数
		List<Want_Book_Hot> want_book_hot = bookService.selectByHotBookNum();		
		
		//设置返回Json数据原型
		HomelistJson homelistJson=new HomelistJson();
		
		// 获取分页参数
        int start = 0;
        int count = 5;
        try {
            start = Integer.parseInt(request.getParameter("start"));
            count = Integer.parseInt(request.getParameter("count"));
        } catch (Exception e) {
        }
		
        //获取对应ID的热门书籍的具体书籍信息
        int want_book_hot_size= want_book_hot.size();
        List<Book> book_hot_list = null;
        for(int i = 0; i<want_book_hot_size; i++) {
        	String book_hot_id = want_book_hot.get(i).getBookId();
        	Book book_hot = bookService.hotBook(book_hot_id);
        	book_hot_list.add(book_hot);        	
        }
        	      
        //进行返回书籍格式的封装
		int total = book_hot_list.size();		
		homelistJson.setCount(total);
		
		List<BookInfo> temp = new ArrayList<BookInfo>();
				
		//进行数据的分页(这里采取的是物理分页)
		for(int i = start; i<count;i++) {
			Book b =  book_hot_list.get(i);
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
	 * 需要捐献的书籍（返回给安卓端）
	 * 需要捐献的书籍 = 被 want 次数较多的书籍的名称（相同书名、不同书籍ID的书籍会被去重）
	 * @return
	 */
	@RequestMapping(value="needlist")
	@ResponseBody
	public HomelistJson needlist(HttpServletRequest request) {
		//获取热门书籍的ID以及相应的want次数
		List<Want_Book_Hot> want_book_hot = bookService.selectByHotBookNum();		
		
		//设置返回Json数据原型
		HomelistJson homelistJson=new HomelistJson();
		
		// 获取分页参数
        int start = 0;
        int count = 5;
        try {
            start = Integer.parseInt(request.getParameter("start"));
            count = Integer.parseInt(request.getParameter("count"));
        } catch (Exception e) {
        }
		
       
        //获取对应ID的热门书籍的具体书籍信息，同时进行去重
        int want_book_hot_size= want_book_hot.size();
        List<Book> book_hot_list = null;
        for(int i = 0; i<want_book_hot_size; i++) {
        	String book_hot_id = want_book_hot.get(i).getBookId();
        	
        	Book book_hot = bookService.hotBook(book_hot_id);
        	book_hot_list.add(book_hot);        	
        }
        	
        //字段去重逻辑,进行字符的相同判断然后去重
        Set set = new TreeSet<Book>(new Comparator<Book>(){
        	@Override
        	public int compare(Book book1, Book book2) {
        		//
        		return book1.getBookName().compareTo(book2.getBookName());
        	}
        });
        
        //返回去重的书籍列表
        set.addAll(book_hot_list);
        
        List<Book> book_hot_nodup__list = new ArrayList<Book>(set);
       
        //进行返回书籍格式的封装
		int total = book_hot_nodup__list.size();		
		homelistJson.setCount(total);
		
		List<BookInfo> temp = new ArrayList<BookInfo>();
				
		//进行数据的分页(这里采取的是物理分页)
		for(int i = start; i<count;i++) {
			Book b =  book_hot_nodup__list.get(i);
			BookInfo bookInfo=new BookInfo();
			bookInfo.setBook_id(b.getBookId());
			bookInfo.setBook_name(b.getBookName());
			bookInfo.setPicture(b.getPicture());;
			temp.add(bookInfo);
		}

		homelistJson.setBooks(temp);
		
		return homelistJson;
	}
	
//	/**
//	 * 今日上新书籍（返回给安卓端）
//	 * @return
//	 */
//	@RequestMapping(value="todaylist")
//	@ResponseBody
//	public HomelistJson todaylist(HttpServletRequest request) {
//		HomelistJson homelistJson=new HomelistJson();
//		// 获取分页参数
//        int start = 0;
//        int count = 5;
//
//        try {
//            start = Integer.parseInt(request.getParameter("start"));
//            count = Integer.parseInt(request.getParameter("count"));
//        } catch (Exception e) {
//        }
//		
//		//List<Book> books = bookService.homeList();
//        List<Book> books = bookService.list(start, count);
//		
//		int total = books.size();
//		homelistJson.setCount(total);
//		
//		List<BookInfo> temp=new ArrayList<BookInfo>();
//				
//		for(Book b: books) {
//			BookInfo bookInfo=new BookInfo();
//			bookInfo.setBook_id(b.getBookId());
//			bookInfo.setBook_name(b.getBookName());
//			bookInfo.setPicture(b.getPicture());;
//			temp.add(bookInfo);
//		}
//		homelistJson.setBooks(temp);
//		
//		return homelistJson;
//	}
	
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
		//设置想看数量
		BigInteger wantCount = ((Uint256)statusResult.get(2)).getValue();
		bookDetail.setLikeCount(wantCount.intValue());
		
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
			
			//在want表中删除对应条目
			Want_book want_stu = new Want_book();
			want_stu.setBookId(bookId.getValue());
			want_stu.setStuId(stuId.getValue());
			bookService.deleteWant_bookBySIdABId(bookId.getValue(), stuId.getValue());
			status = true;
		}
		
		return status;
	}
	
	/**
	 * 还书后功能 -- 发送邮件
	 * @return
	 */
	public void sendMail(String bookId) {
		List<Want_book> want_stu_list = bookService.getWant_bookByBId(bookId);
		List<String> mail_address_list = new ArrayList<String>();
		
		for(int i = 0; i< want_stu_list.size(); i++) {
			Want_book want_stu = want_stu_list.get(i);
			Utf8String _stuId = new Utf8String(want_stu.getStuId());
			
			Future<List<Type>> student_info_list = bookContract.getStudent(_stuId);			
			try {
				List<Type> student_info_result = student_info_list.get();
				String mail_address = ((Utf8String)student_info_result.get(1)).getValue();
				mail_address_list.add(mail_address);
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		MailSenderInfoBean mailInfo = new MailSenderInfoBean();
		
		mailInfo.setMailServerHost("smtp.163.com");      
		mailInfo.setMailServerPort("25");     
		mailInfo.setValidate(true);        
		mailInfo.setUserName("alburtams@163.com");    
		mailInfo.setPassword("hwfwdmm1314521");//您的邮箱授权码        
	    mailInfo.setFromAddress("alburtams@163.com");
		mailInfo.setToAddress(mail_address_list);     
		mailInfo.setSubject("图书漂流");        
		mailInfo.setContent("您想阅读的书籍目前已经归还，请您及时借阅");     
		
		MailSender sender = new MailSender();
		sender.sendTextMail(mailInfo);	
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
		
		//在还书后进行邮件发送
		sendMail(bookId.getValue());
		return status;		
	}	
	
	/**
	 * 点击想看书籍
	 * @param request
	 * @return
	 */
	@RequestMapping(value="book/like")
	@ResponseBody
	public MessageBean likeBook(HttpServletRequest request) {
		MessageBean status=new MessageBean();
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
			status.setResult(200);
			status.setErrMsg("success");
		}else {
			//已经点击过想看
			status.setResult(500);;
			status.setErrMsg("err");
		}
		
		
		return status;
	}
	
	
	/**
	 * 	判断用户是否点击过想看
	 * @param request
	 * @return
	 */
	@RequestMapping(value="book/liked")
	@ResponseBody
	public MessageBean likedBook(HttpServletRequest request) {
		MessageBean status=new MessageBean();
		//获取请求数据
		String book_id = request.getParameter("book_id");
		String stu_id = request.getParameter("stu_id");
		
		//检查该用户是否点击过想看
		Want_book result = bookService.getWant_bookBySIdABId(book_id, stu_id);
		if(result==null) {
			status.setResult(500);;
			status.setErrMsg("none");
		}else {
			//已经点击过想看
			status.setResult(200);
			status.setErrMsg("liked");
		}
		
		
		return status;
	}
	
	/**
	 * 	获取用户想看列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="book/likeList")
	@ResponseBody
	public SearchResultJson liskeList(HttpServletRequest request) {
		String stu_id=request.getParameter("stu_id");
		List<Want_book> wantList = bookService.getWant_bookBySId(stu_id);
		SearchResultJson result=new SearchResultJson();
		List<BookResultForSearch> books=new ArrayList<BookResultForSearch>();
		
		for(Want_book wb:wantList) {
			Book tempBook = bookService.getBookById(wb.getBookId());
			BookResultForSearch tbrs=new BookResultForSearch();
			tbrs.setBookResult(tempBook);
			books.add(tbrs);
		}
		
		result.setBooks(books);
		result.setCount(wantList.size());
		
		return result;
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
	
	/**
	 * 根据流行度更新书籍借阅时间
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	@Scheduled(cron = "0 0 0 * * ? ") // 每天24点执行一次
	public void UpdateBorrowDuration() throws InterruptedException, ExecutionException {
		//从borrow_record中获取已经借出去的书籍列表
		List<String> allBorrowedBookId = bookService.getAllBorrowedBooks();
		
		for(String b_id : allBorrowedBookId) {
			Utf8String _bookId = new Utf8String(b_id);
			Future<List<Type>> checkOverdue = bookContract.checkOverdue(_bookId);
			List<Type> checkresult = checkOverdue.get();
			Boolean isOutOfDays = ((Bool)checkresult.get(0)).getValue();
			
			if(isOutOfDays) {
				//已经超出借阅时间，智能合约自动还书，需要删除对应数据库的记录
				bookService.deleteBorrowedRecord(b_id);
			}else {
				//未超出借阅时间，则根据想看数量更新最长借阅时间
			}
		}
	}

}