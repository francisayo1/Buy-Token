package com.feedco.note.Models.PlanModel;

import com.google.gson.annotations.SerializedName;

public class VarationsItem{

	@SerializedName("variation_code")
	private String variationCode;

	@SerializedName("name")
	private String name;

	@SerializedName("variation_amount")
	private String variationAmount;

	@SerializedName("fixedPrice")
	private String fixedPrice;

	public String getVariationCode(){
		return variationCode;
	}

	public String getName(){
		return name;
	}

	public String getVariationAmount(){
		return variationAmount;
	}

	public String getFixedPrice(){
		return fixedPrice;
	}
}