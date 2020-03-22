package com.fairideas.coronavirusinfo.ui.fragment.content.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fairideas.coronavirusinfo.R
import com.fairideas.coronavirusinfo.data.model.Article
import com.fairideas.coronavirusinfo.ui.fragment.content.ContentFragment
import com.fairideas.coronavirusinfo.util.GlideApp
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_content.view.*

/**
 * Created by illia-herman on 26.02.20.
 */
class ContentAdapter(
    private val appContext: Context,
    private val mValues: List<Article>,
    private val mListener: ContentFragment.OnListFragmentInteractionListener?
) : RecyclerView.Adapter<ContentAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Article

            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.titleView.text = item.title

        val storageReference = FirebaseStorage.getInstance().reference
        val imagesReference = storageReference.child("articles_graphics")
        val imageReference = imagesReference.child(item.graphic)
        GlideApp.with(appContext).load(imageReference).into(holder.imageView)

        holder.contentPreviewView.text = item.content_text

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val titleView: TextView = mView.cardTitle
        val imageView: ImageView = mView.cardImage
        val contentPreviewView: TextView = mView.cardTitle

        override fun toString(): String {
            return super.toString() + " '" + titleView.text + "', '" + contentPreviewView.text
        }
    }
}
