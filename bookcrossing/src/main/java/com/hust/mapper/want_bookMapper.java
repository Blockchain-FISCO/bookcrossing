package com.hust.mapper;

import java.util.List;

import com.hust.pojo.Want_book;

public interface want_bookMapper {
    int deleteByPrimaryKey(String wantId);
    
    int deleteBySIdAndBId(String book_id,String stu_id);

    int insert(Want_book record);

    int insertSelective(Want_book record);

    Want_book selectByPrimaryKey(String wantId);

    int updateByPrimaryKeySelective(Want_book record);

    int updateByPrimaryKey(Want_book record);
    
    Want_book selectBySIdAndBId(String book_id,String stu_id);
    
    List<Want_book> selectByBId(String book_id);
}