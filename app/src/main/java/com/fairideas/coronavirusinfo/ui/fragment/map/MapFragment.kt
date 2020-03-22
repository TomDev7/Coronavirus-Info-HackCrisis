package com.fairideas.coronavirusinfo.ui.fragment.map

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.fairideas.coronavirusinfo.R
import com.fairideas.coronavirusinfo.data.DataStorage
import com.fairideas.coronavirusinfo.data.model.MapStyle
import com.fairideas.coronavirusinfo.ui.common.fragment.BaseFragment
import com.fairideas.coronavirusinfo.util.PrefHelper
import com.fairideas.coronavirusinfo.util.applyViewGone
import com.fairideas.coronavirusinfo.util.applyViewVisible
import com.fairideas.coronavirusinfo.util.createBitmap
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.android.synthetic.main.permission_placeholder.*
import kotlinx.android.synthetic.main.popup_marker_info.*
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay


class MapFragment : BaseFragment(R.layout.fragment_map) {

    override fun getToolbarTitle(): String = getString(R.string.toolbar_map)

    private lateinit var mapViewModel: MapViewModel
    private lateinit var currentLocationOverlay: MyLocationNewOverlay
    private lateinit var mapController: IMapController
    private lateinit var mapStyleDialog: MapStyleDialog
    private var personBitmap: Bitmap? = null
    private var totalCases = 0

    private var mapStyleListener = object : MapStyleDialog.MapStyleListener {
        override fun OnMapStyleClickListener(mapStyle: MapStyle) {
            map.setTileSource(mapStyle.mapStyle)
            DataStorage.currentMapStyle = mapStyle.mapStyle
            mapStyleDialog.dismiss()
        }

    }

    private val mapEventsReceiver = object : MapEventsReceiver {
        override fun longPressHelper(p: GeoPoint?): Boolean {
            return true
        }

        override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {
            return true
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModels()
        observeViewModels()
        initMap()
        handleClickListeners()
        observeLiveData()
    }


    private fun initView() {
        mapStyleDialog = MapStyleDialog.newInstance(mapStyleListener)
        personBitmap =
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_person_marker)?.let {
                createBitmap(
                    it
                )
            }
    }

    private fun observeLiveData() {
        DataStorage.areasLiveData.observe(viewLifecycleOwner, Observer {
            if (it) {
                addContaminationMarkerOverlays()
            }
        })
    }


    private fun initViewModels() {
        mapViewModel =
            ViewModelProviders.of(getMainActivity()).get(MapViewModel::class.java)
    }

    private fun observeViewModels() {
        baseViewModel.isPermissionsGranted.observe(viewLifecycleOwner, Observer {
            if (it) showMap() else hideMap()
        })
    }

    private fun initMap() {
        map.apply {
            setTileSource(DataStorage.currentMapStyle)
            maxZoomLevel = 11.0
            minZoomLevel = 3.4
            setMultiTouchControls(true)
            zoomController.setVisibility(CustomZoomButtonsController.Visibility.SHOW_AND_FADEOUT)
            mapController = this.controller
            isHorizontalMapRepetitionEnabled = true
            isVerticalMapRepetitionEnabled = false
            setScrollableAreaLimitLatitude(
                MapView.getTileSystem().maxLatitude,
                MapView.getTileSystem().minLatitude,
                0
            )
        }
        val startLocation = GeoPoint(52.2814, 20.8648)
        mapController.setCenter(startLocation)
        mapController.setZoom(3.5)
        addCurrentLocationOverlay()
    }

    private fun handleClickListeners() {
        btn_select_map.setOnClickListener {
            mapStyleDialog.show(childFragmentManager, MapStyleDialog.TAG)
        }

        btn_mylocation.setOnClickListener {
            mapController.animateTo(currentLocationOverlay.myLocation)
        }

        btn_grant_permission.setOnClickListener {
            getMainActivity().showPermissionsPreview()
        }
    }


    fun addCurrentLocationOverlay() {
        currentLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(context), map)
        currentLocationOverlay.apply {
            enableMyLocation()
            setPersonHotspot(28.0f, 28.0f) // should be the same as icon size
            setPersonIcon(personBitmap)
        }
        map.overlays.apply {
            add(currentLocationOverlay)
            add(MapEventsOverlay(mapEventsReceiver))
        }
    }

    private fun addContaminationMarkerOverlays() {
        DataStorage.contaminationAreas?.forEach {
            map.overlays
                .add(
                    createMarker(
                        it.latitude.toDouble(),
                        it.longitude.toDouble(),
                        it.num_of_infected,
                        it.num_of_recoveries,
                        it.num_of_deaths,
                        it.radius,
                        it.country
                    )
                )
            totalCases += it.num_of_infected
        }
        if (isFirstInit) {
            mapController.zoomOut(1000L)
            isFirstInit = false
        }
    }


    private fun createMarker(
        latitude: Double,
        longitude: Double,
        numberOfInfected: Int,
        numberOfRecovered: Int,
        numberOfDeaths: Int,
        radius: Int,
        country: String
    ): Marker {
        val m = Marker(map)
        val jDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_infected_dot)
        var mRadius = radius
        var bitmapDrawable: BitmapDrawable? = null

        when {
            mRadius == 2 -> mRadius--
            mRadius >= 3 -> mRadius -= 2
        }

        if (jDrawable != null) {
            val bitmap = Bitmap.createBitmap(
                jDrawable.intrinsicWidth * mRadius,
                jDrawable.intrinsicHeight * mRadius,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            jDrawable.setBounds(0, 0, canvas.width, canvas.height)
            jDrawable.draw(canvas)
            bitmapDrawable = BitmapDrawable(requireContext().resources, bitmap)
        }

        m.apply {
            position = GeoPoint(latitude, longitude)
            icon = bitmapDrawable
            setOnMarkerClickListener { marker, mapView ->
                initMarkerInfoPopup(
                    country = country,
                    infected = numberOfInfected,
                    recovered = numberOfRecovered,
                    deaths = numberOfDeaths
                )
                true
            }
            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
        }
        return m
    }

    private fun initMarkerInfoPopup(country: String, infected: Int, recovered: Int, deaths: Int) {
        applyViewVisible(marker_container)
        tv_location.text = country
        tv_cases.text = getString(R.string.number_of_infected, infected)
        tv_recovered.text = getString(R.string.number_of_recovered, recovered)
        tv_deaths.text = getString(R.string.number_of_deaths, deaths)
        tv_cases_all.text = getString(R.string.total_of_infected, totalCases)
        iv_close.setOnClickListener { applyViewGone(marker_container) }
    }


    private fun showMap() {
        applyViewGone(placeholder_permissions)
        applyViewVisible(map, btn_mylocation, btn_select_map)
    }

    private fun hideMap() {
        applyViewGone(map, btn_mylocation, btn_select_map)
        applyViewVisible(placeholder_permissions)
    }

    override fun onResume() {
        super.onResume()
        Configuration.getInstance().load(requireContext(), PrefHelper.preferences)
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        Configuration.getInstance().save(requireContext(), PrefHelper.preferences)
        map.onPause()
    }


    companion object {
        private var isFirstInit = true

        @JvmStatic
        fun newInstance() = MapFragment()

    }
}