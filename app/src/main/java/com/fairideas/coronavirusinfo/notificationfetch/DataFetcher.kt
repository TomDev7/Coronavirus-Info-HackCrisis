package com.fairideas.coronavirusinfo.notificationfetch

import android.content.Context
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class DataFetcher(val context: Context) {

    companion object {

        val url = "http://www.zskdk.hekko24.pl"
    }

    fun fetchNewsHeadlines() {

        if (isNetworkConnected()) {
            FetchAsyncTask(context, url).execute()
        }
    }

    private fun isNetworkConnected(): Boolean {
        val cm: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return cm.activeNetwork != null && cm.activeNetworkInfo.isConnected()
    }
}

private class FetchAsyncTask(val context: Context, val url: String) : AsyncTask<Void, Void, Void>() {

    override fun doInBackground(vararg params: Void?): Void? {

        val document = Jsoup.connect(url).get()
        val newsSection: Element? = document?.select("div.items-leading")?.first()
        val newsHeadlines: Elements? = newsSection?.select("p")

        val news: ArrayList<NewsHeadline> = ArrayList()

        newsHeadlines?.forEach {

            val fields: Elements? = it.select("a")
            if (fields?.size == 2) {
                news.add(
                    NewsHeadline(
                        fields[0].text().toInt(),
                        fields[1].text(),
                        fields[1].attr("href")
                    )
                )
            }
        }

        if (news.size > 0) {

            val sharedPref = context.getSharedPreferences("CoronavirusInfoSharedPrefs", Context.MODE_PRIVATE)
            val lastHeadlineId = sharedPref.getInt("LastHeadlineId", 0)

            if (news.last().id > lastHeadlineId) {

                NotificationHandler(context).showNotification(news.last().title, news.last().url)
            }

            with (sharedPref.edit()) {
                putInt("LastHeadlineId", news.last().id)
                commit()
            }

        }

        return null
    }
}

private data class NewsHeadline(var id: Int, var title: String, var url: String)