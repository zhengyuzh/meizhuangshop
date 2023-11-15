package com.example.controller;

import com.example.common.Result;
import com.example.entity.CommentInfo;
import com.example.entity.Account;
import com.example.service.CommentInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/commentInfo")
public class CommentInfoController {
    @Resource
    private CommentInfoService commentInfoService;

    @PostMapping
    public Result<CommentInfo> add(@RequestBody CommentInfo commentInfo, HttpServletRequest request) {
        Account user = (Account) request.getSession().getAttribute("user");
        commentInfo.setUserId(user.getId());
        commentInfoService.add(commentInfo);
        return Result.success(commentInfo);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        commentInfoService.delete(id);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody CommentInfo commentInfo) {
        commentInfoService.update(commentInfo);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<CommentInfo> detail(@PathVariable Long id) {
        CommentInfo commentInfo = commentInfoService.findById(id);
        return Result.success(commentInfo);
    }

    @GetMapping
    public Result<List<CommentInfo>> all() {
        return Result.success(commentInfoService.findAll());
    }

    @GetMapping("/all/{goodsId}")
    public Result<List<CommentInfo>> all(@PathVariable("goodsId") Long goodsId) {
        return Result.success(commentInfoService.findAll(goodsId));
    }

    @GetMapping("/page/{name}")
    public Result<PageInfo<CommentInfo>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                              @RequestParam(defaultValue = "10") Integer pageSize,
                                              @PathVariable String name,
                                              HttpServletRequest request) {
        return Result.success(commentInfoService.findPage(pageNum, pageSize, name, request));
    }
}
