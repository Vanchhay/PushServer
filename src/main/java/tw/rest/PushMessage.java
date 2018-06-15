package tw.rest;

import java.time.Instant;

public class PushMessage {

	String sender, topic, text;
	boolean urgent;
	Instant sendTime;

	PushMessage(){
		Instant now = Instant.now();
		this.sendTime = now;
	}

	public void setSender (String sender) {
		this.sender = sender;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setUrgent(Boolean urgent) {
		this.urgent = urgent;
	}

	public String getSender(){
		return this.sender;
	}

	public String getTopic(){
		return this.topic;
	}

	public String getText(){
		return this.text;
	}

	public boolean getUrgent(){
		return this.urgent;
	}

	public Instant getSendTime(){
		return this.sendTime;
	}
}