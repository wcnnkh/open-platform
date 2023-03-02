package io.basc.start.tencent.wx.pay;

import io.basc.framework.json.JsonObject;

public class UnifiedorderResponse extends WeiXinPayResponse {

	public UnifiedorderResponse(JsonObject target) {
		super(target);
	}

	public TradeType getTradeType() {
		return (TradeType) getAsEnum("trade_type", TradeType.class);
	}

	public String getRawTradeType() {
		return getAsString("trade_type");
	}

	/**
	 * 微信生成的预支付会话标识，用于后续接口调用中使用，该值有效期为2小时
	 * 
	 * @return 预支付会话标识
	 */
	public String getPrepayId() {
		return getAsString("prepay_id");
	}
}
