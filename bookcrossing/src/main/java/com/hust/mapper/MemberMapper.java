package com.hust.mapper;

import com.hust.pojo.Member;

public interface MemberMapper {
    int deleteByPrimaryKey(String stuId);

    int insert(Member record);

    int insertSelective(Member record);

    Member selectByPrimaryKey(String stuId);

    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKey(Member record);
}