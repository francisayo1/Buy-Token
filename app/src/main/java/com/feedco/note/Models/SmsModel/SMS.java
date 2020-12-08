package com.feedco.note.Models.SmsModel;

import com.google.gson.annotations.SerializedName;

public class SMS{

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	String errorMessage;
	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	int errorCode;

	@SerializedName("SMS")
	private SMS sMS;

	@SerializedName("dndsender")
	private int dndsender;

	@SerializedName("auth")
	private Auth auth;

	@SerializedName("recipients")
	private Recipients recipients;

	@SerializedName("message")
	private Message message;

	public void setSMS(SMS sMS){
		this.sMS = sMS;
	}

	public SMS getSMS(){
		return sMS;
	}

	public void setDndsender(int dndsender){
		this.dndsender = dndsender;
	}

	public int getDndsender(){
		return dndsender;
	}

	public void setAuth(Auth auth){
		this.auth = auth;
	}

	public Auth getAuth(){
		return auth;
	}

	public void setRecipients(Recipients recipients){
		this.recipients = recipients;
	}

	public Recipients getRecipients(){
		return recipients;
	}

	public void setMessage(Message message){
		this.message = message;
	}

	public Message getMessage(){
		return message;
	}
}