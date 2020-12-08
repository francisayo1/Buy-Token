package com.feedco.note.Models.SmsModel;

import com.google.gson.annotations.SerializedName;

public class Message{

	@SerializedName("sender")
	private String sender;

	@SerializedName("messagetext")
	private String messagetext;

	@SerializedName("flash")
	private String flash;

	public void setSender(String sender){
		this.sender = sender;
	}

	public String getSender(){
		return sender;
	}

	public void setMessagetext(String messagetext){
		this.messagetext = messagetext;
	}

	public String getMessagetext(){
		return messagetext;
	}

	public void setFlash(String flash){
		this.flash = flash;
	}

	public String getFlash(){
		return flash;
	}
}