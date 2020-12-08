package com.feedco.note.Models.SmsModel;

import com.google.gson.annotations.SerializedName;

public class SMSBody {
    public SMS getsMS() {
        return sMS;
    }

    public void setsMS(SMS sMS) {
        this.sMS = sMS;
    }

    @SerializedName("SMS")
    private SMS sMS;
}
