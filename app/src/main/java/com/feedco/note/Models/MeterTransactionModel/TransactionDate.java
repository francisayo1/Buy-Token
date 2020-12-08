package com.feedco.note.Models.MeterTransactionModel;

import com.google.gson.annotations.SerializedName;

public class TransactionDate{

	@SerializedName("date")
	private String date;

	@SerializedName("timezone")
	private String timezone;

	@SerializedName("timezone_type")
	private int timezoneType;

	public String getDate(){
		return date;
	}

	public String getTimezone(){
		return timezone;
	}

	public int getTimezoneType(){
		return timezoneType;
	}
}