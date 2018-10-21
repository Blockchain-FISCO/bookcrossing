package com.hust.mapper;

import com.hust.pojo.Member;

public interface MemberMapper {
    int insert(Member record);

    int insertSelective(Member record);
}