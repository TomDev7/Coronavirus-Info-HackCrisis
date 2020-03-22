package com.fairideas.coronavirusinfo.ui.fragment.news.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fairideas.coronavirusinfo.data.model.NewsFeed
import com.fairideas.coronavirusinfo.ui.fragment.news.NewsFragment

class NewsListAdapter(
    private val appContext: Context,
    private val list: ArrayList<NewsFeed>,
    private val mListener: NewsFragment.OnListFragmentInteractionListener?)
    : RecyclerView.Adapter<NewsViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as NewsFeed

            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NewsViewHolder(parent.context, inflater, parent)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsFeed: NewsFeed = list[position]
        holder.bind(newsFeed)

        with(holder.view) {
            tag = newsFeed
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = list.size

}