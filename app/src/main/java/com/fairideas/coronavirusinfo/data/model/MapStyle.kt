package com.fairideas.coronavirusinfo.data.model

import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase

/**
 * Created by illia-herman on 25.02.20.
 */

data class MapStyle(
    val title: String,
    val mapStyle: OnlineTileSourceBase,
    val imgId: Int
)