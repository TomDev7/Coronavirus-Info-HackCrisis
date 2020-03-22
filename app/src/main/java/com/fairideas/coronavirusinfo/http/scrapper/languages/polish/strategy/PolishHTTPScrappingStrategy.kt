package com.fairideas.coronavirusinfo.http.scrapper.languages.polish.strategy

import com.fairideas.coronavirusinfo.http.scrapper.languages.polish.providers.gov.controller.GovAPIController
import com.fairideas.coronavirusinfo.http.scrapper.strategy.HTTPScrappingStrategy

class PolishHTTPScrappingStrategy : HTTPScrappingStrategy {

    private val govAPIController: GovAPIController = GovAPIController()

    override fun scrapInfoAndAddToDataStorage() {
        govAPIController.scrapInfoAndAddToDataStorage()
    }
}