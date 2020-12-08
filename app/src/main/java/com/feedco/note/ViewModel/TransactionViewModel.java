package com.feedco.note.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.feedco.note.Models.MeterTransactionModel.MeterTransaction;
import com.feedco.note.Models.SmsModel.SMS;
import com.feedco.note.Models.SmsModel.SMSBody;
import com.feedco.note.Models.SmsModel.SmsResponse;
import com.feedco.note.Repository.TransactionRepository;
import com.feedco.note.Resource.TransactionResource;

public class TransactionViewModel extends ViewModel {



    public LiveData <TransactionResource <MeterTransaction>> getTransactionLiveData(String rechargeType,String requestId, String serviceId,String billerCode, String variationCode, Number amount, Number phone, TransactionRepository transactionRepository) {

      //  transactionRepository.requestTransaction ( billerCode, serviceId, type, amount, phone, requestId );
        transactionRepository.requestTransaction ( rechargeType,requestId, serviceId, billerCode,variationCode, amount, phone );

        return transactionRepository.getTransactionLiveData ( );


    }

    public LiveData<TransactionResource<SmsResponse>> getSmsResponseData(String url, SMSBody sms, TransactionRepository transactionRepository){

        transactionRepository.sendSMS ( url,sms );
        return transactionRepository.getSmsLiveData ();

    }

}
