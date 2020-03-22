package com.fairideas.coronavirusinfo.http.scrapper.languages.polish.providers.gov.service

import com.fairideas.coronavirusinfo.http.scrapper.languages.polish.constants.ScrapperEndpoint
import com.fairideas.coronavirusinfo.http.scrapper.languages.polish.providers.gov.api.GovAPI
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class GovAPIService {
    companion object {
        fun create(): GovAPI {
            val retrofit = Retrofit.Builder().addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(ScrapperEndpoint.GOV_BASE_URL)
                .build()

            return retrofit.create(GovAPI::class.java)
        }
    }
}