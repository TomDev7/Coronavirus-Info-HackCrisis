package com.fairideas.coronavirusinfo.ui.fragment.content

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fairideas.coronavirusinfo.R
import com.fairideas.coronavirusinfo.data.model.Article
import com.fairideas.coronavirusinfo.data.model.NewsPiece
import com.fairideas.coronavirusinfo.ui.common.dialog.ContentDialog
import com.fairideas.coronavirusinfo.ui.common.fragment.BaseFragment
import com.fairideas.coronavirusinfo.ui.fragment.content.adapter.ContentAdapter

/**
 * Created by illia-herman on 26.02.20.
 */
class ContentFragment : BaseFragment(/*R.layout.layout_under_construction*/R.layout.fragment_content_list) {

    override fun getToolbarTitle(): String = getString(R.string.toolbar_knowledge)

    private var columnCount = 1
    private lateinit var contentDialog: ContentDialog
    private var listener: OnListFragmentInteractionListener = object : OnListFragmentInteractionListener {
        override fun onListFragmentInteraction(item: Article?) {
            if (item != null) {
                contentDialog = ContentDialog.newInstance(contentImg = item.graphic, contentTitle = item.title, content = item.content_text)
                contentDialog.show(childFragmentManager.beginTransaction(), TAG)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter =
                    ContentAdapter(context, ListContent.ITEMS, listener)
            }
        }
    }

    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: Article?)
    }


    companion object {
        @JvmStatic
        fun newInstance(columnCount: Int = 1) =
            ContentFragment().apply {
                this.columnCount = columnCount
            }
        val TAG = "ContentFragment"
    }
}