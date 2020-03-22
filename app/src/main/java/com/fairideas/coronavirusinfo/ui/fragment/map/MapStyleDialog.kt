package com.fairideas.coronavirusinfo.ui.fragment.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.fairideas.coronavirusinfo.R
import com.fairideas.coronavirusinfo.data.model.MapStyle
import com.fairideas.coronavirusinfo.ui.fragment.map.adapter.MapStyleAdapter
import kotlinx.android.synthetic.main.dialog_map_style.view.*


/**
 * Created by illia-herman on 24.02.20.
 */
class MapStyleDialog : DialogFragment() {

    protected lateinit var rootFragmentView: View
    protected lateinit var listener: MapStyleListener

    init {
        setStyle(STYLE_NO_TITLE, R.style.MapDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootFragmentView = inflater.inflate(R.layout.dialog_map_style, container, false)
        return rootFragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initClickListeners()
    }

    private fun initClickListeners() {
        rootFragmentView.btn_close.setOnClickListener {
            dismiss()
        }
    }

    private fun initAdapter() {
        rootFragmentView.recycler_view.adapter = MapStyleAdapter(listener)
    }

    interface MapStyleListener {
        fun OnMapStyleClickListener(mapStyle: MapStyle)
    }


    companion object {
        fun newInstance(listener: MapStyleListener) = MapStyleDialog().apply {
            this.listener = listener
        }
        val TAG = "MapStyleDialog"
    }
}