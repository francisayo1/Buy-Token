package com.feedco.note.Models.MeterSearchModel;

import com.google.gson.annotations.SerializedName;

public class MeterSearchModel{

	@SerializedName("code")
	private String code;

	@SerializedName("content")
	private Content content;

	public void setCode(String code) {
		this.code = code;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public String getCode(){
		return code;
	}

	public Content getContent(){
		return content;
	}
}