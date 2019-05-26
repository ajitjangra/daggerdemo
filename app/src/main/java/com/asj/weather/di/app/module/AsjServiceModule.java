package com.asj.weather.di.app.module;

import com.asj.weather.di.app.scope.AsjApplicationScope;
import com.asj.weather.network.ApiInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = NetworkModule.class)
public class AsjServiceModule {

    @Provides
    @AsjApplicationScope
    public ApiInterface githubService(Retrofit gitHubRetrofit) {
        return gitHubRetrofit.create(ApiInterface.class);
    }

    @Provides
    @AsjApplicationScope
    public Gson gson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    @Provides
    @AsjApplicationScope
    public Retrofit retrofit(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .baseUrl("https://api.apixu.com/v1/")
                .build();
    }

}
