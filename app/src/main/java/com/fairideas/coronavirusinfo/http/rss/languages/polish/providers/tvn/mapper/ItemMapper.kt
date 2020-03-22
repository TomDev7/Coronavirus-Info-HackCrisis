package com.fairideas.coronavirusinfo.http.rss.languages.polish.providers.tvn.mapper

import android.annotation.SuppressLint
import android.util.Log
import com.fairideas.coronavirusinfo.data.model.NewsFeed
import com.fairideas.coronavirusinfo.data.model.exception.NewsFeedException
import com.fairideas.coronavirusinfo.http.rss.languages.polish.providers.tvn.model.Item
import org.jsoup.Jsoup
import java.text.ParseException
import java.text.SimpleDateFormat

class ItemMapper {
    companion object {
        const val DATE_PATTERN : String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

        @SuppressLint("SimpleDateFormat")
        fun mapItemToRSSNewsFeed(item: Item, language: String, copyright: String) : NewsFeed? {
            val sdf = SimpleDateFormat(DATE_PATTERN)
            val date = sdf.parse(item.pubDate)
            date?:
                throw ParseException("Couldn't parse date: ${item.pubDate} with pattern $DATE_PATTERN", 0)
            val descriptionDocument = Jsoup.parse(item.description)
            val imageElement = descriptionDocument.selectFirst("img")
            val imageUrl = imageElement.absUrl("src")
            val description : String = descriptionDocument.body().text()

            var newsFeed : NewsFeed? = null

            try {
                newsFeed = NewsFeed(
                    item.title, item.link, imageUrl,
                    description, date, item.guid, language, copyright
                )
            } catch (ex: NewsFeedException) {
                Log.e(ItemMapper::class.java.name, ex.message, ex)
            }
            return newsFeed
        }
    }
}