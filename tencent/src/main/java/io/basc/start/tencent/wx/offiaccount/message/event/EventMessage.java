package io.basc.start.tencent.wx.offiaccount.message.event;

import io.basc.start.tencent.wx.offiaccount.message.Message;

public class EventMessage extends Message{
	private static final long serialVersionUID = 1L;
	private String event;
	
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
}