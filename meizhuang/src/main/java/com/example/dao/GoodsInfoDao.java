package com.example.dao;

import com.example.entity.GoodsInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface GoodsInfoDao extends Mapper<GoodsInfo> {
    @Select("select * from goods_info left join type_info on goods_info.typeId = type_info.id where goods_info.typeId = #{typeId} limit 8")
    List<GoodsInfo> findByType(@Param("typeId") Integer typeId);

    @Select("select * from goods_info where recommend = '是' order by id desc")
    List<GoodsInfo> findRecommendGoods();

    @Select("select * from goods_info order by sales desc")
    List<GoodsInfo> findHotSalesGoods();

    List<GoodsInfo> findByNameAndUserId(@Param("name") String name,
                                        @Param("userId") Long userId,
                                        @Param("level") Integer level);

    List<GoodsInfo> getOrderGoods(@Param("userId") Long userId, @Param("level") Integer level);

    @Select("select * from goods_info g where g.name like concat('%', #{text}, '%') limit 8")
    List<GoodsInfo> findByText(String text);
}