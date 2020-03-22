package com.fairideas.coronavirusinfo.http.rss.languages.english.providers.who.mapper

import android.annotation.SuppressLint
import android.util.Log
import com.fairideas.coronavirusinfo.data.model.NewsFeed
import com.fairideas.coronavirusinfo.data.model.exception.NewsFeedException
import com.fairideas.coronavirusinfo.http.rss.languages.english.providers.who.model.item.Item
import org.jsoup.Jsoup
import java.text.ParseException
import java.text.SimpleDateFormat

class ItemMapper {
    companion object {
        const val DATE_PATTERN : String = "EEE, dd MMM yyyy HH:mm:ss 'Z'"
        const val IMAGE_URL : String = "https://seeklogo.com/images/W/WHO-logo-04FEF5660B-seeklogo.com.png"

        @SuppressLint("SimpleDateFormat")
        fun mapItemToRSSNewsFeed(item: Item, language: String, copyright: String) : NewsFeed? {
            val sdf = SimpleDateFormat(DATE_PATTERN)
            val date = sdf.parse(item.pubDate)
            date?:
            throw ParseException("Couldn't parse date: ${item.pubDate} with pattern $DATE_PATTERN", 0)
            val descriptionDocument =
                if (item.content.value.isNotBlank() and item.content.value.isNotEmpty()) {
                    Jsoup.parse(item.content.value)
                } else {
                    Jsoup.parse(item.description)
                }

            val description : String = descriptionDocument.body().text()

            var newsFeed : NewsFeed? = null

            try {
                newsFeed = NewsFeed(
                    item.title, item.link, IMAGE_URL,
                    description, date, item.guid.value, language, copyright
                )
            } catch (ex: NewsFeedException) {
                Log.e(ItemMapper::class.java.name, ex.message, ex)
            }
            return newsFeed
        }
    }
}