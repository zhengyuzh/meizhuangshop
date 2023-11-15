package com.example.dao;

import com.example.entity.OrderGoodsRel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OrderGoodsRelDao extends Mapper<OrderGoodsRel> {
    List<OrderGoodsRel> findByOrderId(Long orderId);

    void deleteByGoodsIdAndOrderId(Long goodsId, Long orderId);

    int deleteByOrderId(Long orderId);

    @Select("select sum(count) from order_goods_rel")
    Integer totalShopping();
}