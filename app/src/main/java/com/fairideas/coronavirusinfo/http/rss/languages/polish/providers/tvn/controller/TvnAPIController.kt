package com.fairideas.coronavirusinfo.http.rss.languages.polish.providers.tvn.controller

import android.util.Log
import com.fairideas.coronavirusinfo.data.DataStorage
import com.fairideas.coronavirusinfo.data.model.NewsFeed
import com.fairideas.coronavirusinfo.http.filter.polish.PolishNewsFeedKeywordsFilter
import com.fairideas.coronavirusinfo.http.rss.languages.polish.providers.tvn.mapper.TvnAPIResponseMapper
import com.fairideas.coronavirusinfo.http.rss.languages.polish.providers.tvn.mapper.TvnFeedMapper
import com.fairideas.coronavirusinfo.http.rss.languages.polish.providers.tvn.model.TvnFeed
import com.fairideas.coronavirusinfo.http.rss.languages.polish.providers.tvn.service.TvnAPIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvnAPIController {

    private val TAG: String = TvnAPIController::class.toString()

    val rssNewsFeedKeywordsFilter =
        PolishNewsFeedKeywordsFilter()

    val tvnAPIService by lazy {
        TvnAPIService.create()
    }

    fun fetchFeedsAndAddToDataStorage() {

        val call: Call<String> = tvnAPIService.getNewsFeeds()
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>
            ) {
                Log.d(TAG, "TVNFeed endpoint response: ${response}")
                val tvnNewsFeed = TvnAPIResponseMapper.mapToTvnFeed(response.body()!!)
                val rssNewsFeeds = DataStorage.rssNewsFeeds.value
                rssNewsFeeds?.addAll(rssNewsFeedKeywordsFilter.filterByKeywords(
                    TvnFeedMapper.mapToRSSNewsFeeds(
                        tvnNewsFeed as TvnFeed)
                            as ArrayList<NewsFeed>))
                DataStorage.rssNewsFeeds.postValue(rssNewsFeeds)
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(TAG, t.message, t)
            }

        })
    }
}