package com.example.whatstheweather.data;

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.whatstheweather.R


class UiService {
    private var loader: View? = null

    fun showLoader (context:Context?, layout: ConstraintLayout){
        if (context != null) {
            val layoutInflater = LayoutInflater.from(context)
            loader = layoutInflater.inflate(R.layout.loader, layout, false)
            //loader?.layoutParams?.width = ViewGroup.LayoutParams.MATCH_PARENT
            //loader?.layoutParams?.height = ViewGroup.LayoutParams.WRAP_CONTENT
            val layoutParams: ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT)
            layoutParams.bottomToBottom = layout.id
            layoutParams.topToTop = layout.id
            layoutParams.height = RelativeLayout.LayoutParams.WRAP_CONTENT
            layoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT
            loader?.layoutParams = layoutParams
            layout.addView(loader);
        }
    }

    fun hideLoader (layout: ConstraintLayout) {
        if (loader != null) {
            layout.removeView(loader);
        }
    }
}
