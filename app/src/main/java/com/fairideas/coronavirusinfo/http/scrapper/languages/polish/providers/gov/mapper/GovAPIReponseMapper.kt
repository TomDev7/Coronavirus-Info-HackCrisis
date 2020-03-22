package com.fairideas.coronavirusinfo.http.scrapper.languages.polish.providers.gov.mapper

import android.annotation.SuppressLint
import android.util.Log
import com.fairideas.coronavirusinfo.data.model.NewsFeed
import com.fairideas.coronavirusinfo.http.scrapper.constants.LocalesToScrappingStrategies
import com.fairideas.coronavirusinfo.http.scrapper.languages.polish.constants.ScrapperEndpoint
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.text.ParseException
import java.text.SimpleDateFormat

class GovAPIReponseMapper {

    companion object {

        private const val NEWS_LIST_DIV = "art-prev art-prev--near-menu"
        private const val A_CLASS = "a"
        private const val UNORDERED_LIST_CLASS = "ul"
        private const val LIST_ITEM_CLASS = "li"
        private const val SPAN_CLASS = "span"
        private const val DIV_CLASS = "div"
        private const val PICTURE_CLASS = "picture"
        private const val SOURCE_CLASS = "source"
        private const val HREF_ATTRIBUTE = "href"
        private const val DATE_ATTRIBUTE = "date"
        private const val TITLE_ATTRIBUTE = "title"
        private const val INTRO_ATTRIBUTE = "intro"
        private const val SRC_SET_ATTRIBUTE = "srcSet"

        private const val DATE_PATTERN = "dd.MM.yyyy"

        private val TAG = GovAPIReponseMapper::class.java.name


        fun mapToNewsFeeds(body: String): ArrayList<NewsFeed> {
            val document = Jsoup.parse(body)
            val elements = document.getElementsByClass(NEWS_LIST_DIV)
            val ul = elements.select(UNORDERED_LIST_CLASS)
            val litems = ul.select(LIST_ITEM_CLASS)

            val newsFeeds: ArrayList<NewsFeed> = ArrayList()

            for (element in litems) {
                try{
                    newsFeeds.add(mapElementToNewsFeed(element))
                } catch (ex: Exception) {
                    Log.e(TAG, ex.message, ex)
                }
            }
            return newsFeeds
        }

        @SuppressLint("SimpleDateFormat")
        private fun mapElementToNewsFeed(listItem: Element) : NewsFeed {
            val a = listItem.select(A_CLASS).first()
            val relativeNewsUrl = a.attr(HREF_ATTRIBUTE)
            val dateUnformatted = a.selectFirst("$SPAN_CLASS.$DATE_ATTRIBUTE").text()
            val title = a.selectFirst("$DIV_CLASS.$TITLE_ATTRIBUTE").text()
            val description = a.selectFirst("$DIV_CLASS.$INTRO_ATTRIBUTE").text()
            val relativeImageSet = a.selectFirst(PICTURE_CLASS)
                .selectFirst(SOURCE_CLASS).attr(SRC_SET_ATTRIBUTE)
            val relativeImageUrlsAndWidths = relativeImageSet.split("\n")
            val relativeImageUrl = relativeImageUrlsAndWidths[relativeImageUrlsAndWidths.size-1]
                .split(" ")[0]

            val sdf = SimpleDateFormat(DATE_PATTERN)
            val date = sdf.parse(dateUnformatted)
            date?:
            throw ParseException("Couldn't parse date: $dateUnformatted with pattern $DATE_PATTERN", 0)

            return NewsFeed(
                title,
                ScrapperEndpoint.GOV_BASE_URL + relativeNewsUrl,
                ScrapperEndpoint.GOV_BASE_URL + relativeImageUrl,
                description,
                date,
                "",
                LocalesToScrappingStrategies.PL.locale.language,
                ScrapperEndpoint.GOV_BASE_URL)
        }
    }
}