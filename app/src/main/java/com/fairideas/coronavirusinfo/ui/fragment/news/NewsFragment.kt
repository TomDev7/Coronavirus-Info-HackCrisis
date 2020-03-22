package com.fairideas.coronavirusinfo.ui.fragment.news

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.fairideas.coronavirusinfo.R
import com.fairideas.coronavirusinfo.data.DataStorage
import com.fairideas.coronavirusinfo.data.model.NewsFeed
import com.fairideas.coronavirusinfo.ui.common.fragment.BaseFragment
import com.fairideas.coronavirusinfo.ui.fragment.content.ContentFragment
import com.fairideas.coronavirusinfo.ui.fragment.news.view.NewsListAdapter
import com.fairideas.coronavirusinfo.util.applyViewGone
import com.fairideas.coronavirusinfo.util.applyViewInVisible
import com.fairideas.coronavirusinfo.util.applyViewVisible
import kotlinx.android.synthetic.main.fragment_news.*


class NewsFragment : BaseFragment(R.layout.fragment_news) {

    private lateinit var newsDialog: NewsDialog

    private var listener: OnListFragmentInteractionListener = object :
        OnListFragmentInteractionListener {
        override fun onListFragmentInteraction(item: NewsFeed?) {
            if (item != null) {
                newsDialog = NewsDialog.newInstance(contentImg = item.imageUrl,
                    contentTitle = item.title, content = item.description)
                newsDialog.show(childFragmentManager.beginTransaction(), ContentFragment.TAG)
            }
        }
    }

    override fun getToolbarTitle(): String = getString(R.string.toolbar_news)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNewsView()
    }


    private fun initNewsView() {
        applyViewInVisible(newsRecyclerView)
        observeRSSNewsFeedsFetching()
    }

    private fun observeRSSNewsFeedsFetching() {
        DataStorage.rssNewsFeeds.observe(viewLifecycleOwner, Observer { rssNewsFeeds ->
            if(rssNewsFeeds.isNotEmpty()) {
                applyViewGone(progress_bar)
                applyViewVisible(newsRecyclerView)
                newsRecyclerView.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = NewsListAdapter(context, rssNewsFeeds, listener)
                }
            } else {
                applyViewVisible(progress_bar)
                applyViewInVisible(newsRecyclerView)
            }
        })
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: NewsFeed?)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            NewsFragment().apply {}
    }
}
