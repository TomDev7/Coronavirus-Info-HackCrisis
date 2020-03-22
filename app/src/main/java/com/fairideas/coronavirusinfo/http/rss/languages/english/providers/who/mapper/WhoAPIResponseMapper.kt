package com.fairideas.coronavirusinfo.http.rss.languages.english.providers.who.mapper

import android.util.Log
import com.fairideas.coronavirusinfo.http.rss.languages.english.providers.who.model.WhoFeed
import org.simpleframework.xml.Serializer
import org.simpleframework.xml.core.Persister

class WhoAPIResponseMapper {

    companion object {
        private val TAG: String = WhoAPIResponseMapper::class.toString()

        fun mapToWhoFeed(response: String): WhoFeed? {
            val serializer: Serializer = Persister()
            var whoFeed: WhoFeed? = null
            try {
                whoFeed = serializer.read(WhoFeed::class.java, response, false)
            } catch (e: Exception) {
                Log.e(TAG, e.message, e)
            }
            return whoFeed
        }
    }
}