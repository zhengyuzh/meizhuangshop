package com.example.service;

import com.example.dao.UserInfoDao;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.example.entity.UserInfo;
import com.example.exception.CustomException;
import com.example.common.ResultCode;
import com.example.vo.UserInfoVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.crypto.SecureUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class UserInfoService {

    @Resource
    private UserInfoDao userInfoDao;

    public UserInfo add(UserInfo userInfo) {
        // 唯一校验
        int count = userInfoDao.checkRepeat("name", userInfo.getName(), null);
        if (count > 0) {
            throw new CustomException("1001", "用户名\"" + userInfo.getName() + "\"已存在");
        }
        if (StringUtils.isEmpty(userInfo.getPassword())) {
            // 默认密码123456
            userInfo.setPassword(SecureUtil.md5("123456"));
        } else {
            userInfo.setPassword(SecureUtil.md5(userInfo.getPassword()));
        }
        userInfoDao.insertSelective(userInfo);
        return userInfo;
    }

    public void delete(Long id) {
        userInfoDao.deleteByPrimaryKey(id);
    }

    public void update(UserInfo userInfo) {
        userInfoDao.updateByPrimaryKeySelective(userInfo);
    }

    public UserInfo findById(Long id) {
        return userInfoDao.selectByPrimaryKey(id);
    }

    public List<UserInfoVo> findAll() {
        return userInfoDao.findByName("all");
    }

    public PageInfo<UserInfoVo> findPage(String name, Integer pageNum, Integer pageSize, HttpServletRequest request) {
        PageHelper.startPage(pageNum, pageSize);
        List<UserInfoVo> all = userInfoDao.findByName(name);
        return PageInfo.of(all);
    }

    public UserInfoVo findByUserName(String name) {
        return userInfoDao.findByUsername(name);
    }

    public UserInfo login(String username, String password) {
        UserInfo userInfo = userInfoDao.findByUsername(username);
        if (userInfo == null) {
            throw new CustomException(ResultCode.USER_ACCOUNT_ERROR);
        }
        if (!SecureUtil.md5(password).equalsIgnoreCase(userInfo.getPassword())) {
            throw new CustomException(ResultCode.USER_ACCOUNT_ERROR);
        }
        return userInfo;
    }

}
