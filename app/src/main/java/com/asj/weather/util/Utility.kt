package com.asj.weather.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.animation.TranslateAnimation
import java.text.ParseException
import java.text.SimpleDateFormat

public class Utility {
    companion object {
        fun getDayName(strDate: String?): String {
            if (!isNullOrWhiteSpace(strDate)) {
                val sdf = SimpleDateFormat(Constants.DATE_YYYY_MM_DD)
                try {
                    val d = sdf.parse(strDate)

                    if (d != null) {
                        val parseFormat = SimpleDateFormat(Constants.DATE_EEEE)
                        return parseFormat.format(d)
                    }
                } catch (ex: ParseException) {
                    println("ex")
                }
            }

            return ""
        }

        private fun isNullOrWhiteSpace(str: String?): Boolean {
            return !(str != null && str.trim().isNotEmpty())
        }

        fun isInternetConnected(ctx: Context): Boolean {
            val connectivity = ctx.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var internetConnected = false

            if (connectivity != null) {
                val info = connectivity.allNetworkInfo
                if (info != null) {
                    for (anInfo in info) {
                        if (anInfo.state == NetworkInfo.State.CONNECTED) {
                            internetConnected = true
                        }
                    }
                }
            }
            return internetConnected
        }

        fun slideUp(view: View?) {
            view?.let {
                val animate = TranslateAnimation(0f, 0f, 800f, 0f)
                animate.duration = 1300
                animate.startOffset = 100
                animate.fillAfter = true
                view.startAnimation(animate)
            }
        }

        @JvmStatic
        public fun updateCurrentTempMsg(currentTemp: Double): String {
            return ("" + currentTemp + 0x00B0.toChar())
        }
    }
}
