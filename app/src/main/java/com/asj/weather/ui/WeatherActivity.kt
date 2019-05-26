package com.asj.weather.ui

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.TargetApi
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.Window
import com.asj.weather.AsjApplication
import com.asj.weather.R
import com.asj.weather.adapter.ForeCastAdapter
import com.asj.weather.model.WeatherModel
import com.asj.weather.network.ApiInterface
import com.asj.weather.network.ApiResponse
import com.asj.weather.util.Constants
import com.asj.weather.util.AsjLocationTrack
import com.asj.weather.util.Utility
import com.asj.weather.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.error_view.*
import kotlinx.android.synthetic.main.weather_view.*
import java.util.ArrayList
import com.asj.weather.di.homeactivity.module.HomeActivityModule
import javax.inject.Inject
import android.databinding.DataBindingUtil
import com.asj.weather.databinding.ActivityWeatherBinding
import com.asj.weather.di.homeactivity.component.DaggerHomeActivityComponent


class WeatherActivity : BaseActivity() {

    private lateinit var wViewModel: WeatherViewModel

    private lateinit var alForeCastDay: ArrayList<WeatherModel.ForeCastDay>
    private lateinit var adapter: ForeCastAdapter

    @Inject
    lateinit var apiInterface: ApiInterface

    private var permissionsToRequest: ArrayList<String> = ArrayList()
    private val permissionsRejected = ArrayList<String>()
    private val permissions = ArrayList<String>()
    private val ALL_PERMISSIONS_RESULT = 101
    internal lateinit var locationTrack: AsjLocationTrack

    lateinit var binding: ActivityWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideToolBar()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_weather)

        val component = DaggerHomeActivityComponent.builder()
                .homeActivityModule(HomeActivityModule(this))
                .asjApplicationComponent(AsjApplication.get(this).component())
                .build()

        component.injectHomeActivity(this)

        initAdapter()
        initViewModel()
        locationInit()

        btnRetry.setOnClickListener(View.OnClickListener { locationInit() })
    }

    /**
     * Hide toolbar
     */
    private fun hideToolBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
    }

    /**
     * Init forecast adapter
     */
    private fun initAdapter() {
        val manager = LinearLayoutManager(this@WeatherActivity, LinearLayoutManager.VERTICAL, false)
        rvForeCast.layoutManager = manager
        alForeCastDay = ArrayList<WeatherModel.ForeCastDay>()
        adapter = ForeCastAdapter(this@WeatherActivity, alForeCastDay!!)
        rvForeCast.adapter = adapter
    }

    /**
     * Init View model
     */
    private fun initViewModel() {
        wViewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)

        val weatherObserver = Observer<ApiResponse<WeatherModel>> { response ->
            if (response != null && response.status === Constants.SUCCESS) {
                successState(response.data)
            } else {
                updateState(View.GONE, View.GONE, View.VISIBLE)
            }
        }

        wViewModel?.wiLiveData.observe(this, weatherObserver)
    }

    /**
     * Update State >>> Progress/ Success / Failure
     */
    private fun updateState(progress: Int, success: Int, error: Int) {
        goJekProgressView.visibility = progress
        errorView.visibility = error
        weatherView.visibility = success
    }

    /**
     * Success State
     */
    private fun successState(weatherModel: WeatherModel?) {
        if (weatherModel?.currentTemp != null) {
            updateState(View.GONE, View.VISIBLE, View.GONE)

            binding.model = weatherModel

            if (weatherModel.foreCastTemp != null && weatherModel.foreCastTemp!!.alForeCastDay != null) {
                alForeCastDay?.clear()
                alForeCastDay?.addAll(weatherModel.foreCastTemp!!.alForeCastDay!!)
                adapter!!.notifyDataSetChanged()
            }

            Utility.slideUp(rvForeCast)
        } else {
            updateState(View.GONE, View.GONE, View.VISIBLE)
        }
    }

    private fun locationInit() {
        updateState(View.VISIBLE, View.GONE, View.GONE)

        permissions.add(ACCESS_FINE_LOCATION)
        permissions.add(ACCESS_COARSE_LOCATION)
        permissionsToRequest = findUnAskedPermissions(permissions)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && permissionsToRequest.size > 0) {
            requestPermissions(permissionsToRequest.toTypedArray(), ALL_PERMISSIONS_RESULT)
        } else {
            locationTrack = AsjLocationTrack(this@WeatherActivity)
            locationTrack.getLocation()
            wViewModel.updateWeatherInfo(apiInterface, locationTrack.getLatitude(), locationTrack.getLongitude())
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        when (requestCode) {

            ALL_PERMISSIONS_RESULT -> {
                for (perms in permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms)
                    }
                }

                if (permissionsRejected.size > 0) {
                    if (shouldShowRequestPermissionRationale(permissionsRejected[0])) {
                        showMessageOKCancel(getString(R.string.permission_msg),
                                DialogInterface.OnClickListener { dialog, which ->
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(permissionsRejected.toTypedArray(), ALL_PERMISSIONS_RESULT)
                                    }
                                }, DialogInterface.OnClickListener { dialog, which -> updateState(View.GONE, View.GONE, View.VISIBLE) })
                        return
                    } else {
                        locationInit()
                    }
                } else {
                    locationInit()
                }
            }
        }

    }
}
