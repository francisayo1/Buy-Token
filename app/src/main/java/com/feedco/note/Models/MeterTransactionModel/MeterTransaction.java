package com.feedco.note.Models.MeterTransactionModel;

import com.google.gson.annotations.SerializedName;

public class MeterTransaction {

	/*public String getResponse_description() {
		return response_description;
	}

	public void setResponse_description(String response_description) {
		this.response_description = response_description;
	}*/

	/*@SerializedName ( "response_description" )
	String response_description;*/

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	private String errorMessage;
	@SerializedName("transaction_date")
	private TransactionDate transactionDate;

	@SerializedName("response_description")
	private String responseDescription;

	@SerializedName("amount")
	private int amount;

	public void setCode(String code) {
		this.code = code;
	}

	@SerializedName("code")
	private String code;

	@SerializedName("address")
	private Object address;

	@SerializedName("meterNumber")
	private String meterNumber;

	@SerializedName("tokenAmount")
	private String tokenAmount;

	@SerializedName("businessCenter")
	private Object businessCenter;

	@SerializedName("units")
	private String units;

	@SerializedName("purchased_code")
	private String purchasedCode;

	@SerializedName("content")
	private Content content;

	@SerializedName("customerName")
	private Object customerName;

	@SerializedName("token")
	private String token;

	@SerializedName("requestId")
	private String requestId;

	@SerializedName("exchangeReference")
	private String exchangeReference;

	@SerializedName("tokenValue")
	private String tokenValue;

	public TransactionDate getTransactionDate(){
		return transactionDate;
	}

	public String getResponseDescription(){
		return responseDescription;
	}

	public int getAmount(){
		return amount;
	}

	public String getCode(){
		return code;
	}

	public Object getAddress(){
		return address;
	}

	public String getMeterNumber(){
		return meterNumber;
	}

	public String getTokenAmount(){
		return tokenAmount;
	}

	public Object getBusinessCenter(){
		return businessCenter;
	}

	public String getUnits(){
		return units;
	}

	public String getPurchasedCode(){
		return purchasedCode;
	}

	public Content getContent(){
		return content;
	}

	public Object getCustomerName(){
		return customerName;
	}

	public String getToken(){
		return token;
	}

	public String getRequestId(){
		return requestId;
	}

	public String getExchangeReference(){
		return exchangeReference;
	}

	public String getTokenValue(){
		return tokenValue;
	}
}