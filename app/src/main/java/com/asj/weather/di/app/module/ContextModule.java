package com.asj.weather.di.app.module;

import android.content.Context;

import com.asj.weather.di.app.scope.ApplicationContext;
import com.asj.weather.di.app.scope.AsjApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    private final Context context;

    public ContextModule(Context context) {
        this.context = context.getApplicationContext();
    }

    @Provides
    @AsjApplicationScope
    @ApplicationContext
    public Context context() {
        return context;
    }
}
