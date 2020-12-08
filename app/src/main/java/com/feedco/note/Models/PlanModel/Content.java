package com.feedco.note.Models.PlanModel;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Content{

	@SerializedName("convinience_fee")
	private String convinienceFee;

	@SerializedName("ServiceName")
	private String serviceName;

	@SerializedName("varations")
	private List<VarationsItem> varations;

	@SerializedName("serviceID")
	private String serviceID;

	public String getConvinienceFee(){
		return convinienceFee;
	}

	public String getServiceName(){
		return serviceName;
	}

	public List<VarationsItem> getVarations(){
		return varations;
	}

	public String getServiceID(){
		return serviceID;
	}
}