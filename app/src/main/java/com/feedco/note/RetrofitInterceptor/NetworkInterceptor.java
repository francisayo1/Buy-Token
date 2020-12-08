package com.feedco.note.RetrofitInterceptor;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;

import com.feedco.note.CustomClasses.NoConnectivityException;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Connection;
import okhttp3.Interceptor;
import okhttp3.Response;

public class NetworkInterceptor implements Interceptor {

    Application application;
    public  NetworkInterceptor( Application application){
        this.application=application;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {

        if (!isInternetAvailable ( )) {
            throw new NoConnectivityException ( );
        }
        return chain.proceed ( chain.request ( ) );
    }

    private boolean isInternetAvailable() {


        ConnectivityManager connectivityManager = (ConnectivityManager) application.getApplicationContext ( ).getSystemService ( Context.CONNECTIVITY_SERVICE );


       // System.out.println (" Hanji connected"+connectivityManager.getActiveNetworkInfo ().isConnected () );
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
