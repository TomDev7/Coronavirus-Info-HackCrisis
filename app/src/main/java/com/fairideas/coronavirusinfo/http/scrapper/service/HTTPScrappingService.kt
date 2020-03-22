package com.fairideas.coronavirusinfo.http.scrapper.service

import com.fairideas.coronavirusinfo.http.scrapper.constants.LocalesToScrappingStrategies
import java.util.*

class HTTPScrappingService {

    fun fetchLocalizedFeedsAndAddToDataStorage() {
        val currentLocale: Locale = Locale.getDefault()
        val scrappingStrategy = LocalesToScrappingStrategies.getStrategyByLocale(currentLocale)
        scrappingStrategy.scrapInfoAndAddToDataStorage()
    }
}