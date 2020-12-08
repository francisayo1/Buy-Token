package com.feedco.note.Dagger;

import com.feedco.note.Retrofit.MeterTransactionApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public abstract class TransactionApiClientModule {

    @Provides
    public static MeterTransactionApi provideTransactionApi(Retrofit retrofit){

        return retrofit.create ( MeterTransactionApi.class );
    }


}
