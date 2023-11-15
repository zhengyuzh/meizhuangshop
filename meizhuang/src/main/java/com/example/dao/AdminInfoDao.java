package com.example.dao;

import com.example.entity.AdminInfo;
import com.example.vo.AdminInfoVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface AdminInfoDao extends Mapper<AdminInfo> {
    List<AdminInfoVo> findByName(@Param("name") String name);
    
    int checkRepeat(@Param("column") String column, @Param("value") String value, @Param("id") Long id);
    AdminInfoVo findByUsername(String username);
    Integer count();
}
