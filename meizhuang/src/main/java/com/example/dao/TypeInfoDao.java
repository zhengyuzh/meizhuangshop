package com.example.dao;

import com.example.entity.TypeInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TypeInfoDao extends Mapper<TypeInfo> {

    List<TypeInfo> findByName(@Param("name") String name);
}