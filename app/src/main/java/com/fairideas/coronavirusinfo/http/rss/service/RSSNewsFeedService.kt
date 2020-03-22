package com.fairideas.coronavirusinfo.http.rss.service

import com.fairideas.coronavirusinfo.http.rss.constants.LocalesToParsingStrategies
import java.util.*

class RSSNewsFeedService {
    fun fetchLocalizedFeedsAndAddToDataStorage() {
        val currentLocale: Locale = Locale.getDefault()
        val parsingStrategy = LocalesToParsingStrategies.getStrategyByLocale(currentLocale)
        parsingStrategy.fetchFeedsAndAddToDataStorage()
    }
}