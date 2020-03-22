package com.fairideas.coronavirusinfo.ui.fragment.onboard.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.fairideas.coronavirusinfo.ui.fragment.onboard.LOCALIZATION_VIEW_TYPE
import com.fairideas.coronavirusinfo.ui.fragment.onboard.OnBoardFragment
import com.fairideas.coronavirusinfo.ui.fragment.onboard.STORAGE_VIEW_TYPE

/**
 * Created by illia-herman on 04.03.20.
 */
const val NUM_PAGES = 2
const val STORAGE_ONBOARD_FRAGMENT = 0
const val LOCALIZATION_ONBOARD_FRAGMENT = 1

class OnBoardPagerAdapter(fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val storageOnBoardFragment = OnBoardFragment.newInstance(STORAGE_VIEW_TYPE)
    private val localizationOnBoardFragment = OnBoardFragment.newInstance(LOCALIZATION_VIEW_TYPE)

    override fun getItem(position: Int): Fragment {
        return when (position) {
            STORAGE_ONBOARD_FRAGMENT -> {
                storageOnBoardFragment
            }
            LOCALIZATION_ONBOARD_FRAGMENT -> {
                localizationOnBoardFragment
            }
            else -> throw Exception("NO FRAGMENT FOUND in ${this.javaClass}")
        }
    }

    override fun getCount(): Int = NUM_PAGES

}