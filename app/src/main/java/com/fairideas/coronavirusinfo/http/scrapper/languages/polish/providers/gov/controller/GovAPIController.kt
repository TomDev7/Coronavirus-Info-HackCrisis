package com.fairideas.coronavirusinfo.http.scrapper.languages.polish.providers.gov.controller

import android.util.Log
import com.fairideas.coronavirusinfo.data.DataStorage
import com.fairideas.coronavirusinfo.http.scrapper.languages.polish.providers.gov.mapper.GovAPIReponseMapper
import com.fairideas.coronavirusinfo.http.scrapper.languages.polish.providers.gov.service.GovAPIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GovAPIController {
    private val TAG: String = GovAPIController::class.toString()

    val govAPIService by lazy {
        GovAPIService.create()
    }

    fun scrapInfoAndAddToDataStorage() {
        val call: Call<String> = govAPIService.getNewsFeeds()
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>
            ) {
                Log.d(TAG, "Gov endpoint response: $response")
                val newsFeeds = GovAPIReponseMapper.mapToNewsFeeds(response.body()!!)
                val tipsNewsFeeds = DataStorage.announcementsNewsFeeds.value
                tipsNewsFeeds?.addAll(newsFeeds)
                DataStorage.announcementsNewsFeeds.postValue(tipsNewsFeeds)
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(TAG, t.message, t)
            }
        })
    }
}