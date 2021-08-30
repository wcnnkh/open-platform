package io.basc.start.web;

import io.basc.framework.beans.annotation.Autowired;
import io.basc.framework.context.result.DataResult;
import io.basc.framework.context.result.Result;
import io.basc.framework.context.result.ResultFactory;
import io.basc.framework.http.HttpMethod;
import io.basc.framework.mvc.HttpChannel;
import io.basc.framework.mvc.annotation.Controller;
import io.basc.framework.oauth2.AccessToken;
import io.basc.framework.util.StringUtils;
import io.basc.start.enums.SexType;
import io.basc.start.tencent.qq.connect.QQ;
import io.basc.start.tencent.qq.connect.QQRequest;
import io.basc.start.tencent.qq.connect.UserInfoResponse;
import io.basc.start.user.enums.UnionIdType;
import io.basc.start.user.model.UserAttributeModel;
import io.basc.start.user.pojo.User;
import io.basc.start.user.security.LoginRequired;
import io.basc.start.user.security.UserLoginService;
import io.basc.start.user.service.UserService;

import java.util.Map;

@Controller(value = "qq", methods = { HttpMethod.GET, HttpMethod.POST })
@io.basc.framework.mvc.annotation.FactoryResult
public class QQController {
	private QQ qq;
	private UserService userService;
	@Autowired
	private ResultFactory resultFactory;
	@Autowired
	private UserLoginService userControllerService;

	public QQController(UserService userService, QQ qq) {
		this.userService = userService;
		this.qq = qq;
	}

	@Controller(value = "login")
	public Result login(String openid, String accessToken, HttpChannel httpChannel) {
		if (StringUtils.isEmpty(openid, accessToken)) {
			return resultFactory.parameterError();
		}
		
		User user = userService.getUserByUnionId(openid, UnionIdType.QQ_OPENID);
		if (user == null) {
			UserInfoResponse userinfo = qq.getUserinfo(new QQRequest(accessToken, openid));
			UserAttributeModel userAttributeModel = new UserAttributeModel();
			userAttributeModel.setSex(SexType.forDescribe(userinfo.getGender()));
			userAttributeModel.setHeadImg(userinfo.getfigureUrlQQ1());
			userAttributeModel.setNickname(userinfo.getNickname());
			DataResult<User> dataResult = userService.registerUnionId(UnionIdType.QQ_OPENID, openid, null, userAttributeModel);
			if (dataResult.isError()) {
				return dataResult;
			}

			user = dataResult.getData();
		}
		Map<String, Object> infoMap = userControllerService.login(user, httpChannel);
		return resultFactory.success(infoMap);
	}

	@Controller(value = "web_login")
	public Result webLogin(String code, String redirect_uri, HttpChannel httpChannel) {
		if (StringUtils.isEmpty(code, redirect_uri)) {
			return resultFactory.parameterError();
		}

		AccessToken accessToken = qq.getAccessToken(redirect_uri, code);
		String openid = qq.getOpenid(accessToken.getToken().getToken());
		return login(openid, accessToken.getToken().getToken(), httpChannel);
	}

	@LoginRequired
	@Controller(value = "bind")
	public Result bind(long uid, String openid, String accessToken) {
		if (StringUtils.isEmpty(openid, accessToken)) {
			return resultFactory.parameterError();
		}

		User user = userService.getUserByUnionId(openid, UnionIdType.QQ_OPENID);
		if (user == null) {
			return resultFactory.error("用户不存在");
		}
		
		Result result = userService.bindUnionId(uid, UnionIdType.QQ_OPENID, openid);
		if(result.isError()){
			return result;
		}

		UserInfoResponse userinfo = qq.getUserinfo(new QQRequest(accessToken, openid));
		UserAttributeModel userAttributeModel = new UserAttributeModel();
		userAttributeModel.setSex(SexType.forDescribe(userinfo.getGender()));
		userAttributeModel.setHeadImg(userinfo.getfigureUrlQQ1());
		userAttributeModel.setNickname(userinfo.getNickname());
		return userService.updateUserAttribute(uid, userAttributeModel);
	}

	@Controller(value = "web_bind")
	@LoginRequired
	public Result webBind(long uid, String code, String redirect_uri) {
		if (StringUtils.isEmpty(code, redirect_uri)) {
			return resultFactory.parameterError();
		}

		AccessToken accessToken = qq.getAccessToken(redirect_uri, code);
		String openid = qq.getOpenid(accessToken.getToken().getToken());
		return bind(uid, openid, accessToken.getToken().getToken());
	}
}
