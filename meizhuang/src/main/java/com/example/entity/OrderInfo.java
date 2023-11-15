package com.example.entity;

import javax.persistence.*;
import java.util.List;

@Table(name = "order_info")
public class OrderInfo {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 订单id
     */
    @Column(name = "orderId")
    private String orderId;

    /**
     * 订单总价格
     */
    @Column(name = "totalPrice")
    private Double totalPrice;

    /**
     * 所属用户
     */
    @Column(name = "userId")
    private Long userId;

    @Column(name = "level")
    private Integer level;

    /**
     * 联系地址
     */
    @Column(name = "linkAddress")
    private String linkAddress;

    /**
     * 联系电话
     */
    @Column(name = "linkPhone")
    private String linkPhone;

    /**
     * 联系人
     */
    @Column(name = "linkMan")
    private String linkMan;

    /**
     * 寄货地址
     */
    @Column(name = "jihuoAddress")
    private String jihuoAddress;

    /**
     * 寄货电话
     */
    @Column(name = "jihuoPhone")
    private String jihuoPhone;

    /**
     * 寄货人
     */
    @Column(name = "jihuoMan")
    private String jihuoMan;

    /**
     * 创建时间
     */
    @Column(name = "createTime")
    private String createTime;

    private String status;

    @Transient
    private Account userInfo;

    @Transient
    private List<GoodsInfo> goodsList;

    /**
     * 获取主键id
     *
     * @return id - 主键id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键id
     *
     * @param id 主键id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取订单id
     *
     * @return order_id - 订单id
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 设置订单id
     *
     * @param orderId 订单id
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取订单总价格
     *
     * @return total_price - 订单总价格
     */
    public Double getTotalPrice() {
        return totalPrice;
    }

    /**
     * 设置订单总价格
     *
     * @param totalPrice 订单总价格
     */
    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * 获取所属用户
     *
     * @return user_id - 所属用户
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置所属用户
     *
     * @param userId 所属用户
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取联系地址
     *
     * @return link_address - 联系地址
     */
    public String getLinkAddress() {
        return linkAddress;
    }

    /**
     * 设置联系地址
     *
     * @param linkAddress 联系地址
     */
    public void setLinkAddress(String linkAddress) {
        this.linkAddress = linkAddress;
    }

    /**
     * 获取联系电话
     *
     * @return link_phone - 联系电话
     */
    public String getLinkPhone() {
        return linkPhone;
    }

    /**
     * 设置联系电话
     *
     * @param linkPhone 联系电话
     */
    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }

    /**
     * 获取联系人
     *
     * @return link_man - 联系人
     */
    public String getLinkMan() {
        return linkMan;
    }

    /**
     * 设置联系人
     *
     * @param linkMan 联系人
     */
    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Account getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(Account userInfo) {
        this.userInfo = userInfo;
    }

    public List<GoodsInfo> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsInfo> goodsList) {
        this.goodsList = goodsList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getJihuoAddress() {
        return jihuoAddress;
    }

    public void setJihuoAddress(String jihuoAddress) {
        this.jihuoAddress = jihuoAddress;
    }

    public String getJihuoPhone() {
        return jihuoPhone;
    }

    public void setJihuoPhone(String jihuoPhone) {
        this.jihuoPhone = jihuoPhone;
    }

    public String getJihuoMan() {
        return jihuoMan;
    }

    public void setJihuoMan(String jihuoMan) {
        this.jihuoMan = jihuoMan;
    }
}