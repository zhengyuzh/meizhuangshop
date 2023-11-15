package com.example.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.example.common.Result;
import com.example.common.ResultCode;
import com.example.entity.Account;
import com.example.entity.AuthorityInfo;
import com.example.exception.CustomException;
import com.example.entity.AdminInfo;
import com.example.entity.UserInfo;

import com.example.service.AdminInfoService;
import com.example.service.UserInfoService;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import cn.hutool.json.JSONUtil;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class AccountController {

    @Value("${authority.info}")
    private String authorityStr;

	@Resource
	private AdminInfoService adminInfoService;
	@Resource
	private UserInfoService userInfoService;


    @PostMapping("/login")
    public Result<Account> login(@RequestBody Account account, HttpServletRequest request) {
        if (StrUtil.isBlank(account.getName()) || StrUtil.isBlank(account.getPassword()) || account.getLevel() == null) {
            throw new CustomException(ResultCode.PARAM_LOST_ERROR);
        }
        Integer level = account.getLevel();
        Account login = new Account();
		if (1 == level) {
			login = adminInfoService.login(account.getName(), account.getPassword());
		}
		if (3 == level) {
			login = userInfoService.login(account.getName(), account.getPassword());
		}

        request.getSession().setAttribute("user", login);
        return Result.success(login);
    }

    @PostMapping("/register")
    public Result<Account> register(@RequestBody Account account) {
        Integer level = account.getLevel();
        Account login = new Account();
		if (1 == level) {
			AdminInfo info = new AdminInfo();
			BeanUtils.copyProperties(account, info);
			info.setAccount(0D);
			login = adminInfoService.add(info);
		}
		if (3 == level) {
			UserInfo info = new UserInfo();
			BeanUtils.copyProperties(account, info);
            info.setAccount(0D);
			login = userInfoService.add(info);
		}

        return Result.success(login);
    }

    @GetMapping("/logout")
    public Result logout(HttpServletRequest request) {
        request.getSession().setAttribute("user", null);
        return Result.success();
    }

    @GetMapping("/auth")
    public Result getAuth(HttpServletRequest request) {
        Object user = request.getSession().getAttribute("user");
        if(user == null) {
            return Result.error("401", "未登录");
        }
        return Result.success(user);
    }

    @GetMapping("/getAccountInfo")
    public Result<Object> getAccountInfo(HttpServletRequest request) {
        Account account = (Account) request.getSession().getAttribute("user");
        if (account == null) {
            return Result.success(new Object());
        }
        Integer level = account.getLevel();
		if (1 == level) {
			return Result.success(adminInfoService.findById(account.getId()));
		}
		if (3 == level) {
			return Result.success(userInfoService.findById(account.getId()));
		}

        return Result.success(new Object());
    }

    @GetMapping("/getSession")
    public Result<Map<String, String>> getSession(HttpServletRequest request) {
        Account account = (Account) request.getSession().getAttribute("user");
        if (account == null) {
            return Result.success(new HashMap<>(1));
        }
        Map<String, String> map = new HashMap<>(1);
        map.put("username", account.getName());
        return Result.success(map);
    }

    @GetMapping("/getAuthority")
    public Result<List<AuthorityInfo>> getAuthorityInfo() {
        List<AuthorityInfo> authorityInfoList = JSONUtil.toList(JSONUtil.parseArray(authorityStr), AuthorityInfo.class);
        return Result.success(authorityInfoList);
    }

    /**
    * 获取当前用户所能看到的模块信息
    * @param request
    * @return
    */
    @GetMapping("/authority")
    public Result<List<Integer>> getAuthorityInfo(HttpServletRequest request) {
        Account user = (Account) request.getSession().getAttribute("user");
        if (user == null) {
            return Result.success(new ArrayList<>());
        }
        JSONArray objects = JSONUtil.parseArray(authorityStr);
        for (Object object : objects) {
            JSONObject jsonObject = (JSONObject) object;
            if (user.getLevel().equals(jsonObject.getInt("level"))) {
                JSONArray array = JSONUtil.parseArray(jsonObject.getStr("models"));
                List<Integer> modelIdList = array.stream().map((o -> {
                    JSONObject obj = (JSONObject) o;
                    return obj.getInt("modelId");
                    })).collect(Collectors.toList());
                return Result.success(modelIdList);
            }
        }
        return Result.success(new ArrayList<>());
    }

    @GetMapping("/permission/{modelId}")
    public Result<List<Integer>> getPermission(@PathVariable Integer modelId, HttpServletRequest request) {
        List<AuthorityInfo> authorityInfoList = JSONUtil.toList(JSONUtil.parseArray(authorityStr), AuthorityInfo.class);
        Account user = (Account) request.getSession().getAttribute("user");
        if (user == null) {
            return Result.success(new ArrayList<>());
        }
        Optional<AuthorityInfo> optional = authorityInfoList.stream().filter(x -> x.getLevel().equals(user.getLevel())).findFirst();
        if (optional.isPresent()) {
            Optional<AuthorityInfo.Model> firstOption = optional.get().getModels().stream().filter(x -> x.getModelId().equals(modelId)).findFirst();
            if (firstOption.isPresent()) {
                List<Integer> info = firstOption.get().getOperation();
                return Result.success(info);
            }
        }
        return Result.success(new ArrayList<>());
    }

    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody Account info, HttpServletRequest request) {
        Account account = (Account) request.getSession().getAttribute("user");
        if (account == null) {
            return Result.error(ResultCode.USER_NOT_EXIST_ERROR.code, ResultCode.USER_NOT_EXIST_ERROR.msg);
        }
        String oldPassword = SecureUtil.md5(info.getPassword());
        if (!oldPassword.equals(account.getPassword())) {
            return Result.error(ResultCode.PARAM_PASSWORD_ERROR.code, ResultCode.PARAM_PASSWORD_ERROR.msg);
        }
        info.setPassword(SecureUtil.md5(info.getNewPassword()));
        Integer level = account.getLevel();
		if (1 == level) {
			AdminInfo adminInfo = new AdminInfo();
			BeanUtils.copyProperties(info, adminInfo);
			adminInfoService.update(adminInfo);
		}
		if (3 == level) {
			UserInfo userInfo = new UserInfo();
			BeanUtils.copyProperties(info, userInfo);
			userInfoService.update(userInfo);
		}

        info.setLevel(level);
        info.setName(account.getName());
        // 清空session，让用户重新登录
        request.getSession().setAttribute("user", null);
        return Result.success();
    }

    @PostMapping("/resetPassword")
    public Result resetPassword(@RequestBody Account account) {
        Integer level = account.getLevel();
		if (1 == level) {
			AdminInfo info = adminInfoService.findByUserName(account.getName());
			if (info == null) {
				return Result.error(ResultCode.USER_NOT_EXIST_ERROR.code, ResultCode.USER_NOT_EXIST_ERROR.msg);
			}
			info.setPassword(SecureUtil.md5("123456"));
			adminInfoService.update(info);
		}
		if (3 == level) {
			UserInfo info = userInfoService.findByUserName(account.getName());
			if (info == null) {
				return Result.error(ResultCode.USER_NOT_EXIST_ERROR.code, ResultCode.USER_NOT_EXIST_ERROR.msg);
			}
			info.setPassword(SecureUtil.md5("123456"));
			userInfoService.update(info);
		}

        return Result.success();
    }
}
