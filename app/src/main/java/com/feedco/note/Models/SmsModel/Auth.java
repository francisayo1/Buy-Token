package com.feedco.note.Models.SmsModel;

import com.google.gson.annotations.SerializedName;

public class Auth{

	@SerializedName("apikey")
	private String apikey;

	@SerializedName("username")
	private String username;

	public void setApikey(String apikey){
		this.apikey = apikey;
	}

	public String getApikey(){
		return apikey;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}
}