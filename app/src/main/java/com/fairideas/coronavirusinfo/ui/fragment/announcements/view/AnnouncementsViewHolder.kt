package com.fairideas.coronavirusinfo.ui.fragment.announcements.view

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fairideas.coronavirusinfo.R
import com.fairideas.coronavirusinfo.data.model.NewsFeed
import com.fairideas.coronavirusinfo.util.GlideApp

class AnnouncementsViewHolder(val context: Context, inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.news_list_item, parent, false)) {

    private val titleView: TextView
    private val descriptionView: TextView
    private val sourceView: TextView
    val imageView: ImageView
    val view  : View


    init {
        titleView = itemView.findViewById(R.id.newsItemTitle)
        descriptionView = itemView.findViewById(R.id.newsItemTextPreview)
        imageView = itemView.findViewById(R.id.newsItemImage)
        sourceView = itemView.findViewById(R.id.newsItemSource)
        view = this.itemView
    }

    fun bind(newsFeed: NewsFeed) {
        titleView.text  = newsFeed.title
        titleView.setTypeface(null, Typeface.BOLD)
        descriptionView.text = newsFeed.description
        val sourceString = "${context.resources.getString(R.string.source_label)} ${newsFeed.copyright}"
        sourceView.text = sourceString
        GlideApp.with(context).load(newsFeed.imageUrl).into(imageView)

    }

}