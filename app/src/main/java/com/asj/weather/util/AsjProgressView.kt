package com.asj.weather.util

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import com.asj.weather.R
import kotlinx.android.synthetic.main.asj_progress_view.view.*

class AsjProgressView : LinearLayout {

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    private fun init(context: Context) {
        val mLayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        setWillNotDraw(false)
        removeAllViews()

        val vMain = mLayoutInflater.inflate(R.layout.asj_progress_view, null)
        val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        addView(vMain, layoutParams)

        val rotation = AnimationUtils.loadAnimation(context, R.anim.anim_rotate)
        rotation.fillAfter = true
        ivProgress.startAnimation(rotation)
    }
}