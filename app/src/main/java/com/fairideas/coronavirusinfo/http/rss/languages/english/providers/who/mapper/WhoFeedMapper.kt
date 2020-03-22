package com.fairideas.coronavirusinfo.http.rss.languages.english.providers.who.mapper

import com.fairideas.coronavirusinfo.data.model.NewsFeed
import com.fairideas.coronavirusinfo.http.rss.constants.LocalesToParsingStrategies
import com.fairideas.coronavirusinfo.http.rss.languages.english.constants.RSSChannelEndpoints
import com.fairideas.coronavirusinfo.http.rss.languages.english.providers.who.model.WhoFeed
import com.fairideas.coronavirusinfo.http.rss.languages.english.providers.who.model.channel.Channel

class WhoFeedMapper {
    companion object {

        fun mapToRSSNewsFeeds(whoFeed: WhoFeed) : List<NewsFeed> {
            val newsFeeds: MutableList<NewsFeed> = mutableListOf()
            val channel: Channel = whoFeed.channel
            for (item in channel.items) {
                val rssNewsFeed = ItemMapper.mapItemToRSSNewsFeed(item,
                    LocalesToParsingStrategies.EN_US.locale.displayLanguage,
                    RSSChannelEndpoints.WHO_BASE_URL)
                rssNewsFeed?.let { it ->  newsFeeds.add(it)}
            }
            return newsFeeds
        }
    }
}