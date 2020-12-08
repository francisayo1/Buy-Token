package com.feedco.note.Models.Tables;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Tbl_transaction")
public class MeterTable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String token;
    private String address;
    private String time;
    private String date;
    private String meterType;
    private String reference;
    private String transactionId;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMeterType() {
        return meterType;
    }

    public void setMeterType(String meterType) {
        this.meterType = meterType;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    private String amount;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
