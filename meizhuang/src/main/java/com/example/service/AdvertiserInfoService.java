package com.example.service;

import cn.hutool.json.JSONUtil;
import com.example.dao.AdvertiserInfoDao;
import org.springframework.stereotype.Service;
import com.example.entity.AdvertiserInfo;
import com.example.entity.AuthorityInfo;
import com.example.entity.Account;
import com.example.vo.AdvertiserInfoVo;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Value;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class AdvertiserInfoService {

    @Value("${authority.info}")
    private String authorityInfo;

    @Resource
    private AdvertiserInfoDao advertiserInfoDao;

    public AdvertiserInfo add(AdvertiserInfo advertiserInfo) {
        advertiserInfo.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        advertiserInfoDao.insertSelective(advertiserInfo);
        return advertiserInfo;
    }

    public void delete(Long id) {
        advertiserInfoDao.deleteByPrimaryKey(id);
    }

    public void update(AdvertiserInfo advertiserInfo) {
        advertiserInfo.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        advertiserInfoDao.updateByPrimaryKeySelective(advertiserInfo);
    }

    public AdvertiserInfo findById(Long id) {
        return advertiserInfoDao.selectByPrimaryKey(id);
    }

    public List<AdvertiserInfoVo> findAll() {
        return advertiserInfoDao.findByName("all");
    }

    public PageInfo<AdvertiserInfoVo> findPage(String name, Integer pageNum, Integer pageSize, HttpServletRequest request) {
        PageHelper.startPage(pageNum, pageSize);
        List<AdvertiserInfoVo> all = findAllPage(request, name);
        return PageInfo.of(all);
    }

    public List<AdvertiserInfoVo> findAllPage(HttpServletRequest request, String name) {
		return advertiserInfoDao.findByName(name);
    }

}
