package com.hust.service;

import java.util.List;

import com.hust.pojo.Book;
import com.hust.pojo.BorrowRecord;
import com.hust.pojo.Want_Book_Hot;
import com.hust.pojo.Want_book;

public interface BookService {
	public Book getBookById(String bookId);
	public List<Book> list(int start,int count);
	public List<Book> homeList();
	public Book hotBook(String book_id);
	public Book needBook(String book_id);
	public void addBook(Book book);
	public void deleteBookById(String bookId);
	public int getTotal();
	public void updateBook(Book book);
	public List<Book> searchBookByName(String bookName, int start ,int count);
	public List<String> getBorrowedBooksId(String stuId);
	public void addBorrowedRecord(BorrowRecord record);
	public void deleteBorrowedRecord(String bookId);
	public Want_book getWant_bookBySIdABId(String book_id,String stu_id);
	public List<Want_book> getWant_bookByBId(String book_id);
	public List<Want_Book_Hot> selectByHotBookNum();
	public void insert(Want_book record);
	public void deleteWant_bookBySIdABId(String book_id,String stu_id);
	public List<Book> getBooksByTag(String book_id_list, int start, int count);
	public List<String> getAllBorrowedBooks();
}
