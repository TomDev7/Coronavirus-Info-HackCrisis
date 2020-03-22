package com.fairideas.coronavirusinfo.http.rss.strategy

interface RSSParsingStrategy {
    fun fetchFeedsAndAddToDataStorage()
}