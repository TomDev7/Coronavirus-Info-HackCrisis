package com.fairideas.coronavirusinfo.http.rss.constants

import com.fairideas.coronavirusinfo.http.rss.languages.english.strategy.EnglishRSSParsingStrategy
import com.fairideas.coronavirusinfo.http.rss.languages.polish.strategy.PolishRSSParsingStrategy
import com.fairideas.coronavirusinfo.http.rss.strategy.RSSParsingStrategy
import java.util.*

enum class LocalesToParsingStrategies(val locale: Locale, val parsingStrategy: RSSParsingStrategy) {
    PL(Locale("pl", "PL"), PolishRSSParsingStrategy()),
    EN_US(Locale("en", "US"), EnglishRSSParsingStrategy()),
    EN_GB(Locale("en", "GB"), EnglishRSSParsingStrategy());

    companion object {
        fun getStrategyByLocale(locale: Locale): RSSParsingStrategy {
            for (value in values()) {
                if (value.locale.equals(locale)) {
                    return value.parsingStrategy
                }
            }
            return EN_US.parsingStrategy
        }
    }
}