package com.fairideas.coronavirusinfo.ui.fragment.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.osmdroid.views.overlay.OverlayItem
import java.util.*

class MapViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is map Fragment"
    }
    val text: LiveData<String> = _text

    private val _contaminationMarkerItems = MutableLiveData<ArrayList<OverlayItem>>()
    val contaminationMarkerItems: LiveData<ArrayList<OverlayItem>> = _contaminationMarkerItems
}