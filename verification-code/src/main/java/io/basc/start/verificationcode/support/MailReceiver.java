package io.basc.start.verificationcode.support;

public class MailReceiver extends SimpleReceiver {
	private static final long serialVersionUID = 1L;

	public MailReceiver() {
	}

	public MailReceiver(String mail) {
		super(mail);
	}
	
	public MailReceiver(String mail, String tag) {
		super(mail, tag);
	}
}