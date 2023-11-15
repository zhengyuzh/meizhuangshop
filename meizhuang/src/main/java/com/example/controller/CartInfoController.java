package com.example.controller;

import com.example.common.Result;
import com.example.common.ResultCode;
import com.example.entity.CartInfo;
import com.example.entity.GoodsInfo;
import com.example.exception.CustomException;
import com.example.service.CartInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/cartInfo")
public class CartInfoController {
    @Resource
    private CartInfoService cartInfoService;

    /**
     * 查询所有购物车（不分页）
     *
     * @return 购物车list
     */
    @GetMapping
    public Result<List<GoodsInfo>> findAll(@RequestParam("userId") Long userId,
                                           @RequestParam("level") Integer level) {
        return Result.success(cartInfoService.findAll(userId, level));
    }

    /**
     * 查询所有购物车（分页）
     *
     * @return 购物车list
     */
    @GetMapping("/page")
    public Result<PageInfo<CartInfo>> findAll(
            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            HttpServletRequest request) {
        return Result.success(cartInfoService.findPageDetails(pageNum, pageSize, request));
    }

    /**
     * 根据id获取购物车
     *
     * @param id 购物车id
     * @return 购物车信息
     */
    @GetMapping("/{id}")
    public Result<CartInfo> findById(@PathVariable Long id) {
        return Result.success(cartInfoService.findById(id));
    }

    /**
     * 添加购物车
     *
     * @param detailInfo 购物车信息
     * @return 购物车信息
     */
    @PostMapping
    public Result<CartInfo> add(@RequestBody CartInfo detailInfo) {
        return Result.success(cartInfoService.add(detailInfo));
    }

    /**
     * 更新购物车详情
     *
     * @param detailInfo 商品购物车信息
     * @return 商品购物车信息
     */
    @PutMapping
    public Result<CartInfo> update(@RequestBody CartInfo detailInfo) {
        if (detailInfo.getId() == null) {
            throw new CustomException(ResultCode.PARAM_ERROR);
        }
        return Result.success(cartInfoService.update(detailInfo));
    }

    /**
     * 删除购物车
     *
     * @param id 商品id
     * @return result
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        cartInfoService.delete(id);
        return Result.success();
    }

    /**
     * 删除购物车
     *
     * @param userId 用户
     * @param goodsId 商品id
     * @return result
     */
    @DeleteMapping("/goods/{userId}/{level}/{goodsId}")
    public Result deleteGoods(@PathVariable Long userId, @PathVariable Integer level, @PathVariable Long goodsId) {
        cartInfoService.deleteGoods(userId, level, goodsId);
        return Result.success();
    }

    /**
     * 删除购物车
     *
     * @param userId 用户id
     * @return result
     */
    @DeleteMapping("/empty/{userId}/{level}")
    public Result empty(@PathVariable Long userId, @PathVariable Integer level) {
        cartInfoService.empty(userId, level);
        return Result.success();
    }
}
