package com.feedco.note.Models.PlanModel;

import com.google.gson.annotations.SerializedName;

public class Plans{

	@SerializedName("response_description")
	private String responseDescription;

	@SerializedName("content")
	private Content content;

	public void setContent(Content content) {
		this.content = content;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	private int responseCode;
	public String getResponseDescription(){
		return responseDescription;
	}

	public Content getContent(){
		return content;
	}

	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}
}