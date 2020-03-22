package com.fairideas.coronavirusinfo.http.rss.languages.polish.providers.tvn.service

import com.fairideas.coronavirusinfo.http.rss.languages.polish.constants.RSSChannelEndpoints
import com.fairideas.coronavirusinfo.http.rss.languages.polish.providers.tvn.api.TvnAPI
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class TvnAPIService {
    companion object {
        fun create(): TvnAPI {
            val retrofit = Retrofit.Builder().addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(RSSChannelEndpoints.TVN_BASE_URL)
                .build()

            return retrofit.create(TvnAPI::class.java)
        }
    }
}