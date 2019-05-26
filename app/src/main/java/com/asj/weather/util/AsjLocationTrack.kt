package com.asj.weather.util

import android.Manifest
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.support.v4.app.ActivityCompat

class AsjLocationTrack(private val mContext: Context) : Service(), LocationListener {

    internal var checkGPS = false

    internal var loc: Location? = null
    internal var latitude: Double = 0.toDouble()
    internal var longitude: Double = 0.toDouble()
    protected var locationManager: LocationManager? = null

    init {
        locationManager = mContext.getSystemService(LOCATION_SERVICE) as LocationManager
    }

    fun getLocation() {
            try {
                locationManager?.let {
                    checkGPS = locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER)!!

                    if (checkGPS && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        locationManager?.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), this)
                    }

                    loc = locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)

                    if (loc != null) {
                        latitude = loc!!.latitude
                        longitude = loc!!.longitude
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    fun getLongitude(): Double {
        if (loc != null) {
            longitude = loc!!.longitude
        }
        return longitude
    }

    fun getLatitude(): Double {
        if (loc != null) {
            latitude = loc!!.latitude
        }
        return latitude
    }

    override fun onLocationChanged(location: Location) {

    }

    override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {

    }

    override fun onProviderEnabled(s: String) {

    }

    override fun onProviderDisabled(s: String) {

    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    companion object {
        private val MIN_DISTANCE_CHANGE_FOR_UPDATES: Long = 10
        private val MIN_TIME_BW_UPDATES = (1000 * 60 * 1).toLong()
    }
}
