package com.fairideas.coronavirusinfo.ui.fragment.map.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.fairideas.coronavirusinfo.R
import com.fairideas.coronavirusinfo.data.DataStorage
import com.fairideas.coronavirusinfo.data.model.MapStyle
import com.fairideas.coronavirusinfo.ui.fragment.map.MapStyleDialog
import com.fairideas.coronavirusinfo.util.applyViewVisible
import kotlinx.android.synthetic.main.viewholder_map_item.view.*

/**
 * Created by illia-herman on 25.02.20.
 */
class MapStyleAdapter(val listener: MapStyleDialog.MapStyleListener) :
    RecyclerView.Adapter<MapStyleAdapter.MapStyleViewHolder>() {
    private val data = DataStorage.listOfMapStyle

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapStyleViewHolder {
        return MapStyleViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.viewholder_map_item, parent, false)
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MapStyleViewHolder, position: Int) {
        holder.bind(data[position], listener)
    }

    class MapStyleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title = itemView.tv_title
        private val checkedState = itemView.selected
        private val img = itemView.iv_img_example

        fun bind(item: MapStyle, listener: MapStyleDialog.MapStyleListener) {
            title.text = item.title
            img.setImageDrawable(ContextCompat.getDrawable(itemView.context, item.imgId))
            if (DataStorage.currentMapStyle == item.mapStyle) applyViewVisible(checkedState)
            itemView.setOnClickListener {
                listener.OnMapStyleClickListener(item)
            }

        }
    }

}