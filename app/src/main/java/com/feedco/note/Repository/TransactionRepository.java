package com.feedco.note.Repository;

import android.provider.Telephony;
import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;

import com.feedco.note.Models.MeterSearchModel.MeterSearchModel;
import com.feedco.note.Models.MeterTransactionModel.MeterTransaction;
import com.feedco.note.Models.PlanModel.Plans;
import com.feedco.note.Models.SmsModel.SMS;
import com.feedco.note.Models.SmsModel.SMSBody;
import com.feedco.note.Models.SmsModel.SmsResponse;
import com.feedco.note.Resource.MeterSearchResource;
import com.feedco.note.Resource.PlanSearchResource;
import com.feedco.note.Resource.TransactionResource;
import com.feedco.note.Retrofit.MeterTransactionApi;

import java.math.BigInteger;
import java.text.NumberFormat;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class TransactionRepository {
    private MeterTransactionApi meterTransactionApi;
    private MediatorLiveData <TransactionResource <MeterTransaction>> mediatorLiveData;
    private MediatorLiveData <TransactionResource <SmsResponse>> smsResponseMediatorData;

    @Inject
    public TransactionRepository(MeterTransactionApi meterTransactionApi) {

        this.meterTransactionApi = meterTransactionApi;

        mediatorLiveData = new MediatorLiveData <> ( );
        smsResponseMediatorData = new MediatorLiveData <> ( );

    }

    public void requestTransaction(String rechargeType, String requestId, String service, String billerCode, String variationCode, Number amount, Number phone) {

        Flowable<MeterTransaction> flowable;
        if (rechargeType.equalsIgnoreCase ( "electricity" )) {
            billerCode = variationCode.equalsIgnoreCase ( "prepaid" ) ? "1111111111111" : "1010101010101";

        } else if (rechargeType.equalsIgnoreCase ( "tv" )) {
            billerCode = "1212121212";
        } else if (rechargeType.equalsIgnoreCase ( "internet" )) {
            billerCode = "08011111111";
         //   Number number = NumberFormat.getInstance().parse(phone);
            //phone=
           // phone=new BigInteger ( 08011111111 );
        } else if (rechargeType.equalsIgnoreCase ( "phone" )) {
           // billerCode = "08011111111";
           // phone=new BigInteger ( "08011111111" );
        }

        mediatorLiveData.setValue ( TransactionResource.loading ( null ) );

        if(rechargeType.equalsIgnoreCase ( "phone" )){
          flowable=meterTransactionApi.publishAirtimeTransaction ( requestId,service,amount,phone  );
     }
        else{
          flowable=  meterTransactionApi.publishTransaction ( requestId, service, billerCode, variationCode, amount, phone );
        }
        final LiveData <TransactionResource <MeterTransaction>> userData = LiveDataReactiveStreams.
                fromPublisher ( flowable
                        .onErrorReturn ( (throwable) -> {

                            MeterTransaction meterTransaction = new MeterTransaction ( );
                            meterTransaction.setErrorMessage (  throwable.getMessage ());
                            meterTransaction.setCode ( "-1" );

                            return meterTransaction;

                        } )

                        .map ( meterTransaction -> {


                                    if (meterTransaction.getCode ( ).equalsIgnoreCase ( "-1" )) {
                                        return TransactionResource.error ( "Some error has occurred", meterTransaction );
                                    }
                                    return TransactionResource.authenticated ( meterTransaction );

                                }


                        )


                        .subscribeOn ( Schedulers.io ( ) ) );

        mediatorLiveData.addSource ( userData, meterSearchResource -> {

            mediatorLiveData.setValue ( meterSearchResource );

            mediatorLiveData.removeSource ( userData );
        } );

    }

    public LiveData <TransactionResource <MeterTransaction>> getTransactionLiveData() {
        return mediatorLiveData;
    }


    public void sendSMS(String url, SMSBody sms){
        mediatorLiveData.setValue ( TransactionResource.loading (  null ) );
        final LiveData <TransactionResource <SmsResponse>> smsData = LiveDataReactiveStreams.
                fromPublisher ( meterTransactionApi.sendSmsAfterTransaction ( url,sms )
                        .onErrorReturn ( (throwable) -> {

                          SmsResponse smsClass=new SmsResponse ();

                            smsClass.setErrorCode ( -1 );
                            smsClass.setErrorMessage ( throwable.getMessage ());


                            return smsClass;
                        } )

                        .map ( smsResponse -> {

                                    //System.out.println ( "Inside map" );
                                    if (smsResponse.getErrorCode ()==-1 ) {

                                        return TransactionResource.error ( "Some error has occurred", smsResponse );
                                    }
                                    return TransactionResource.authenticated ( smsResponse );

                                }


                        ).subscribeOn ( Schedulers.io ( ) ) );

        smsResponseMediatorData.addSource ( smsData, smsResponse -> {
            //  System.out.println ("adding source..." );
            smsResponseMediatorData.setValue ( smsResponse );

            smsResponseMediatorData.removeSource ( smsData );
        } );


    }

    public LiveData<TransactionResource<SmsResponse>> getSmsLiveData(){
        return smsResponseMediatorData;
    }
}
