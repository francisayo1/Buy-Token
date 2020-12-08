package com.feedco.note.Retrofit;

import com.feedco.note.Models.PlanModel.Plans;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public
interface PlanSearchApi {

    @GET("service-variations")
    Flowable<Plans> getPlansData(@Query ( "serviceID" )String type);
}
