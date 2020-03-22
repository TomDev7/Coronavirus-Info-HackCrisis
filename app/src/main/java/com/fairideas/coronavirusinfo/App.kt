package com.fairideas.coronavirusinfo

import android.app.Application
import com.fairideas.coronavirusinfo.util.PrefHelper
import com.squareup.leakcanary.LeakCanary

/**
 * Created by illia-herman on 24.02.20.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) initLeakCanary()
        PrefHelper.init(baseContext)
    }

    private fun initLeakCanary() {
        when {
            LeakCanary.isInAnalyzerProcess(this) -> return
            else -> LeakCanary.install(this)
        }
    }
}