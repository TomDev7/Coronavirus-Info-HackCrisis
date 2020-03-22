package com.fairideas.coronavirusinfo.data.model

import com.fairideas.coronavirusinfo.data.model.exception.NewsFeedException
import java.util.*

class NewsFeed(
    val title: String,
    val link: String,
    val imageUrl: String,
    val description: String,
    val pubDate: Date,
    val guid: String,
    val language: String,
    val copyright: String
) {

    init {
        if(title.isEmpty()) {
            throw NewsFeedException("Required title parameter is blank or empty")
        }
        if(imageUrl.isEmpty()) {
            throw NewsFeedException("Required imageUrl parameter is blank or empty")
        }
        if(description.isEmpty()) {
            throw NewsFeedException("Required description parameter is blank or empty")
        }
    }

    override fun toString(): String {
        return "NewsFeed(title='$title', link='$link', imageUrl='$imageUrl', " +
                "description='$description', " + "pubDate=$pubDate, guid='$guid', " +
                "language='$language', copyright='$copyright')"
    }
}