package com.fairideas.coronavirusinfo.http.scrapper.languages.polish.providers.gov.api

import com.fairideas.coronavirusinfo.http.scrapper.languages.polish.constants.ScrapperEndpoint
import retrofit2.Call
import retrofit2.http.GET

interface GovAPI {

    @GET(ScrapperEndpoint.GOV_COVID_NEWS_RELATIVE_URL)
    fun getNewsFeeds(): Call<String>

}