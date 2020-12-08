package com.feedco.note.ViewModel;

import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.feedco.note.Models.MeterSearchModel.MeterSearchModel;
import com.feedco.note.Repository.MeterSearchRepository;
import com.feedco.note.Resource.MeterSearchResource;

public class MeterSearchViewModel extends ViewModel {


    public LiveData <MeterSearchResource <MeterSearchModel>> getMeterDetails(String billerCode, String serviceId, String type, MeterSearchRepository meterSearchRepository
    ) {
        if(!TextUtils.isEmpty ( billerCode )){

            meterSearchRepository.requestMeter ( billerCode, serviceId, type );
            //meterSearchRepository.requestMeter ( billerCode, serviceId, type );
            return meterSearchRepository.getUserMeterDetails ( );

        }
        return null;

    }

    /*public void getMeterDetails(String billerCode,String serviceId,String type,MeterSearchRepository meterSearchRepository,
    MeterDetails meterDetails) {
        //meterSearchRepository.requestMeter (billerCode,serviceId,type,meterDetails );
       // return meterSearchRepository.getUserMeterDetails ( );
        //meterSearchRepository.getMeterDetails ( billerCode,serviceId,type,meterDetails );
    }*/


}
