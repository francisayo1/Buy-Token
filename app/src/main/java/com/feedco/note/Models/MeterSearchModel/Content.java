package com.feedco.note.Models.MeterSearchModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Content implements Parcelable {

    public Content(Parcel in) {
        mAXPurchaseAmount = in.readString ( );
        meterNumber = in.readString ( );
        lastPurchaseDays = in.readString ( );
        customerName = in.readString ( );
        address = in.readString ( );
        customerPhone = in.readString ( );
        minPurchaseAmount = in.readInt ( );
        customerArrears = in.readString ( );
        meterType = in.readString ( );
        error = in.readString ( );
    }

    public static final Creator <Content> CREATOR = new Creator <Content> ( ) {
        @Override
        public Content createFromParcel(Parcel in) {
            return new Content ( in );
        }

        @Override
        public Content[] newArray(int size) {
            return new Content[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString ( mAXPurchaseAmount );
        dest.writeString ( meterNumber );
        dest.writeString ( lastPurchaseDays );
        dest.writeString ( customerName );
        dest.writeString ( address );
        dest.writeString ( customerPhone );
        dest.writeInt ( minPurchaseAmount );
        dest.writeString ( customerArrears );
        dest.writeString ( meterType );
        dest.writeString ( error );
    }

    public void setmAXPurchaseAmount(String mAXPurchaseAmount) {
        this.mAXPurchaseAmount = mAXPurchaseAmount;
    }

    public void setMeterNumber(String meterNumber) {
        this.meterNumber = meterNumber;
    }

    public void setLastPurchaseDays(String lastPurchaseDays) {
        this.lastPurchaseDays = lastPurchaseDays;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public void setMinPurchaseAmount(int minPurchaseAmount) {
        this.minPurchaseAmount = minPurchaseAmount;
    }

    public void setCustomerArrears(String customerArrears) {
        this.customerArrears = customerArrears;
    }

    public void setMeterType(String meterType) {
        this.meterType = meterType;
    }

    @SerializedName("MAX_Purchase_Amount")
    private String mAXPurchaseAmount;

    @SerializedName("MeterNumber")
    private String meterNumber;

    @SerializedName("Last_Purchase_Days")
    private String lastPurchaseDays;

    @SerializedName("Customer_Name")
    private String customerName;

    @SerializedName("Address")
    private String address;

    @SerializedName("Customer_Phone")
    private String customerPhone;

    @SerializedName("Min_Purchase_Amount")
    private int minPurchaseAmount;

    @SerializedName("Customer_Arrears")
    private String customerArrears;

    @SerializedName("Meter_Type")
    private String meterType;

    public String getMAXPurchaseAmount() {
        return mAXPurchaseAmount;
    }

    public String getMeterNumber() {
        return meterNumber;
    }

    public String getLastPurchaseDays() {
        return lastPurchaseDays;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAddress() {
        return address;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public int getMinPurchaseAmount() {
        return minPurchaseAmount;
    }

    public String getCustomerArrears() {
        return customerArrears;
    }

    public String getMeterType() {
        return meterType;
    }

    public String error;

    public String getmAXPurchaseAmount() {
        return mAXPurchaseAmount;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }


}
