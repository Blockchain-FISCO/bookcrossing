package com.hust.mapper;

import com.hust.pojo.want_book;

public interface want_bookMapper {
    int deleteByPrimaryKey(String wantId);

    int insert(want_book record);

    int insertSelective(want_book record);

    want_book selectByPrimaryKey(String wantId);

    int updateByPrimaryKeySelective(want_book record);

    int updateByPrimaryKey(want_book record);
}