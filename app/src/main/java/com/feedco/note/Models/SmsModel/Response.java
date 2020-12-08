package com.feedco.note.Models.SmsModel;

import com.google.gson.annotations.SerializedName;

public class Response{

	@SerializedName("cost")
	private String cost;

	@SerializedName("totalsent")
	private String totalsent;

	@SerializedName("status")
	private String status;

	public String getCost(){
		return cost;
	}

	public String getTotalsent(){
		return totalsent;
	}

	public String getStatus(){
		return status;
	}
}