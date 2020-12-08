package com.feedco.note.Models.SmsModel;

import com.google.gson.annotations.SerializedName;

public class GsmItem{

	@SerializedName("msidn")
	private String msidn;

	@SerializedName("msgid")
	private String msgid;

	public void setMsidn(String msidn){
		this.msidn = msidn;
	}

	public String getMsidn(){
		return msidn;
	}

	public void setMsgid(String msgid){
		this.msgid = msgid;
	}

	public String getMsgid(){
		return msgid;
	}
}