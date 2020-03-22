package com.fairideas.coronavirusinfo.http.rss.languages.english.providers.who.service

import com.fairideas.coronavirusinfo.http.rss.languages.english.constants.RSSChannelEndpoints
import com.fairideas.coronavirusinfo.http.rss.languages.english.providers.who.api.WhoAPI
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class WhoAPIService {
    companion object {
        fun create(): WhoAPI {
            val retrofit = Retrofit.Builder().addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(RSSChannelEndpoints.WHO_BASE_URL)
                .build()

            return retrofit.create(WhoAPI::class.java)
        }
    }
}