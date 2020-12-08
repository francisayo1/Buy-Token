package com.feedco.note.Dagger;

import android.app.Application;

import androidx.room.Room;

import com.feedco.note.Database.TransactionDB;
import com.feedco.note.RetrofitInterceptor.BasicInterceptor;
import com.feedco.note.RetrofitInterceptor.NetworkInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
abstract class AppModule {
    // https://remnetsolutions1%40gmail.com:Kelapro%402003@vtpass.com/api/merchant-verify


    // https://harpreet%40gmail.com:harry14@sandbox.vtpass.com/api/pay
    @Singleton
    @Provides
    static Retrofit getReprofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder ( ).
                // baseUrl ( "https://vtpass.com/api/" )
                        baseUrl ( "https://sandbox.vtpass.com/api/" )
                .addConverterFactory ( GsonConverterFactory.create ( ) )
                .addCallAdapterFactory ( RxJava2CallAdapterFactory.create ( ) )
                .client ( okHttpClient )
                .build ( );
    }

    @Singleton
    @Provides
    static OkHttpClient getOkhttpClient(HttpLoggingInterceptor loggingInterceptor,Application application) {
        return new OkHttpClient.Builder ( )
                .addInterceptor ( loggingInterceptor )
                .connectTimeout ( 1, TimeUnit.MINUTES )
                .readTimeout ( 30, TimeUnit.SECONDS )
                .writeTimeout ( 15, TimeUnit.SECONDS )

                // .addInterceptor ( new BasicInterceptor ( "remnetsolutions1@gmail.com", "Kelapro@2003" ) )
                .addInterceptor ( new BasicInterceptor ( "harpreet@gmail.com", "harry14" ) )
                .addInterceptor ( new NetworkInterceptor (application) )
                .build ( );

    }

    @Singleton
    @Provides
    static HttpLoggingInterceptor getLogging() {
        return new HttpLoggingInterceptor ( )
                .setLevel ( HttpLoggingInterceptor.Level.BODY );

    }

    @Singleton
    @Provides
    static TransactionDB getAppDatabase(Application application) {

        return Room.databaseBuilder ( application, TransactionDB.class, "Transaction_Db" )
                // allow queries on the main thread.
                // Don't do this on a real app! See PersistenceBasicSample for an example.
                .allowMainThreadQueries ( )
                .build ( );


    }



}
