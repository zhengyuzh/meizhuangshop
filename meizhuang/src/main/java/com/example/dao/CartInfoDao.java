package com.example.dao;

import com.example.entity.CartInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CartInfoDao extends Mapper<CartInfo> {
    List<CartInfo> findCartByUserId(@Param("userId") Long userId, @Param("level") Integer level);

    List<CartInfo> findAll();

    @Delete("delete from cart_info where userId = #{userId} and level = #{level}")
    int deleteByUserId(@Param("userId") Long userId, @Param("level") Integer level);

    @Delete("delete from cart_info where userId = #{userId} and level = #{level} and goodsId = #{goodsId}")
    int deleteGoods(@Param("userId") Long userId,
                    @Param("level") Integer level,
                    @Param("goodsId") Long goodsId);
}