package com.fairideas.coronavirusinfo.http.rss.languages.polish.providers.tvn.mapper

import android.util.Log
import com.fairideas.coronavirusinfo.http.rss.languages.polish.providers.tvn.model.TvnFeed
import org.simpleframework.xml.Serializer
import org.simpleframework.xml.core.Persister


class TvnAPIResponseMapper {

    companion object {
        private val TAG: String = TvnAPIResponseMapper::class.toString()

        fun mapToTvnFeed(response: String): TvnFeed? {
            val serializer: Serializer = Persister()
            var tvnFeed: TvnFeed? = null
            try {
                tvnFeed = serializer.read(TvnFeed::class.java, response)
            } catch (e: Exception) {
                Log.e(TAG, e.message, e)
            }
            return tvnFeed
        }
    }
}
