package io.basc.start.apple.pay;

import java.io.Serializable;

/**
 * <a href=
 * "https://developer.apple.com/documentation/appstorereceipts/requestbody">文档</a>
 * 
 * @author wcnnkh
 *
 */
public class VerifyReceiptRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private String receiptData;
	private Boolean excludeOldTransactions;

	public VerifyReceiptRequest(String receiptData) {
		this.receiptData = receiptData;
	}

	/**
	 * Base64编码的收据数据。
	 * 
	 * @return 收据数据
	 */
	public String getReceiptData() {
		return receiptData;
	}

	public void setReceiptData(String receiptData) {
		this.receiptData = receiptData;
	}

	/**
	 * 将此值设置为，true以使响应仅包括任何订阅的最新续订交易。仅对包含自动续订的应用收据使用此字段。
	 * 
	 * @return 仅对包含自动续订的应用收据使用此字段。
	 */
	public Boolean getExcludeOldTransactions() {
		return excludeOldTransactions;
	}

	public void setExcludeOldTransactions(Boolean excludeOldTransactions) {
		this.excludeOldTransactions = excludeOldTransactions;
	}
}
