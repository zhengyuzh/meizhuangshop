package com.example.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.Result;
import com.example.common.ResultCode;
import com.example.entity.AdvertiserInfo;
import com.example.service.*;
import com.example.vo.AdvertiserInfoVo;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/advertiserInfo")
public class AdvertiserInfoController {
    @Resource
    private AdvertiserInfoService advertiserInfoService;

    @PostMapping
    public Result<AdvertiserInfo> add(@RequestBody AdvertiserInfoVo advertiserInfo) {
        advertiserInfoService.add(advertiserInfo);
        return Result.success(advertiserInfo);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        advertiserInfoService.delete(id);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody AdvertiserInfoVo advertiserInfo) {
        advertiserInfoService.update(advertiserInfo);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<AdvertiserInfo> detail(@PathVariable Long id) {
        AdvertiserInfo advertiserInfo = advertiserInfoService.findById(id);
        return Result.success(advertiserInfo);
    }

    @GetMapping
    public Result<List<AdvertiserInfoVo>> all() {
        return Result.success(advertiserInfoService.findAll());
    }

    @GetMapping("/page/{name}")
    public Result<PageInfo<AdvertiserInfoVo>> page(@PathVariable String name,
                                                @RequestParam(defaultValue = "1") Integer pageNum,
                                                @RequestParam(defaultValue = "5") Integer pageSize,
                                                HttpServletRequest request) {
        return Result.success(advertiserInfoService.findPage(name, pageNum, pageSize, request));
    }

    /**
    * 批量通过excel添加信息
    * @param file excel文件
    * @throws IOException
    */
    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {

        List<AdvertiserInfo> infoList = ExcelUtil.getReader(file.getInputStream()).readAll(AdvertiserInfo.class);
        if (!CollectionUtil.isEmpty(infoList)) {
            // 处理一下空数据
            List<AdvertiserInfo> resultList = infoList.stream().filter(x -> ObjectUtil.isNotEmpty(x.getName())).collect(Collectors.toList());
            for (AdvertiserInfo info : resultList) {
                advertiserInfoService.add(info);
            }
        }
        return Result.success();
    }

    @GetMapping("/getExcelModel")
    public void getExcelModel(HttpServletResponse response) throws IOException {
        // 1. 生成excel
        Map<String, Object> row = new LinkedHashMap<>();
		row.put("name", "系统公告");
		row.put("content", "这是系统公告，管理员可以在后台修改");
		row.put("time", "TIME");

        List<Map<String, Object>> list = CollUtil.newArrayList(row);

        // 2. 写excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition","attachment;filename=advertiserInfoModel.xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(System.out);
    }
}
