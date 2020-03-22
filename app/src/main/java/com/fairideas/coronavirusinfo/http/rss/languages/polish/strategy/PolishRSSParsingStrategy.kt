package com.fairideas.coronavirusinfo.http.rss.languages.polish.strategy

import com.fairideas.coronavirusinfo.http.rss.languages.polish.providers.tvn.controller.TvnAPIController
import com.fairideas.coronavirusinfo.http.rss.strategy.RSSParsingStrategy

class PolishRSSParsingStrategy : RSSParsingStrategy {

    private val tvnAPIController: TvnAPIController = TvnAPIController()

    override fun fetchFeedsAndAddToDataStorage() {
        tvnAPIController.fetchFeedsAndAddToDataStorage()
    }

}