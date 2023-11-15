package com.example.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.Result;
import com.example.entity.TypeInfo;
import com.example.service.TypeInfoService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/typeInfo")
public class TypeInfoController {
    @Resource
    private TypeInfoService typeInfoService;

    @PostMapping
    public Result<TypeInfo> add(@RequestBody TypeInfo typeInfo) {
        typeInfoService.add(typeInfo);
        return Result.success(typeInfo);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        typeInfoService.delete(id);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody TypeInfo typeInfo) {
        typeInfoService.update(typeInfo);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<TypeInfo> detail(@PathVariable Long id) {
        TypeInfo typeInfo = typeInfoService.findById(id);
        return Result.success(typeInfo);
    }

    @GetMapping
    public Result<List<TypeInfo>> all() {
        return Result.success(typeInfoService.findAll());
    }

    @GetMapping("/page/{name}")
    public Result<PageInfo<TypeInfo>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                           @PathVariable String name) {
        return Result.success(typeInfoService.findPage(pageNum, pageSize, name));
    }

    /**
     * 批量通过excel添加信息
     * @param file excel文件
     * @throws IOException
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {

        List<TypeInfo> infoList = ExcelUtil.getReader(file.getInputStream()).readAll(TypeInfo.class);
        if (!CollectionUtil.isEmpty(infoList)) {
            // 处理一下空数据
            List<TypeInfo> resultList = infoList.stream().filter(x -> ObjectUtil.isNotEmpty(x.getName())).collect(Collectors.toList());
            for (TypeInfo info : resultList) {
                typeInfoService.add(info);
            }
        }
        return Result.success();
    }

    @GetMapping("/getExcelModel")
    public void getExcelModel(HttpServletResponse response) throws IOException {
        // 1. 生成excel
        Map<String, Object> row = new LinkedHashMap<>();
        row.put("name", "女装");
        row.put("description", "这是女装");

        List<Map<String, Object>> list = CollUtil.newArrayList(row);

        // 2. 写excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition","attachment;filename=typeInfoModel.xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(System.out);
    }
}
