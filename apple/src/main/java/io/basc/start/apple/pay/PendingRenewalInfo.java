package io.basc.start.apple.pay;

import io.basc.framework.json.JsonObject;
import io.basc.framework.json.JsonObjectWrapper;

/**
 * <a href=
 * "https://developer.apple.com/documentation/appstorereceipts/responsebody/pending_renewal_info">文档</a>
 * 
 * @author wcnnkh
 *
 */
public class PendingRenewalInfo extends JsonObjectWrapper {

	public PendingRenewalInfo(JsonObject target) {
		super(target);
	}

	/**
	 * 自动续订订阅的当前续订首选项。该密钥的值对应于客户订阅续订的产品的属性。仅当用户降级或交叉降级到下一个订阅期的持续时间不同的订阅时，此字段才存在
	 * 
	 * @return 产品
	 */
	public String getAutoRenewProductId() {
		return getAsString("auto_renew_product_id");
	}

	/**
	 * 自动续订的续订状态
	 * 
	 * <a href=
	 * "https://developer.apple.com/documentation/appstorereceipts/auto_renew_status">文档</a>
	 * 
	 * @return 1 订阅将在当前订阅期结束时续订。
	 * 
	 *         0 客户已关闭订阅的自动续订。
	 */
	public int getAutoRenewStatus() {
		return getAsInt("auto_renew_status");
	}

	/**
	 * 订阅过期的原因
	 * 
	 * <a href=
	 * "https://developer.apple.com/documentation/appstorereceipts/expiration_intent">文档</a>
	 * 
	 * @return 1 客户自愿取消订阅。 2 帐单错误；例如，客户的付款信息不再有效。 3 客户不同意最近的提价。 4 续订时无法购买该产品。 5
	 *         未知错误。
	 */
	public int getExpirationIntent() {
		return getAsInt("expiration_intent");
	}

	/**
	 * 续订宽限期的到期时间
	 * 
	 * @return
	 */
	public ApplePayDate getGracePeriodExpiresDate() {
		return new ApplePayDate(this, "grace_period_expires_date");
	}

	/**
	 * 指示自动续订的订阅是否在计费重试期内。仅当自动续订的订阅处于计费重试状态时，此字段才存在 true App Store正在尝试续订。 false App
	 * Store已停止尝试续订。
	 * 
	 * <a href=
	 * "https://developer.apple.com/documentation/appstorereceipts/is_in_billing_retry_period">文档</a>
	 * 
	 * @return yes/no
	 */
	public boolean isInBillingRetryPeriod() {
		return getAsBoolean("is_in_billing_retry_period");
	}

	/**
	 * 原始购买的交易标识符。
	 * 
	 * @return 原始购买的交易标识符。
	 */
	public String getOriginalTransactionId() {
		return getAsString("original_transaction_id");
	}

	/**
	 * 订阅价格上涨的价格同意状态。仅当通知客户提价后，才显示此字段。如果客户同意，默认值为，"0"并且更改为"1"。 可能的值： 1, 0
	 * 
	 * @return 订阅价格上涨的价格同意状态
	 */
	public int getPriceConsentStatus() {
		return getAsInt("price_consent_status");
	}

	/**
	 * 购买产品的唯一标识符。您可以在App Store Connect中创建产品时提供此值，它对应于存储在交易的付款属性中的对象的属性
	 * 
	 * @return 产品的唯一标识符
	 */
	public String getProductId() {
		return getAsString("product_id");
	}
}
