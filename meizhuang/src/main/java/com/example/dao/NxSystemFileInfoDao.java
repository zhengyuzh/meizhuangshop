package com.example.dao;

import com.example.entity.NxSystemFileInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface NxSystemFileInfoDao extends Mapper<NxSystemFileInfo> {
    List<NxSystemFileInfo> findByName(@Param("name") String name);
    NxSystemFileInfo findByFileName(@Param("name") String name);
}
