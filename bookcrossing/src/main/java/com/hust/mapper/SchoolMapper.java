package com.hust.mapper;

import com.hust.pojo.School;

public interface SchoolMapper {
    int deleteByPrimaryKey(String schoId);

    int insert(School record);

    int insertSelective(School record);

    School selectByPrimaryKey(String schoId);

    int updateByPrimaryKeySelective(School record);

    int updateByPrimaryKey(School record);
}