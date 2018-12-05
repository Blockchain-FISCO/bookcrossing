package com.hust.mapper;

import java.util.List;

import com.hust.pojo.BorrowRecord;

public interface BorrowRecordMapper {
    int deleteByPrimaryKey(String bookId);

    int insert(BorrowRecord record);

    int insertSelective(BorrowRecord record);

    BorrowRecord selectByPrimaryKey(String bookId);

    int updateByPrimaryKeySelective(BorrowRecord record);

    int updateByPrimaryKey(BorrowRecord record);
    
    List<String> getBorrowedBooksId(String stuId);
    
    List<String> getAllBorrowedBooks();
}