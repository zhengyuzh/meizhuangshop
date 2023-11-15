package com.example.dao;

import com.example.entity.MessageInfo;
import com.example.vo.MessageInfoVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface MessageInfoDao extends Mapper<MessageInfo> {
    List<MessageInfoVo> findByName(@Param("name") String name);


    List<MessageInfoVo> findByParentId(Long parentId);
}
