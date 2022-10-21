package io.basc.start.tencent.qq.connect;

import io.basc.framework.json.JsonObject;
import io.basc.framework.json.JsonObjectWrapper;

public class VipRichInfoItem extends JsonObjectWrapper {
	private final String prefix;

	public VipRichInfoItem(JsonObject target, String prefix) {
		super(target);
		this.prefix = prefix;
	}

	/**
	 * 最后一次充值时间
	 * 
	 * @return
	 */
	public long getStart() {
		return getAsLong(prefix + "_start");
	}

	/**
	 * 会员期限
	 * 
	 * @return
	 */
	public long getEnd() {
		return getAsLong(prefix + "_end");
	}

	/**
	 * 充值方式
	 * 
	 * @return
	 */
	public int getPayway() {
		return getAsInt(prefix + "_payway");
	}
}
