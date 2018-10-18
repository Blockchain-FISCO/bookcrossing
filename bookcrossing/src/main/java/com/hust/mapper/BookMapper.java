package com.hust.mapper;

import com.hust.pojo.Book;

public interface BookMapper {
    int deleteByPrimaryKey(String bookId);

    int insert(Book record);

    int insertSelective(Book record);

    Book selectByPrimaryKey(String bookId);

    int updateByPrimaryKeySelective(Book record);

    int updateByPrimaryKey(Book record);
}