package com.fairideas.coronavirusinfo.notificationfetch

import android.app.job.JobParameters
import android.app.job.JobService

class FetchNotificationJobService : JobService() {



    override fun onStopJob(params: JobParameters?): Boolean {

        return false
    }

    override fun onStartJob(params: JobParameters?): Boolean {

        fetchNotificationData()
        return false
    }

    fun fetchNotificationData() {

        DataFetcher(this).fetchNewsHeadlines()
    }


}