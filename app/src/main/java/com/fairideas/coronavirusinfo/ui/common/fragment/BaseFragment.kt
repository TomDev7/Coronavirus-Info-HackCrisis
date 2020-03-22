package com.fairideas.coronavirusinfo.ui.common.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.fairideas.coronavirusinfo.ui.common.activity.MainActivity
import com.fairideas.coronavirusinfo.ui.common.viewmodel.BaseViewModel
import com.fairideas.coronavirusinfo.util.ConnectionLiveData

/**
 * Created by illia-herman on 24.02.20.
 */
abstract class BaseFragment(layoutId: Int) : Fragment(layoutId) {

    lateinit var baseViewModel: BaseViewModel
    lateinit var networkConnection: ConnectionLiveData

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarTitle()
        baseViewModel = ViewModelProviders.of(getMainActivity()).get(BaseViewModel::class.java)
        networkConnection = ConnectionLiveData(requireContext())

    }

    fun getMainActivity(): MainActivity = (activity as MainActivity)
    fun setToolbarTitle(text: String = getToolbarTitle()) {
        getMainActivity().setToolbarTitle(text)
    }

    abstract fun getToolbarTitle(): String

}