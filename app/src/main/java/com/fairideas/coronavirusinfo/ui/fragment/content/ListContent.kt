package com.fairideas.coronavirusinfo.ui.fragment.content

import com.fairideas.coronavirusinfo.data.DataStorage
import com.fairideas.coronavirusinfo.data.model.Article
import com.fairideas.coronavirusinfo.data.model.NewsPiece
import java.util.ArrayList
import java.util.HashMap

/**
 * Created by illia-herman on 26.02.20.
 */
object ListContent {
    val ITEMS: MutableList<Article> = ArrayList()
    val ITEM_MAP: MutableMap<String, Article> = HashMap()

    init {
        DataStorage.articles?.let {

            it.forEach { piece ->
                addItem(piece)
            }
        }
    }

    private fun addItem(item: Article) {
        ITEMS.add(item)
        ITEM_MAP[item.id] = item
    }
}