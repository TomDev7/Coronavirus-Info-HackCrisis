package com.fairideas.coronavirusinfo.http.rss.languages.english.providers.who.controller

import android.util.Log
import com.fairideas.coronavirusinfo.data.DataStorage
import com.fairideas.coronavirusinfo.data.model.NewsFeed
import com.fairideas.coronavirusinfo.http.filter.english.EnglishNewsFeedKeywordsFilter
import com.fairideas.coronavirusinfo.http.rss.languages.english.providers.who.mapper.WhoAPIResponseMapper
import com.fairideas.coronavirusinfo.http.rss.languages.english.providers.who.mapper.WhoFeedMapper
import com.fairideas.coronavirusinfo.http.rss.languages.english.providers.who.model.WhoFeed
import com.fairideas.coronavirusinfo.http.rss.languages.english.providers.who.service.WhoAPIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WhoAPIController {
    private val TAG: String = WhoAPIController::class.toString()

    val rssNewsFeedKeywordsFilter =
        EnglishNewsFeedKeywordsFilter()

    val whoAPIService by lazy {
        WhoAPIService.create()
    }

    fun fetchFeedsAndAddToDataStorage() {

        val call: Call<String> = whoAPIService.getNewsFeeds()
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>
            ) {
                Log.d(TAG, "WHOFeed endpoint response: $response")
                val whoNewsFeed = WhoAPIResponseMapper.mapToWhoFeed(response.body()!!)
                val rssNewsFeeds = DataStorage.rssNewsFeeds.value
                rssNewsFeeds?.addAll(rssNewsFeedKeywordsFilter.filterByKeywords(
                    WhoFeedMapper.mapToRSSNewsFeeds(whoNewsFeed as WhoFeed)
                            as ArrayList<NewsFeed>))
                DataStorage.rssNewsFeeds.postValue(rssNewsFeeds)
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(TAG, t.message, t)
            }

        })
    }

}

