package com.feedco.note.Retrofit;

import com.feedco.note.Models.MeterSearchModel.MeterSearchModel;

import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MeterSeachApi {


    @FormUrlEncoded
    @POST("merchant-verify")
    Flowable <MeterSearchModel> getUserMeterDetails(@Field("billersCode") String billerCode, @Field("serviceID") String serviceId,
                                                @Field("type") String type);
   // @FormUrlEncoded
    /*@POST("merchant-verify")
    Flowable <MeterSearchModel> getUserMeterDetails(@Body MeterDetails meterDetails);
*/


    /*@FormUrlEncoded
    @POST("merchant-verify")
    Call<MeterSearchModel> getUserMeterdetails(@Field("billersCode") String billerCode, @Field("serviceID") String serviceId,
                                               @Field("type") String type);
*/    /*@Headers("Content-Type: application/x-www-form-urlencoded")
    //@FormUrlEncoded
    @POST("merchant-verify")
    Call<MeterSearchModel> getUserMeterdetails(@Body MeterDetails meterDetails);*/


}
