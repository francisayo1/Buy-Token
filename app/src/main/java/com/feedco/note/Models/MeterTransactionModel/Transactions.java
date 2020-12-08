package com.feedco.note.Models.MeterTransactionModel;

import com.google.gson.annotations.SerializedName;

public class Transactions{

	@SerializedName("amount")
	private String amount;

	@SerializedName("quantity")
	private int quantity;

	@SerializedName("method")
	private String method;

	@SerializedName("channel")
	private String channel;

	@SerializedName("discount")
	private Object discount;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("is_api")
	private int isApi;

	@SerializedName("type")
	private String type;

	@SerializedName("unit_price")
	private String unitPrice;

	@SerializedName("transactionId")
	private String transactionId;

	@SerializedName("platform")
	private String platform;

	@SerializedName("convinience_fee")
	private int convinienceFee;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("phone")
	private String phone;

	@SerializedName("total_amount")
	private double totalAmount;

	@SerializedName("commission")
	private double commission;

	@SerializedName("id")
	private int id;

	@SerializedName("customer_id")
	private int customerId;

	@SerializedName("email")
	private String email;

	@SerializedName("status")
	private String status;

	public String getAmount(){
		return amount;
	}

	public int getQuantity(){
		return quantity;
	}

	public String getMethod(){
		return method;
	}

	public String getChannel(){
		return channel;
	}

	public Object getDiscount(){
		return discount;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getIsApi(){
		return isApi;
	}

	public String getType(){
		return type;
	}

	public String getUnitPrice(){
		return unitPrice;
	}

	public String getTransactionId(){
		return transactionId;
	}

	public String getPlatform(){
		return platform;
	}

	public int getConvinienceFee(){
		return convinienceFee;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public String getPhone(){
		return phone;
	}

	public double getTotalAmount(){
		return totalAmount;
	}

	public double getCommission(){
		return commission;
	}

	public int getId(){
		return id;
	}

	public int getCustomerId(){
		return customerId;
	}

	public String getEmail(){
		return email;
	}

	public String getStatus(){
		return status;
	}
}