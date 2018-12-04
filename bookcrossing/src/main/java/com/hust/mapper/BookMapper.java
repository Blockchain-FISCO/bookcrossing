package com.hust.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
    
    List<Book> searchBookByName(@Param("bookName")String bookName,@Param("start")int start,@Param("count")int count);
    
    int getTotal();
    
    List<Book> getBooksByTag(@Param("book_id_list")String book_id_list,@Param("start")int start,@Param("count")int count);
}