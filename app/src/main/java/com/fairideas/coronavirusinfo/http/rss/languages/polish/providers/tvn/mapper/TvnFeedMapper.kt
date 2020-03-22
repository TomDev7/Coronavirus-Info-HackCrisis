package com.fairideas.coronavirusinfo.http.rss.languages.polish.providers.tvn.mapper

import com.fairideas.coronavirusinfo.data.model.NewsFeed
import com.fairideas.coronavirusinfo.http.rss.languages.polish.providers.tvn.model.Channel
import com.fairideas.coronavirusinfo.http.rss.languages.polish.providers.tvn.model.TvnFeed

class TvnFeedMapper {
    companion object {
        fun mapToRSSNewsFeeds(tvnFeed: TvnFeed) : List<NewsFeed> {
            val newsFeeds: MutableList<NewsFeed> = mutableListOf()
            val channel: Channel = tvnFeed.channel
            for (item in channel.items) {
                val rssNewsFeed = ItemMapper.mapItemToRSSNewsFeed(item, channel.language, channel.copyright)
                rssNewsFeed?.let { it ->  newsFeeds.add(it)}
            }
            return newsFeeds
        }
    }
}