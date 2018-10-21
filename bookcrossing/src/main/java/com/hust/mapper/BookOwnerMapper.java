package com.hust.mapper;

import com.hust.pojo.BookOwner;

public interface BookOwnerMapper {
    int deleteByPrimaryKey(String bookId);

    int insert(BookOwner record);

    int insertSelective(BookOwner record);

    BookOwner selectByPrimaryKey(String bookId);

    int updateByPrimaryKeySelective(BookOwner record);

    int updateByPrimaryKey(BookOwner record);
}