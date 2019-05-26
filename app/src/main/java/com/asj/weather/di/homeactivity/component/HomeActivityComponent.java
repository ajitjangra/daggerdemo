package com.asj.weather.di.homeactivity.component;

import com.asj.weather.di.homeactivity.module.HomeActivityModule;
import com.asj.weather.di.homeactivity.scope.HomeActivityScope;
import com.asj.weather.di.app.component.AsjApplicationComponent;
import com.asj.weather.ui.WeatherActivity;

import dagger.Component;

@HomeActivityScope
@Component(modules = HomeActivityModule.class, dependencies = AsjApplicationComponent.class)
public interface HomeActivityComponent {

    void injectHomeActivity(WeatherActivity homeActivity);
}
