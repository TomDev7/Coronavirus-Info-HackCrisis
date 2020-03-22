package com.fairideas.coronavirusinfo.http.filter.polish

import com.fairideas.coronavirusinfo.data.model.NewsFeed
import com.fairideas.coronavirusinfo.data.model.constants.polish.CoronavirusKeyword
import com.fairideas.coronavirusinfo.http.filter.NewsFeedKeywordsFilter

class PolishNewsFeedKeywordsFilter : NewsFeedKeywordsFilter() {

    @Override
    override fun isCovidNewsFeed(newsFeed: NewsFeed): Boolean {
        for (value in CoronavirusKeyword.values()) {
            for (keyword in value.searchPhrases) {
                if (newsFeed.title.contains(keyword, true)
                    || newsFeed.description.contains(keyword, true)) {
                    return true
                }
            }
        }
        return false
    }
}