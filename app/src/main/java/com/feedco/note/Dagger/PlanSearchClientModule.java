package com.feedco.note.Dagger;

import com.feedco.note.Retrofit.PlanSearchApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;


@Module
class PlanSearchClientModule {

    @Singleton
    @Provides
    static PlanSearchApi providePlanSearchApi(Retrofit retrofit){

        return retrofit.create ( PlanSearchApi.class );

    }
}
