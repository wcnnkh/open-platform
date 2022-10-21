package io.basc.start.apple.pay;

import io.basc.framework.json.JsonObject;

public class LatestReceiptInfo extends InApp {

	public LatestReceiptInfo(JsonObject target) {
		super(target);
	}

	/**
	 * 指示由于升级已取消订阅的指示器。该字段仅在升级交易中存在。值： true
	 * 
	 * @return
	 */
	public boolean isUpgraded() {
		return getAsBoolean("is_upgraded");
	}

	/**
	 * 订阅所属的订阅组的标识符。该字段的值与SKProduct中的属性相同。
	 * 
	 * @return
	 */
	public String getSubscriptionGroupIdentifier() {
		return getAsString("subscription_group_identifier");
	}
}
