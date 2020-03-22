package com.fairideas.coronavirusinfo.util

import android.view.animation.Animation

/**
 * Created by illia-herman on 28.02.20.
 */
interface AnimationShort: Animation.AnimationListener{
    override fun onAnimationRepeat(animation: Animation?) {}
    override fun onAnimationEnd(animation: Animation?) {}
    override fun onAnimationStart(animation: Animation?) {}
}