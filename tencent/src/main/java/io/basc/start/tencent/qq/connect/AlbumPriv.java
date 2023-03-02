package io.basc.start.tencent.qq.connect;

import java.io.Serializable;

/**
 * 相册权限，其取值含义为： 1=公开；3=只主人可见； 4=QQ好友可见； 5=问答加密。 
 * 不传则相册默认为公开权限。
 * 如果priv取值为5，即相册是问答加密的，则必须包含问题和答案两个参数：
 * -question: 问题，不能超过30个字符。
 * -answer: 答案，不能超过30个字符。
 * @author shuchaowen
 *
 */
public class AlbumPriv implements Serializable{
	private static final long serialVersionUID = 1L;
	private final int priv;
	private String question;
	private String answer;
	
	public AlbumPriv(int priv){
		this.priv = priv;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getPriv() {
		return priv;
	}
}
