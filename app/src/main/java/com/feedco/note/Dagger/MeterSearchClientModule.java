package com.feedco.note.Dagger;

import com.feedco.note.Repository.MeterSearchRepository;
import com.feedco.note.Retrofit.MeterSeachApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
abstract class MeterSearchClientModule {

    @Provides
   static MeterSeachApi provideMeterSearchApi(Retrofit retrofit){

        return retrofit.create ( MeterSeachApi.class );

    }
   /* @Provides
    static MeterSearchRepository provideMeterSearchRepo(MeterSeachApi meterSeachApi){

        return new MeterSearchRepository ( meterSeachApi );

    }*/
}
