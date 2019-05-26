package com.asj.weather;

import android.app.Activity;
import android.app.Application;

import com.asj.weather.di.app.component.AsjApplicationComponent;
import com.asj.weather.di.app.component.DaggerAsjApplicationComponent;
import com.asj.weather.di.app.module.ContextModule;
import com.asj.weather.network.ApiInterface;

import org.jetbrains.annotations.Nullable;

public class AsjApplication extends Application {

  private ApiInterface apiInterface;

  public static AsjApplication get(Activity activity) {
    return (AsjApplication) activity.getApplication();
  }

  AsjApplicationComponent component;

  @Override
  public void onCreate() {
    super.onCreate();

    component = DaggerAsjApplicationComponent.builder().contextModule(new ContextModule(this)).build();
    apiInterface = component.getGithubService();
    System.out.println("asj "+apiInterface);
  }

  public ApiInterface apiInterface() {
    return apiInterface;
  }

  @Nullable
  public AsjApplicationComponent component() {
    return component;
  }
}
