package io.basc.start.usercenter.controller;

import java.util.Map;

import io.basc.framework.context.ioc.annotation.Autowired;
import io.basc.framework.context.transaction.Result;
import io.basc.framework.context.transaction.ResultFactory;
import io.basc.framework.http.HttpMethod;
import io.basc.framework.mvc.HttpChannel;
import io.basc.framework.security.session.UserSession;
import io.basc.framework.util.StringUtils;
import io.basc.framework.web.message.annotation.RequestBody;
import io.basc.framework.web.pattern.annotation.RequestMapping;
import io.basc.start.usercenter.enums.AccountType;
import io.basc.start.usercenter.model.UserAttributeModel;
import io.basc.start.usercenter.pojo.User;
import io.basc.start.usercenter.security.LoginRequired;
import io.basc.start.usercenter.security.UserLoginService;
import io.basc.start.usercenter.service.UserService;

@RequestMapping(value = "usercenter", methods = { HttpMethod.GET, HttpMethod.POST })
public class UserController {
	private UserService userService;
	@Autowired
	private ResultFactory resultFactory;
	@Autowired
	private UserLoginService userControllerService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "login")
	public Result login(String username, String password, HttpChannel httpChannel) {
		if (StringUtils.isEmptyAny(username, password)) {
			return resultFactory.parameterError();
		}

		User user = userService.getUserByAccount(AccountType.USERNAME, username);
		if (user == null) {
			user = userService.getUserByAccount(AccountType.PHONE, username);
		}

		if (user == null) {
			return resultFactory.error("账号或密码错误");
		}

		Result result = userService.checkPassword(user.getUid(), password);
		if (result.isError()) {
			return result;
		}

		Map<String, Object> infoMap = userControllerService.login(user, httpChannel);
		return resultFactory.success(infoMap);
	}

	@RequestMapping(value = "update")
	@LoginRequired
	public Result updateUserInfo(UserSession<Long> requestUser, @RequestBody UserAttributeModel userAttributeModel) {
		return userService.updateUserAttribute(requestUser.getUid(), userAttributeModel);
	}

	@RequestMapping(value = "register")
	public Result register(String username, String password, @RequestBody UserAttributeModel userAttributeModel) {
		if (StringUtils.isEmptyAny(username, password)) {
			return resultFactory.parameterError();
		}

		return userService.register(AccountType.USERNAME, username, password, userAttributeModel);
	}

	@LoginRequired
	@RequestMapping(value = "info")
	public Result info(UserSession<Long> requestUser) {
		User user = userService.getUser(requestUser.getUid());
		if (user == null) {
			return resultFactory.error("用户不存在");
		}

		Map<String, Object> map = userControllerService.info(user);
		return resultFactory.success(map);
	}
}
