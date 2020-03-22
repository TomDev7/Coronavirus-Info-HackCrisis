package com.fairideas.coronavirusinfo.http.scrapper.constants

import com.fairideas.coronavirusinfo.http.scrapper.languages.polish.strategy.PolishHTTPScrappingStrategy
import com.fairideas.coronavirusinfo.http.scrapper.strategy.HTTPScrappingStrategy
import java.util.*

enum class LocalesToScrappingStrategies(val locale: Locale, val scrappingStrategy: HTTPScrappingStrategy) {
    PL(Locale("pl", "PL"), PolishHTTPScrappingStrategy());

    companion object {
        fun getStrategyByLocale(locale: Locale): HTTPScrappingStrategy {
            for (value in values()) {
                if (value.locale.equals(locale)) {
                    return value.scrappingStrategy
                }
            }
            return PL.scrappingStrategy
        }
    }
}