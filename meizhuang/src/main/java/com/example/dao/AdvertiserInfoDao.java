package com.example.dao;

import com.example.entity.AdvertiserInfo;
import com.example.vo.AdvertiserInfoVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface AdvertiserInfoDao extends Mapper<AdvertiserInfo> {
    List<AdvertiserInfoVo> findByName(@Param("name") String name);
    
    
    
}
