package com.asj.weather.di.homeactivity.module;

import com.asj.weather.di.homeactivity.scope.HomeActivityScope;
import com.asj.weather.ui.WeatherActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeActivityModule {

    private final WeatherActivity homeActivity;

    public HomeActivityModule(WeatherActivity homeActivity) {
        this.homeActivity = homeActivity;
    }

    @Provides
    @HomeActivityScope
    public WeatherActivity homeActivity() {
        return homeActivity;
    }
}
