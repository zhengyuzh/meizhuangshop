package com.example.controller;

import com.example.common.Result;
import com.example.entity.MessageInfo;
import com.example.service.MessageInfoService;
import com.example.vo.MessageInfoVo;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/messageInfo")
public class MessageInfoController {
    @Resource
    private MessageInfoService messageInfoService;

    @PostMapping
    public Result<MessageInfo> add(@RequestBody MessageInfoVo messageInfo) {
        messageInfoService.add(messageInfo);
        return Result.success(messageInfo);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        messageInfoService.delete(id);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody MessageInfoVo messageInfo) {
        messageInfoService.update(messageInfo);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<MessageInfo> detail(@PathVariable Long id) {
        MessageInfo messageInfo = messageInfoService.findById(id);
        return Result.success(messageInfo);
    }

    @GetMapping
    public Result<List<MessageInfoVo>> all() {
        return Result.success(messageInfoService.findAll());
    }

    @GetMapping("/page/{name}")
    public Result<PageInfo<MessageInfoVo>> page(@PathVariable String name,
                                                @RequestParam(defaultValue = "1") Integer pageNum,
                                                @RequestParam(defaultValue = "5") Integer pageSize,
                                                HttpServletRequest request) {
        return Result.success(messageInfoService.findPage(name, pageNum, pageSize, request));
    }

}
