package com.hust.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hust.mapper.BookMapper;
import com.hust.mapper.BorrowRecordMapper;
import com.hust.mapper.want_bookMapper;
import com.hust.pojo.Book;
import com.hust.pojo.BorrowRecord;
import com.hust.pojo.Want_book;
import com.hust.service.BookService;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookMapper bookMapper;
	@Autowired
	private BorrowRecordMapper borrowRecordMapper;
	@Autowired
	private want_bookMapper wantBookMapper;
	
	@Override
	public Book getBookById(String bookId) {
		// TODO Auto-generated method stub
		return bookMapper.selectByPrimaryKey(bookId);
	}

	@Override
	public List<Book> list(int start, int count) {
		// TODO Auto-generated method stub
		return bookMapper.list(start, count);
	}

	@Override
	public int getTotal() {
		// TODO Auto-generated method stub
		return bookMapper.getTotal();
	}

	@Override
	public void addBook(Book book) {
		// TODO Auto-generated method stub
		bookMapper.insert(book);
	}

	@Override
	public List<Book> homeList() {
		// TODO Auto-generated method stub
		return bookMapper.homeList();
	}

	@Override
	public void deleteBookById(String bookId) {
		// TODO Auto-generated method stub
		bookMapper.deleteByPrimaryKey(bookId);
	}

	@Override
	public void updateBook(Book book) {
		// TODO Auto-generated method stub
		bookMapper.updateByPrimaryKey(book);
	}

	@Override
	public List<Book> searchBookByName(String bookName,int start, int count) {
		// TODO Auto-generated method stub
		return bookMapper.searchBookByName(bookName,start,count);
	}

	@Override
	public List<String> getBorrowedBooksId(String stuId) {
		// TODO Auto-generated method stub
		return borrowRecordMapper.getBorrowedBooksId(stuId);
	}

	@Override
	public void addBorrowedRecord(BorrowRecord record) {
		// TODO Auto-generated method stub
		borrowRecordMapper.insert(record);
	}

	@Override
	public void deleteBorrowedRecord(String bookId) {
		// TODO Auto-generated method stub
		borrowRecordMapper.deleteByPrimaryKey(bookId);
	}

	@Override
	public Want_book getWant_bookBySIdABId(String book_id, String stu_id) {
		// TODO Auto-generated method stub
		return wantBookMapper.selectBySIdAndBId(book_id, stu_id);
	}

	@Override
	public void insert(Want_book record) {
		// TODO Auto-generated method stub
		wantBookMapper.insert(record);
	}

	@Override
	public List<Book> getBooksByTag(String book_id_list, int start, int count) {
		// TODO Auto-generated method stub
		return bookMapper.getBooksByTag(book_id_list, start, count);
	}

	@Override
	public List<Want_book> getWant_bookByBId(String book_id) {
		// TODO Auto-generated method stub	
		return wantBookMapper.selectByBId(book_id);
	}

	@Override
	public void deleteWant_bookBySIdABId(String book_id, String stu_id) {
		// TODO Auto-generated method stub
		wantBookMapper.deleteBySIdAndBId(book_id, stu_id);
	}
	
	
}
