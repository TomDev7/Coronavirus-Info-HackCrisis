package com.fairideas.coronavirusinfo.ui.fragment.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.fairideas.coronavirusinfo.R
import com.fairideas.coronavirusinfo.util.GlideApp
import kotlinx.android.synthetic.main.fragment_dialog_content.*
import kotlinx.android.synthetic.main.fragment_dialog_content.view.*

class NewsDialog : DialogFragment() {

    protected lateinit var rootFragmentView: View
    protected var contentImg: String = ""
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
        GlideApp.with(this).load(contentImg).into(iv_content_image)
        rootFragmentView.tv_title.text = contentTitle
        rootFragmentView.tv_content.setText(content)
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
        fun newInstance(contentImg: String = "", contentTitle: String = "", content: String = "") =
            NewsDialog().apply {
                this.contentImg = contentImg
                this.contentTitle = contentTitle
                this.content = content
            }
        val TAG = "ContentDialog"
    }
}