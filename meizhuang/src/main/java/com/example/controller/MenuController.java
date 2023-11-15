package com.example.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.example.common.Result;
import com.example.entity.Account;
import com.example.service.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class MenuController {

	@Resource
	private AdminInfoService adminInfoService;
	@Resource
	private UserInfoService userInfoService;
	@Resource
	private AdvertiserInfoService advertiserInfoService;


    @GetMapping(value = "/getMenu", produces="application/json;charset=UTF-8")
    public String getMenu(HttpServletRequest request) {
        Account account = (Account) request.getSession().getAttribute("user");
        Integer level;
        if (account == null) {
            level = 1;
        } else {
            level = account.getLevel();
        }
        JSONObject obj = new JSONObject();
        obj.putOpt("code", 0);
        obj.putOpt("msg", "");
        JSONArray dataArray = new JSONArray();

        dataArray.add(getJsonObject("/", "系统首页", "layui-icon-home", "/"));

        JSONObject tableObj = new JSONObject();
        tableObj.putOpt("title", "信息管理");
        tableObj.putOpt("icon", "layui-icon-table");
		if (1 == level) {
			JSONArray array = new JSONArray();
			array.add(getJsonObject("adminInfo", "管理员信息", "layui-icon-table", "adminInfo"));
			array.add(getJsonObject("businessInfo", "卖家信息", "layui-icon-table", "businessInfo"));
			array.add(getJsonObject("userInfo", "用户信息", "layui-icon-table", "userInfo"));
			array.add(getJsonObject("messageInfo", "在线交流", "layui-icon-table", "messageInfo"));
			array.add(getJsonObject("advertiserInfo", "公告信息", "layui-icon-table", "advertiserInfo"));
			array.add(getJsonObject("typeInfo", "化妆品类别", "layui-icon-table", "typeInfo"));
			array.add(getJsonObject("goodsInfo", "化妆品详情", "layui-icon-table", "goodsInfo"));
			array.add(getJsonObject("orderInfo", "订单信息", "layui-icon-table", "orderInfo"));
			array.add(getJsonObject("commentInfo", "评价信息", "layui-icon-table", "commentInfo"));
			array.add(getJsonObject("accountAdminInfo", "个人信息", "layui-icon-user", "accountAdminInfo"));
			tableObj.putOpt("list", array);
		}

		if (2 == level) {
			JSONArray array = new JSONArray();
			array.add(getJsonObject("userInfo", "用户信息", "layui-icon-table", "userInfo"));
			array.add(getJsonObject("messageInfo", "在线交流", "layui-icon-table", "messageInfo"));
			array.add(getJsonObject("advertiserInfo", "公告信息", "layui-icon-table", "advertiserInfo"));
			array.add(getJsonObject("typeInfo", "化妆品类别", "layui-icon-table", "typeInfo"));
			array.add(getJsonObject("goodsInfo", "化妆品详情", "layui-icon-table", "goodsInfo"));
			array.add(getJsonObject("orderInfo", "订单信息", "layui-icon-table", "orderInfo"));
			array.add(getJsonObject("commentInfo", "评价信息", "layui-icon-table", "commentInfo"));
			array.add(getJsonObject("accountBusinessInfo", "个人信息", "layui-icon-user", "accountBusinessInfo"));
			tableObj.putOpt("list", array);
		}

		if (3 == level) {
			JSONArray array = new JSONArray();
			array.add(getJsonObject("messageInfo", "在线交流", "layui-icon-table", "messageInfo"));
			array.add(getJsonObject("advertiserInfo", "公告信息", "layui-icon-table", "advertiserInfo"));
			array.add(getJsonObject("typeInfo", "化妆品类别", "layui-icon-table", "typeInfo"));
			array.add(getJsonObject("goodsInfo", "化妆品详情", "layui-icon-table", "goodsInfo"));
			array.add(getJsonObject("orderInfo", "订单信息", "layui-icon-table", "orderInfo"));
			array.add(getJsonObject("commentInfo", "评价信息", "layui-icon-table", "commentInfo"));
			array.add(getJsonObject("accountUserInfo", "个人信息", "layui-icon-user", "accountUserInfo"));
			tableObj.putOpt("list", array);
		}


        dataArray.add(tableObj);

        dataArray.add(getJsonObject("updatePassword", "修改密码", "layui-icon-password", "updatePassword"));
        dataArray.add(getJsonObject("login", "退出登录", "layui-icon-logout", "login"));

        obj.putOpt("data", dataArray);
        return obj.toString();
    }

    private JSONObject getJsonObject(String name, String title, String icon, String jump) {
        JSONObject object = new JSONObject();
        object.putOpt("name", name);
        object.putOpt("title", title);
        object.putOpt("icon", icon);
        object.putOpt("jump", jump);
        return object;
    }

    @GetMapping(value = "/getTotal", produces="application/json;charset=UTF-8")
    public Result<Map<String, Integer>> getTotle() {
        Map<String, Integer> resultMap = new HashMap<>();
		resultMap.put("adminInfo", adminInfoService.findAll().size());
		resultMap.put("userInfo", userInfoService.findAll().size());
		resultMap.put("advertiserInfo", advertiserInfoService.findAll().size());

        return Result.success(resultMap);
    }
}
