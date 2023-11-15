package com.example.service;

import com.example.dao.NxSystemFileInfoDao;
import com.example.entity.NxSystemFileInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NxSystemFileInfoService {

    @Value("${authority.info}")
    private String authorityInfo;

    @Resource
    private NxSystemFileInfoDao nxSystemFileInfoDao;

    public NxSystemFileInfo add(NxSystemFileInfo nxSystemFileInfo) {
        nxSystemFileInfoDao.insertSelective(nxSystemFileInfo);
        return nxSystemFileInfo;
    }

    public void delete(Long id) {
        nxSystemFileInfoDao.deleteByPrimaryKey(id);
    }

    public void update(NxSystemFileInfo nxSystemFileInfo) {
        nxSystemFileInfoDao.updateByPrimaryKeySelective(nxSystemFileInfo);
    }

    public NxSystemFileInfo findById(Long id) {
        return nxSystemFileInfoDao.selectByPrimaryKey(id);
    }
    
    public NxSystemFileInfo findByFileName(String name) {
        return nxSystemFileInfoDao.findByFileName(name);
    }

    public List<NxSystemFileInfo> findAll() {
        return nxSystemFileInfoDao.findByName("all");
    }

    public PageInfo<NxSystemFileInfo> findPage(String name, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<NxSystemFileInfo> all = nxSystemFileInfoDao.findByName(name);
        return PageInfo.of(all);
    }
}
