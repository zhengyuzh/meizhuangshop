package com.example.service;

import com.example.dao.MessageInfoDao;
import com.example.entity.MessageInfo;
import com.example.vo.MessageInfoVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageInfoService {

    @Resource
    private MessageInfoDao messageInfoDao;

    public MessageInfo add(MessageInfo messageInfo) {
        messageInfoDao.insertSelective(messageInfo);
        return messageInfo;
    }

    public void delete(Long id) {
        messageInfoDao.deleteByPrimaryKey(id);
    }

    public void update(MessageInfo messageInfo) {
        messageInfoDao.updateByPrimaryKeySelective(messageInfo);
    }

    public MessageInfo findById(Long id) {
        return messageInfoDao.selectByPrimaryKey(id);
    }

    public List<MessageInfoVo> findAll() {
        List<MessageInfoVo> all = messageInfoDao.findByParentId(0L);
        for (MessageInfoVo messageInfoVo : all) {
            Long id = messageInfoVo.getId();
            List<MessageInfoVo> children = new ArrayList<>(messageInfoDao.findByParentId(id));
            messageInfoVo.setChildren(children);
        }
        return all;
    }

    public PageInfo<MessageInfoVo> findPage(String name, Integer pageNum, Integer pageSize, HttpServletRequest request) {
        PageHelper.startPage(pageNum, pageSize);
        List<MessageInfoVo> all = findAllPage(request, name);
        return PageInfo.of(all);
    }

    public List<MessageInfoVo> findAllPage(HttpServletRequest request, String name) {
		return messageInfoDao.findByName(name);
    }

}
