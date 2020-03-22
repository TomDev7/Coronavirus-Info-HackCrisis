package com.fairideas.coronavirusinfo.http.rss.languages.polish.providers.tvn.api

import com.fairideas.coronavirusinfo.http.rss.languages.polish.constants.RSSChannelEndpoints
import retrofit2.Call
import retrofit2.http.GET

interface TvnAPI {

    @GET(RSSChannelEndpoints.TVN_RELATIVE_RSS_URL)
    fun getNewsFeeds(): Call<String>

}