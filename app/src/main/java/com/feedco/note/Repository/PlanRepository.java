package com.feedco.note.Repository;

import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;

import com.feedco.note.Models.PlanModel.Plans;
import com.feedco.note.Models.SmsModel.SMS;
import com.feedco.note.Resource.PlanSearchResource;
import com.feedco.note.Retrofit.PlanSearchApi;

import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;

public class PlanRepository {

    PlanSearchApi planSearchApi;

    //private MediatorLiveData <MeterSearchResource <MeterSearchModel>> mediatorLiveData = new MediatorLiveData <> ( );
    private MediatorLiveData <PlanSearchResource <Plans>> mediatorLiveData = new MediatorLiveData <> ( );



    @Inject
    public PlanRepository(PlanSearchApi planSearchApi) {
        this.planSearchApi = planSearchApi;
    }

    public void requestPlans( String type) {
        mediatorLiveData.setValue ( PlanSearchResource.loading ( (Plans) null ) );
        final LiveData <PlanSearchResource <Plans>> planData = LiveDataReactiveStreams.
                fromPublisher ( planSearchApi.getPlansData ( type )
                        .onErrorReturn ( (throwable) -> {



                            Plans plans = new Plans ( );


                            plans.setResponseCode ( -1 );
                            plans.setResponseDescription ( throwable.getMessage () );


                            return plans;
                        } )

                        .map ( plans -> {

                                    //System.out.println ( "Inside map" );
                                    if (plans.getResponseCode ()==-1 ) {

                                        return PlanSearchResource.error ( "Some error has occurred", plans );
                                    }
                                    return PlanSearchResource.success ( plans );

                                }


                        ).subscribeOn ( Schedulers.io ( ) ) );

        mediatorLiveData.addSource ( planData, planSearchResource -> {
            //  System.out.println ("adding source..." );
            mediatorLiveData.setValue ( planSearchResource );

            mediatorLiveData.removeSource ( planData );
        } );

    }

    public LiveData <PlanSearchResource <Plans>> getPlansLiveData() {

        return mediatorLiveData;
    }


}
