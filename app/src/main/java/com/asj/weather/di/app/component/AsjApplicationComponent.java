package com.asj.weather.di.app.component;

import com.asj.weather.di.app.module.AsjServiceModule;
import com.asj.weather.di.app.scope.AsjApplicationScope;
import com.asj.weather.network.ApiInterface;

import dagger.Component;

@AsjApplicationScope
@Component(modules = {AsjServiceModule.class})
public interface AsjApplicationComponent {

    ApiInterface getGithubService();
}
