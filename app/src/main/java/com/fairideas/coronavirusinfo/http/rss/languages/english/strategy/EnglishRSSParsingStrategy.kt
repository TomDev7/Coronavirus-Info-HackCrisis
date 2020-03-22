package com.fairideas.coronavirusinfo.http.rss.languages.english.strategy

import com.fairideas.coronavirusinfo.http.rss.languages.english.providers.who.controller.WhoAPIController
import com.fairideas.coronavirusinfo.http.rss.strategy.RSSParsingStrategy

class EnglishRSSParsingStrategy : RSSParsingStrategy {

    private val whoAPIController: WhoAPIController = WhoAPIController()

    override fun fetchFeedsAndAddToDataStorage() {
        whoAPIController.fetchFeedsAndAddToDataStorage()
    }

}