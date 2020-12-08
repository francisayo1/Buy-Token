package com.feedco.note.Models.SmsModel;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Recipients{

	@SerializedName("gsm")
	private List<GsmItem> gsm;

	public void setGsm(List<GsmItem> gsm){
		this.gsm = gsm;
	}

	public List<GsmItem> getGsm(){
		return gsm;
	}
}