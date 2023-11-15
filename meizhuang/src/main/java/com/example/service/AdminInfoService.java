package com.example.service;

import com.example.dao.AdminInfoDao;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.example.entity.AdminInfo;
import com.example.exception.CustomException;
import com.example.common.ResultCode;
import com.example.vo.AdminInfoVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.crypto.SecureUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class AdminInfoService {

    @Resource
    private AdminInfoDao adminInfoDao;

    public AdminInfo add(AdminInfo adminInfo) {
        // 唯一校验
        int count = adminInfoDao.checkRepeat("name", adminInfo.getName(), null);
        if (count > 0) {
            throw new CustomException("1001", "用户名\"" + adminInfo.getName() + "\"已存在");
        }
        if (StringUtils.isEmpty(adminInfo.getPassword())) {
            // 默认密码123456
            adminInfo.setPassword(SecureUtil.md5("123456"));
        } else {
            adminInfo.setPassword(SecureUtil.md5(adminInfo.getPassword()));
        }
        adminInfoDao.insertSelective(adminInfo);
        return adminInfo;
    }

    public void delete(Long id) {
        adminInfoDao.deleteByPrimaryKey(id);
    }

    public void update(AdminInfo adminInfo) {
        adminInfoDao.updateByPrimaryKeySelective(adminInfo);
    }

    public AdminInfo findById(Long id) {
        return adminInfoDao.selectByPrimaryKey(id);
    }

    public List<AdminInfoVo> findAll() {
        return adminInfoDao.findByName("all");
    }

    public PageInfo<AdminInfoVo> findPage(String name, Integer pageNum, Integer pageSize, HttpServletRequest request) {
        PageHelper.startPage(pageNum, pageSize);
        List<AdminInfoVo> all = adminInfoDao.findByName(name);
        return PageInfo.of(all);
    }

    public AdminInfoVo findByUserName(String name) {
        return adminInfoDao.findByUsername(name);
    }

    public AdminInfo login(String username, String password) {
        AdminInfo adminInfo = adminInfoDao.findByUsername(username);
        if (adminInfo == null) {
            throw new CustomException(ResultCode.USER_ACCOUNT_ERROR);
        }
        if (!SecureUtil.md5(password).equalsIgnoreCase(adminInfo.getPassword())) {
            throw new CustomException(ResultCode.USER_ACCOUNT_ERROR);
        }
        return adminInfo;
    }

}
