package com.feedco.note.RetrofitInterceptor;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class BasicInterceptor implements Interceptor {

    private String authToken;

    public BasicInterceptor(String username, String password) {
        System.out.println (username+" "+ password );
        authToken = Credentials.basic ( username, password );
        //this.authToken = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request ( );

        Request.Builder builder = original.newBuilder ( )
                .header ( "Authorization", authToken );

        Request request = builder.build ( );
        return chain.proceed ( request );
    }
}
