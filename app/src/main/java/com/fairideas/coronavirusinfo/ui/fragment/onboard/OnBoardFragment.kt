package com.fairideas.coronavirusinfo.ui.fragment.onboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.fairideas.coronavirusinfo.R
import com.fairideas.coronavirusinfo.ui.common.activity.OnBoardActivity
import kotlinx.android.synthetic.main.fragment_onboard.*

/**
 * Created by illia-herman on 04.03.20.
 */

const val STORAGE_VIEW_TYPE = "storageViewType"
const val LOCALIZATION_VIEW_TYPE = "localizationViewType"

class OnBoardFragment : Fragment(R.layout.fragment_onboard) {

    private lateinit var viewType: String
    private lateinit var onBoardActivity: OnBoardActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBoardActivity = (activity as OnBoardActivity)
        setViewType()
    }

    private fun setViewType() {
        when (viewType) {
            STORAGE_VIEW_TYPE -> {
                setViewStrings(
                    getString(R.string.turn_on_storage),
                    getString(R.string.storage_explanation),
                    getString(R.string.btn_grant_permissions),
                    getString(R.string.later)
                )

                postponeTextView.setOnClickListener {
                    onBoardActivity.isStorageFragmentFinished.postValue(true)
                }

                affirmativeButton.setOnClickListener {
                    onBoardActivity.requestStoragePermission()
                }
            }

            LOCALIZATION_VIEW_TYPE -> {
                setViewStrings(
                    getString(R.string.turn_on_localization),
                    getString(R.string.find_my_location),
                    getString(R.string.btn_grant_permissions),
                    getString(R.string.later)
                )
                postponeTextView.setOnClickListener {
                    onBoardActivity.isLocationFragmentFinished.postValue(true)
                }

                affirmativeButton.setOnClickListener {
                    (activity as OnBoardActivity).requestLocationPermission()
                }
            }
        }
    }

    private fun setViewStrings(
        title: String,
        subtitle: String,
        affirmativeButtonText: String,
        postponeText: String
    ) {
        onBoardTitleTextView.text = title
        onBoardSubtitleTextView.text = subtitle
        affirmativeButton.text = affirmativeButtonText
        postponeTextView.text = postponeText
    }

    companion object {
        fun newInstance(viewType: String) = OnBoardFragment().apply {
            this.viewType = viewType
        }
    }
}