package com.hust.mapper;

import java.util.List;

import com.hust.pojo.Book;

public interface BookMapper {
    int deleteByPrimaryKey(String bookId);

    int insert(Book record);

    int insertSelective(Book record);

    Book selectByPrimaryKey(String bookId);

    int updateByPrimaryKeySelective(Book record);

    int updateByPrimaryKey(Book record);
    
    List<Book> list(int start,int count);
    
    List<Book> homeList();
    
    List<Book> searchBookByName(String bookName);
    
    int getTotal();
}