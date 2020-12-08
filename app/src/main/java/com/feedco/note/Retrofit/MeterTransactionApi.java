package com.feedco.note.Retrofit;

import com.feedco.note.Models.MeterTransactionModel.MeterTransaction;
import com.feedco.note.Models.SmsModel.SMS;
import com.feedco.note.Models.SmsModel.SMSBody;
import com.feedco.note.Models.SmsModel.SmsResponse;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface MeterTransactionApi {

   /* @FormUrlEncoded
    @POST("pay")
    Flowable<MeterTransaction>publishTransaction(@Field("request_id") String requestId,
                                                 @Field("amount") Number amount,
                                                 @Field("phone") Number phone,
                                                 @Field("billersCode") String billerCode,
                                                 @Field("serviceID") String serviceId,
                                                 @Field("variation_code") String type);*/


    @FormUrlEncoded
    @POST("pay")
    Flowable<MeterTransaction>publishTransaction(@Field("request_id") String requestId,
                                                 @Field("serviceID") String serviceId,
                                                 @Field("billersCode") String billerCode,
                                                 @Field("variation_code") String type,
                                                 @Field("amount") Number amount,
                                                 @Field("phone") Number phone);

    @FormUrlEncoded
    @POST("pay")
    Flowable<MeterTransaction>publishAirtimeTransaction(@Field("request_id") String requestId,
                                                 @Field("serviceID") String serviceId,
                                                 @Field("amount") Number amount,
                                                 @Field("phone") Number phone);


    @Headers( "Content-Type:application/json" )
    @POST
    Flowable<SmsResponse>sendSmsAfterTransaction(@Url String url, @Body SMSBody sms);

}
