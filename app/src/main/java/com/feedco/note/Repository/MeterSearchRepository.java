package com.feedco.note.Repository;

import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.feedco.note.Models.MeterSearchModel.Content;
import com.feedco.note.Models.MeterSearchModel.MeterSearchModel;
import com.feedco.note.Resource.MeterSearchResource;
import com.feedco.note.Retrofit.MeterSeachApi;

import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;

public class MeterSearchRepository {
    MeterSeachApi meterSeachApi;

    //private MediatorLiveData <MeterSearchResource <MeterSearchModel>> mediatorLiveData = new MediatorLiveData <> ( );
    private MediatorLiveData <MeterSearchResource <MeterSearchModel>> mediatorLiveData = new MediatorLiveData <> ( );

    private MutableLiveData <MeterSearchResource <MeterSearchModel>> liveData = new MutableLiveData <> ( );


    @Inject
    public MeterSearchRepository(MeterSeachApi meterSeachApi) {
        this.meterSeachApi = meterSeachApi;
        if (meterSeachApi == null) {
            System.out.println ( "Null repo" );
        } else System.out.println ( "cool done" );


    }

    public void requestMeter(String billerCode, String serviceId, String type) {
        mediatorLiveData.setValue ( MeterSearchResource.loading ( (MeterSearchModel) null ) );
        final LiveData <MeterSearchResource <MeterSearchModel>> userData = LiveDataReactiveStreams.
                fromPublisher ( meterSeachApi.getUserMeterDetails ( billerCode, serviceId, type )
                        .onErrorReturn ( (throwable) -> {



                            MeterSearchModel meterSearchModel = new MeterSearchModel ( );
                            Content content = new Content ( null );
                            content.setMinPurchaseAmount ( -1 );
                            content.setMeterNumber ( throwable.getLocalizedMessage ( ) );
                            meterSearchModel.setContent ( content );

                            return meterSearchModel;
                        } )

                        .map ( meterSearchModel -> {

                                    //System.out.println ( "Inside map" );
                                    if (!TextUtils.isEmpty ( meterSearchModel.getContent ( ).getError ( ) ) || meterSearchModel.getContent ( ).getMinPurchaseAmount ( ) == -1) {
                                        System.out.println ("Inside error" );
                                        return MeterSearchResource.error ( "Some error has occurred", meterSearchModel );
                                    }
                                    return MeterSearchResource.authenticated ( meterSearchModel );

                                }


                        ).subscribeOn ( Schedulers.io ( ) ) );

        mediatorLiveData.addSource ( userData, meterSearchResource -> {
          //  System.out.println ("adding source..." );
            mediatorLiveData.setValue ( meterSearchResource );

            mediatorLiveData.removeSource ( userData );
        } );

    }

    public LiveData <MeterSearchResource <MeterSearchModel>> getUserMeterDetails() {
        //System.out.println ("Under Repo..." );

        return mediatorLiveData;
    }
/*
    public void getMeterDetails(String billerCode, String serviceId, String type) {
        Call <MeterSearchModel> call = meterSeachApi.getUserMeterDetails ( billerCode, serviceId, type );

        call.enqueue ( new Callback <MeterSearchModel> ( ) {
            @Override
            public void onResponse(Call <MeterSearchModel> call, Response <MeterSearchModel> response) {

                mediatorLiveData.setValue ( MeterSearchResource.authenticated ( response.body ( ) ) );
                *//*mediatorLiveData.addSource ( liveData, new Observer <MeterSearchResource <MeterSearchModel>> ( ) {
                    @Override
                    public void onChanged(MeterSearchResource <MeterSearchModel> meterSearchModelMeterSearchResource) {
                        mediatorLiveData.setValue ( MeterSearchResource.authenticated ( response.body ( ) ) );
                        mediatorLiveData.removeSource ( liveData );
                    }
                } );*//*
                //mediatorLiveData.postValue ( MeterSearchResource.authenticated ( response.body ( ) ));
            }

            @Override
            public void onFailure(Call <MeterSearchModel> call, Throwable t) {
                System.out.println ( "error " + t.getLocalizedMessage ( ) );
            }
        } );

    }

    */
}
