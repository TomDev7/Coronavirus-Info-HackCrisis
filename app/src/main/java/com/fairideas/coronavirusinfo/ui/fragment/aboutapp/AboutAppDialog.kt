package com.fairideas.coronavirusinfo.ui.fragment.aboutapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.DialogFragment

import com.fairideas.coronavirusinfo.R
import kotlinx.android.synthetic.main.fragment_about_app.view.*
import kotlinx.android.synthetic.main.fragment_dialog_content.view.*
import kotlinx.android.synthetic.main.fragment_dialog_content.view.btn_close


class AboutAppDialog : DialogFragment() {

    protected lateinit var rootFragmentView: View
    protected var appName = ""
    protected var contact = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootFragmentView = inflater.inflate(R.layout.fragment_about_app, container, false)
        return rootFragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClickListeners()
    }

    private fun initView() {

        rootFragmentView.tv_appname.text = appName
        rootFragmentView.tv_contact.text = contact

        val webViewDescription: WebView = rootFragmentView.wv_description as WebView
        webViewDescription.loadUrl("file:///android_asset/about_app_text_pl.html")
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
        fun newInstance(appName: String, contact: String) =
            AboutAppDialog().apply {
                this.appName = appName
                this.contact = contact
            }

        val TAG = "AboutAppDialog"
    }
}
