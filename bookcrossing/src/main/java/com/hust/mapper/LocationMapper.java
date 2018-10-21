package com.hust.mapper;

import com.hust.pojo.Location;

public interface LocationMapper {
    int deleteByPrimaryKey(String bookId);

    int insert(Location record);

    int insertSelective(Location record);

    Location selectByPrimaryKey(String bookId);

    int updateByPrimaryKeySelective(Location record);

    int updateByPrimaryKey(Location record);
}