package com.feedco.note.Models.MeterTransactionModel;

import com.google.gson.annotations.SerializedName;

public class Content{

	@SerializedName("transactions")
	private Transactions transactions;

	public Transactions getTransactions(){
		return transactions;
	}
}