package com.example.service;

import com.example.dao.TypeInfoDao;
import org.springframework.stereotype.Service;
import com.example.entity.TypeInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TypeInfoService {

    @Resource
    private TypeInfoDao typeInfoDao;

    public TypeInfo add(TypeInfo typeInfo) {
        typeInfoDao.insertSelective(typeInfo);
        return typeInfo;
    }

    public void delete(Long id) {
        typeInfoDao.deleteByPrimaryKey(id);
    }

    public void update(TypeInfo typeInfo) {
        typeInfoDao.updateByPrimaryKeySelective(typeInfo);
    }

    public TypeInfo findById(Long id) {
        return typeInfoDao.selectByPrimaryKey(id);
    }

    public List<TypeInfo> findAll() {
        return typeInfoDao.selectAll();
    }

    public PageInfo<TypeInfo> findPage(Integer pageNum, Integer pageSize, String name) {
        PageHelper.startPage(pageNum, pageSize);
        List<TypeInfo> all = typeInfoDao.findByName(name);
        return PageInfo.of(all);
    }

}
