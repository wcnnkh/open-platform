package io.basc.start.tencent.qq.connect;

import io.basc.framework.json.JsonObject;
import io.basc.framework.json.JsonObjectWrapper;

public class QQResponse extends JsonObjectWrapper {

	public QQResponse(JsonObject target) {
		super(target);
	}

	/**
	 * 返回码
	 * 
	 * @return
	 */
	public int getRet() {
		return getAsInt("ret");
	}

	/**
	 * 如果ret<0，会有相应的错误信息提示，返回数据全部用UTF-8编码。
	 * 
	 * @return
	 */
	public String getMsg() {
		return getAsString("msg");
	}

	public boolean isSuccess() {
		return getRet() == 0;
	}

	public boolean isError() {
		return !isSuccess();
	}
}
