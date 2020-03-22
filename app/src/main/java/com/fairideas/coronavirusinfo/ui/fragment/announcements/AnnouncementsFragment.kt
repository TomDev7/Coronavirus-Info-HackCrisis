package com.fairideas.coronavirusinfo.ui.fragment.announcements

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.fairideas.coronavirusinfo.R
import com.fairideas.coronavirusinfo.data.DataStorage
import com.fairideas.coronavirusinfo.data.model.NewsFeed
import com.fairideas.coronavirusinfo.ui.common.fragment.BaseFragment
import com.fairideas.coronavirusinfo.ui.fragment.announcements.view.AnnouncementsListAdapter
import com.fairideas.coronavirusinfo.ui.fragment.content.ContentFragment
import com.fairideas.coronavirusinfo.util.applyViewGone
import com.fairideas.coronavirusinfo.util.applyViewInVisible
import com.fairideas.coronavirusinfo.util.applyViewVisible
import kotlinx.android.synthetic.main.fragment_news.*

class AnnouncementsFragment : BaseFragment(R.layout.fragment_news){

    private lateinit var announcementsDialog: AnnouncementsDialog

    private var listener: OnListFragmentInteractionListener = object :
        OnListFragmentInteractionListener {
        override fun onListFragmentInteraction(item: NewsFeed?) {
            if (item != null) {
                announcementsDialog = AnnouncementsDialog.newInstance(contentImg = item.imageUrl,
                    contentTitle = item.title, content = item.description)
                announcementsDialog.show(childFragmentManager.beginTransaction(), ContentFragment.TAG)
            }
        }
    }

    override fun getToolbarTitle(): String = getString(R.string.toolbar_announcements)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAnnouncementsView()
    }


    private fun initAnnouncementsView() {
        applyViewInVisible(newsRecyclerView)
        observeAnnouncementsNewsFeedsFetching()
    }

    private fun observeAnnouncementsNewsFeedsFetching() {
        DataStorage.announcementsNewsFeeds.observe(viewLifecycleOwner, Observer {
                announcementsNewsFeeds ->
            if(announcementsNewsFeeds.isNotEmpty()) {
                applyViewGone(progress_bar)
                applyViewVisible(newsRecyclerView)
                newsRecyclerView.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = AnnouncementsListAdapter(context, announcementsNewsFeeds, listener)
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
            AnnouncementsFragment().apply {}
    }
}