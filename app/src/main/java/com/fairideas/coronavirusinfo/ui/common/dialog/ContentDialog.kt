package com.fairideas.coronavirusinfo.ui.common.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.DialogFragment
import com.fairideas.coronavirusinfo.R
import com.fairideas.coronavirusinfo.util.GlideApp
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_dialog_content.*
import kotlinx.android.synthetic.main.fragment_dialog_content.view.*
import kotlinx.android.synthetic.main.fragment_dialog_content.view.tv_title
import kotlinx.android.synthetic.main.permission_placeholder.view.*

/**
 * Created by illia-herman on 25.02.20.
 */
class ContentDialog : DialogFragment() {

    protected lateinit var rootFragmentView: View
    protected var contentImg: String? = null
    protected var contentTitle = ""
    protected var content = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootFragmentView = inflater.inflate(R.layout.fragment_dialog_content, container, false)
        return rootFragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClickListeners()
    }

    private fun initView() {
        contentImg?.let {
            val storageReference = FirebaseStorage.getInstance().reference
            val imagesReference = storageReference.child("articles_graphics")
            val imageReference = imagesReference.child(it)
            GlideApp.with(this).load(imageReference).into(iv_content_image)
        }
        rootFragmentView.tv_title.text = contentTitle
        rootFragmentView.tv_content.setText(HtmlCompat.fromHtml(content,HtmlCompat.FROM_HTML_MODE_COMPACT))

    }

    private fun initClickListeners() {
        rootFragmentView.btn_close.setOnClickListener {
            dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }


    companion object {
        fun newInstance(contentImg: String? = null, contentTitle: String = "", content: String = "") =
            ContentDialog().apply {
                this.contentImg = contentImg
                this.contentTitle = contentTitle
                this.content = content
            }

        val TAG = "ContentDialog"
    }
}