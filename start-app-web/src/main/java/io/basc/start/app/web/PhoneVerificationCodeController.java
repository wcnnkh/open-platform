package io.basc.start.app.web;

import java.util.Map;

import io.basc.framework.context.ioc.annotation.Autowired;
import io.basc.framework.context.result.DataResult;
import io.basc.framework.context.result.Result;
import io.basc.framework.context.result.ResultFactory;
import io.basc.framework.http.HttpMethod;
import io.basc.framework.mvc.HttpChannel;
import io.basc.framework.security.session.UserSession;
import io.basc.framework.util.StringUtils;
import io.basc.framework.web.pattern.annotation.RequestMapping;
import io.basc.start.app.enums.VerificationCodeType;
import io.basc.start.app.user.enums.AccountType;
import io.basc.start.app.user.pojo.User;
import io.basc.start.app.user.security.LoginRequired;
import io.basc.start.app.user.security.UserLoginService;
import io.basc.start.app.user.service.UserService;
import io.basc.start.verificationcode.VerificationCodeRecipient;
import io.basc.start.verificationcode.VerificationCodeRequest;
import io.basc.start.verificationcode.VerificationCodeResponse;
import io.basc.start.verificationcode.VerificationCodeService;

@RequestMapping(value = "/phone/code", methods = { HttpMethod.GET, HttpMethod.POST })
public class PhoneVerificationCodeController {
	private final VerificationCodeService verificationCodeService;
	private final UserService userService;
	@Autowired
	private ResultFactory resultFactory;
	@Autowired
	private UserLoginService userControllerService;

	public PhoneVerificationCodeController(VerificationCodeService verificationCodeService, UserService userService) {
		this.verificationCodeService = verificationCodeService;
		this.userService = userService;
	}

	@RequestMapping(value = "send")
	public Result send(String phone, VerificationCodeType type) {
		if (type == null || StringUtils.isEmpty(phone)) {
			return resultFactory.parameterError();
		}

		switch (type) {
		case REGISTER:
		case BIND:
			if (userService.getUserByAccount(AccountType.PHONE, phone) != null) {
				return resultFactory.error("该账号已注册");
			}
			break;
		default:
			break;
		}

		VerificationCodeResponse response = verificationCodeService
				.random(new VerificationCodeRecipient(phone, type.name()));
		if (!response.isSuccess()) {
			return resultFactory.error(response.getMessage());
		}
		return resultFactory.success();
	}

	@RequestMapping(value = "login")
	public Result login(String phone, String code, HttpChannel httpChannel) {
		if (StringUtils.isEmpty(phone, code)) {
			return resultFactory.parameterError();
		}

		VerificationCodeResponse response = verificationCodeService.validate(VerificationCodeRequest.builder()
				.recipient(new VerificationCodeRecipient(phone, VerificationCodeType.LOGIN.name())).code(code).build());
		if (!response.isSuccess()) {
			return resultFactory.error(response.getMessage());
		}

		User user = userService.getUserByAccount(AccountType.PHONE, phone);
		if (user == null) {
			DataResult<User> dataResult = userService.register(AccountType.PHONE, phone, null, null);
			if (dataResult.isError()) {
				return dataResult;
			}

			user = dataResult.getData();
		}

		Map<String, Object> infoMap = userControllerService.login(user, httpChannel);
		return resultFactory.success(infoMap);
	}

	@RequestMapping(value = "update_pwd")
	public Result updatePassword(String phone, String code, String password) {
		if (StringUtils.isEmpty(phone, code, password)) {
			return resultFactory.parameterError();
		}

		VerificationCodeResponse response = verificationCodeService.validate(VerificationCodeRequest.builder()
				.recipient(new VerificationCodeRecipient(phone, VerificationCodeType.UPDATE_PASSWORD.name())).code(code)
				.build());
		if (!response.isSuccess()) {
			return resultFactory.error(response.getMessage());
		}

		User user = userService.getUserByAccount(AccountType.PHONE, phone);
		if (user == null) {
			return resultFactory.error("用户不存在");
		}

		return userService.updatePassword(user.getUid(), password);
	}

	@RequestMapping(value = "bind")
	@LoginRequired
	public Result bind(UserSession<Long> requestUser, String phone, String code) {
		if (StringUtils.isEmpty(phone, code)) {
			return resultFactory.parameterError();
		}

		VerificationCodeResponse response = verificationCodeService.validate(VerificationCodeRequest.builder()
				.recipient(new VerificationCodeRecipient(phone, VerificationCodeType.BIND.name())).code(code).build());
		if (!response.isSuccess()) {
			return resultFactory.error(response.getMessage());
		}

		return userService.bind(requestUser.getUid(), AccountType.PHONE, phone);
	}

	@RequestMapping(value = "register")
	public Result register(String phone, String code, String password) {
		if (StringUtils.isEmpty(phone, code, password)) {
			return resultFactory.parameterError();
		}

		VerificationCodeResponse response = verificationCodeService.validate(VerificationCodeRequest.builder()
				.recipient(new VerificationCodeRecipient(phone, VerificationCodeType.REGISTER.name())).code(code)
				.build());
		if (!response.isSuccess()) {
			return resultFactory.error(response.getMessage());
		}

		return userService.register(AccountType.PHONE, phone, password, null);
	}
}
