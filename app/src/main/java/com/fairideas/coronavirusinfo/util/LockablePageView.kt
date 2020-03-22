package com.fairideas.coronavirusinfo.util

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * Created by illia-herman on 04.03.20.
 */
class LockablePageView(context: Context, attrs: AttributeSet) :
    ViewPager(context, attrs) {

    var isUnlocked: Boolean = false

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean = when {
        this.isUnlocked -> super.onInterceptTouchEvent(ev)
        else -> false
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean = when {
        this.isUnlocked -> super.onTouchEvent(ev)
        else -> false
    }
}