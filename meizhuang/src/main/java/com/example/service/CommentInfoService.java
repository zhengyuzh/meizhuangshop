package com.example.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.example.dao.CommentInfoDao;
import com.example.entity.Account;
import com.example.entity.CommentInfo;
import com.example.entity.GoodsInfo;
import com.example.exception.CustomException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class CommentInfoService {

    @Resource
    private CommentInfoDao commentInfoDao;
    @Resource
    private GoodsInfoService goodsInfoService;
	@Resource
	private AdminInfoService adminInfoService;
	@Resource
	private UserInfoService userInfoService;


    public CommentInfo add(CommentInfo commentInfo) {
        commentInfo.setCreateTime(DateUtil.formatDateTime(new Date()));
        String content = commentInfo.getContent();
        if (content.length() > 255) {
            commentInfo.setContent(content.substring(0, 250));
        }
        commentInfoDao.insertSelective(commentInfo);
        return commentInfo;
    }

    public void delete(Long id) {
        commentInfoDao.deleteByPrimaryKey(id);
    }

    public void update(CommentInfo commentInfo) {
        String content = commentInfo.getContent();
        if (content.length() > 255) {
            commentInfo.setContent(content.substring(0, 250));
        }
        commentInfoDao.updateByPrimaryKeySelective(commentInfo);
    }

    public CommentInfo findById(Long id) {
        return commentInfoDao.selectByPrimaryKey(id);
    }

    public List<CommentInfo> findAll() {
        return commentInfoDao.selectAll();
    }

    public List<CommentInfo> findAll(Long goodsId) {
        List<CommentInfo> list = commentInfoDao.findByGoodsId(goodsId);
        if (!CollectionUtil.isEmpty(list)) {
            for (CommentInfo info : list) {
                Long userId = info.getUserId();
                Integer level = info.getLevel();
				if (level == 1) {
					info.setUserName(adminInfoService.findById(userId).getName());
				}
				if (level == 3) {
					info.setUserName(userInfoService.findById(userId).getName());
				}

            }
        }
        return list;
    }

    public PageInfo<CommentInfo> findPage(Integer pageNum, Integer pageSize, String name, HttpServletRequest request) {
        PageHelper.startPage(pageNum, pageSize);
        Account user = (Account)request.getSession().getAttribute("user");
        if (user == null) {
            throw new CustomException("1001", "用户未登录");
        }
        List<CommentInfo> all;
        if (user.getLevel() == 1) {
            all = commentInfoDao.selectAll();
        } else {
            all = commentInfoDao.findByContent(name, user.getId(), user.getLevel());
        }
        for (CommentInfo info : all) {
            Long userId = info.getUserId();
            Integer level = info.getLevel();
            GoodsInfo goodsInfo = goodsInfoService.findById(info.getGoodsId());
            info.setGoodsName(goodsInfo.getName());
				if (level == 1) {
					info.setUserName(adminInfoService.findById(userId).getName());
				}
				if (level == 3) {
					info.setUserName(userInfoService.findById(userId).getName());
				}

        }

        return PageInfo.of(all);
    }

}
