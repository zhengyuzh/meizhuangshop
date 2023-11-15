package com.example.dao;

import com.example.entity.OrderInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface OrderInfoDao extends Mapper<OrderInfo> {

    List<OrderInfo> findByUserId(@Param("userId") Long userId,
                                 @Param("level") Integer level);
    List<OrderInfo> findByEndUserId(@Param("userId") Long userId,
                                    @Param("status") String status,
                                    @Param("level") Integer level);

    int deleteById(Long orderId);

    OrderInfo findById(Long orderId);

    @Update("update order_info set status = #{status} where id = #{id}")
    void updateStatus(@Param("id") Long id, @Param("status") String status);

    @Select("select sum(totalPrice) from order_info where status = '完成'")
    Double totalPrice();

    @Select("SELECT e.`name`, SUM(e.price) as price FROM\n" +
            "(SELECT d.`name` as `name`, a.totalPrice as price FROM order_info AS a\n" +
            "JOIN order_goods_rel AS b ON a.id = b.orderId\n" +
            "JOIN goods_info AS c ON b.goodsId = c.id\n" +
            "LEFT JOIN type_info AS d ON c.typeId = d.id) as e\n" +
            "GROUP BY e.`name`")
    List<Map<String, Object>> getTypePrice();

    @Select("SELECT e.`name`, SUM(e.count) as count FROM\n" +
            "(SELECT d.`name` as `name`, b.count as count FROM order_info AS a\n" +
            "JOIN order_goods_rel AS b ON a.id = b.orderId\n" +
            "JOIN goods_info AS c ON b.goodsId = c.id\n" +
            "LEFT JOIN type_info AS d ON c.typeId = d.id) as e\n" +
            "GROUP BY e.`name`")
    List<Map<String, Object>> getTypeCount();

    @Select("select * from order_info where orderId = #{orderId} and status = '回收品'")
    OrderInfo findHuishouByOrderId(String orderId);
}