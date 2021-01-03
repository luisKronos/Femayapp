package com.grupofemaya.SupervisionAlumbradoPublicoNew.Connection;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils.Constantes.URL_API;

public class APIClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        //.addHeader("Authorization", "Basic YW1lcmljYWRpZ2l0YWxoZXJjdWxlczphbWVyaWNhZGlnaXRhbGhlcmN1bGVz")
                        .addHeader("Content-Type", "application/json");

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response;
                boolean responseOK = false;
                int tryCount = 1;

                while (!responseOK && tryCount < 4) {
                    try {
                        response = chain.proceed(request);
                        responseOK = response.isSuccessful();
                        Log.d("intercept", "Request is successful - " + tryCount);
                    }catch (Exception e){
                        Log.d("intercept", "Request is not successful - " + tryCount);
                    }finally{
                        tryCount++;
                    }
                }

                // otherwise just pass the original response on
                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build()
                .newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();



        return retrofit;
    }
}
