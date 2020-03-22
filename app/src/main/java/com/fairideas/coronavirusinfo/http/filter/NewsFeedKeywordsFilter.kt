package com.fairideas.coronavirusinfo.http.filter

import com.fairideas.coronavirusinfo.data.model.NewsFeed

abstract class NewsFeedKeywordsFilter {

    fun filterByKeywords(newsFeeds: ArrayList<NewsFeed>): ArrayList<NewsFeed> {
        val filteredNewsFeeds: ArrayList<NewsFeed> = ArrayList()
        for (rssNewsFeed in newsFeeds) {
            if (isCovidNewsFeed(rssNewsFeed)) {
                filteredNewsFeeds.add(rssNewsFeed)
            }
        }
        return filteredNewsFeeds
    }

    abstract fun isCovidNewsFeed(newsFeed: NewsFeed): Boolean

}