package com.example.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.example.dao.CartInfoDao;
import com.example.entity.CartInfo;
import com.example.entity.GoodsInfo;
import com.example.entity.Account;
import com.example.exception.CustomException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CartInfoService {

    @Resource
    private CartInfoDao cartInfoDao;
    @Resource
    private GoodsInfoService goodsInfoService;
	@Resource
	private AdminInfoService adminInfoService;
	@Resource
	private UserInfoService userInfoService;


    public List<GoodsInfo> findAll(Long userId, Integer level) {
        List<GoodsInfo> goodsList = new ArrayList<>();
        List<CartInfo> cartInfoList = cartInfoDao.findCartByUserId(userId, level);
        for (CartInfo cartInfo : cartInfoList) {
            Long goodsId = cartInfo.getGoodsId();
            GoodsInfo goods = goodsInfoService.findById(goodsId);
            if (goods != null) {
                // 注意这里返回的count是用户加入商品的数量，而不是商品的库存
                goods.setCount(cartInfo.getCount());
                // 这里返回的id也是购物车里商品的关系id
                goods.setId(cartInfo.getGoodsId());
                goodsList.add(goods);
            }
        }
        return goodsList;
    }

    public PageInfo<CartInfo> findPageDetails(Integer pageNum, Integer pageSize, HttpServletRequest request) {
        Account user = (Account) request.getSession().getAttribute("user");
        if (user == null) {
            throw new CustomException("1001", "session已失效，请重新登录");
        }
        Integer userLevel = user.getLevel();
        PageHelper.startPage(pageNum, pageSize);
        List<CartInfo> infoList;
        if (userLevel == 1) {
            infoList = cartInfoDao.findAll();
            // 处理一下userName
            for (CartInfo info : infoList) {
                Long userId = info.getUserId();
                Integer level = info.getLevel();
				if (level == 1) {
					info.setUserName(adminInfoService.findById(userId).getName());
				}
				if (level == 3) {
					info.setUserName(userInfoService.findById(userId).getName());
				}

            }
        } else {
            infoList = cartInfoDao.findCartByUserId(user.getId(), user.getLevel());
        }
        return PageInfo.of(infoList);
    }

    public CartInfo findById(Long id) {
        return cartInfoDao.selectByPrimaryKey(id);
    }

    public CartInfo add(CartInfo detailInfo) {
        Long userId = detailInfo.getUserId();
        Long goodsId = detailInfo.getGoodsId();
        // 先查询购物车里有没有该商品，有就更新数量，没有就添加
        Example example = new Example(CartInfo.class);
        example.createCriteria()
                .andEqualTo("userId", userId)
                .andEqualTo("level", detailInfo.getLevel())
                .andEqualTo("goodsId", goodsId);
        List<CartInfo> infos = cartInfoDao.selectByExample(example);
        if (CollectionUtil.isEmpty(infos)) {
            // 新增
            detailInfo.setCreateTime(DateUtil.formatDateTime(new Date()));
            cartInfoDao.insertSelective(detailInfo);
        } else {
            // 更新
            CartInfo cartInfo = infos.get(0);
            cartInfo.setCount(cartInfo.getCount() + detailInfo.getCount());
            cartInfoDao.updateByPrimaryKeySelective(cartInfo);
        }
        return detailInfo;
    }

    public CartInfo update(CartInfo detailInfo) {
        cartInfoDao.updateByPrimaryKeySelective(detailInfo);
        return detailInfo;
    }

    public void delete(Long id) {
        cartInfoDao.deleteByPrimaryKey(id);
    }

    public void empty(Long userId, Integer level) {
        cartInfoDao.deleteByUserId(userId, level);
    }

    public void deleteGoods(Long userId, Integer level, Long goodsId) {
        cartInfoDao.deleteGoods(userId, level, goodsId);
    }
}
