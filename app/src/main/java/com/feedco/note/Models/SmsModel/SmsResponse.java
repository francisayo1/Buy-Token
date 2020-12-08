package com.feedco.note.Models.SmsModel;

import com.google.gson.annotations.SerializedName;

public class SmsResponse{

	int errorCode;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	String errorMessage;

	@SerializedName("response")
	private Response response;

	public Response getResponse(){
		return response;
	}
}