package com.feedco.note.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.feedco.note.Models.PlanModel.Plans;
import com.feedco.note.Repository.PlanRepository;
import com.feedco.note.Resource.PlanSearchResource;

public class PlanViewModel extends ViewModel {

    public LiveData <PlanSearchResource <Plans>> getPlansData( PlanRepository planRepository,String type) {
        planRepository.requestPlans (  type);
        return planRepository.getPlansLiveData ( );

    }

}
