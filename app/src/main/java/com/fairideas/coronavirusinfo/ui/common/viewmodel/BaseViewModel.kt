package com.fairideas.coronavirusinfo.ui.common.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by illia-herman on 25.02.20.
 */
class BaseViewModel : ViewModel() {
    val isPermissionsGranted = MutableLiveData<Boolean>()
}