package com.fairideas.coronavirusinfo.http.rss.languages.english.providers.who.api

import com.fairideas.coronavirusinfo.http.rss.languages.english.constants.RSSChannelEndpoints
import retrofit2.Call
import retrofit2.http.GET

interface WhoAPI {
    @GET(RSSChannelEndpoints.WHO_RELATIVE_RSS_URL)
    fun getNewsFeeds(): Call<String>
}

