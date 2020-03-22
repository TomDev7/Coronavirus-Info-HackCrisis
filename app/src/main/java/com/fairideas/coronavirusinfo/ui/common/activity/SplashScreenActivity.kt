package com.fairideas.coronavirusinfo.ui.common.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.fairideas.coronavirusinfo.R
import com.fairideas.coronavirusinfo.util.AnimationShort
import com.fairideas.coronavirusinfo.util.PrefHelper
import com.fairideas.coronavirusinfo.util.applyViewVisible
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : AppCompatActivity(R.layout.activity_splash_screen) {

    private var delayHandler: Handler? = null
    private val splashDelay: Long = 500L

    private lateinit var animFadeIn: Animation
    private lateinit var animZoomIn: Animation
    private lateinit var animationListenerBackgroundFadeIn: Animation.AnimationListener
    private lateinit var animationListenerZoomIn: Animation.AnimationListener

    private val runnable: Runnable = Runnable {
        if (!isFinishing) {
            when (PrefHelper.isFirstInit) {
                true -> {
                    startActivity(Intent(this, OnBoardActivity::class.java))
                }
                false -> {
                    startActivity(Intent(this, MainActivity::class.java))
                }
            }
            finish()
            overridePendingTransition(0, 0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAnimationsListener()
        initAnimations()
        startAnimations()
    }


    private fun initAnimations() {
        animFadeIn = AnimationUtils.loadAnimation(this, R.anim.anim_fade_in)
        animZoomIn = AnimationUtils.loadAnimation(this, R.anim.anim_zoom_in)
        animFadeIn.setAnimationListener(animationListenerBackgroundFadeIn)
        animZoomIn.setAnimationListener(animationListenerZoomIn)
    }


    private fun initAnimationsListener() {
        animationListenerBackgroundFadeIn = object : AnimationShort {
            override fun onAnimationEnd(animation: Animation?) {
                applyViewVisible(tv_description, img_logo)
                img_logo.startAnimation(animZoomIn)
            }
        }

        animationListenerZoomIn = object : AnimationShort {
            override fun onAnimationEnd(animation: Animation?) {
                delayHandler = Handler()
                delayHandler?.postDelayed(runnable, splashDelay)
            }
        }
    }

    private fun startAnimations() {
        img_logo_drawable.startAnimation(animFadeIn)
    }

    override fun onDestroy() {
        if (delayHandler != null) {
            delayHandler?.removeCallbacks(runnable)
        }
        super.onDestroy()
    }
}
