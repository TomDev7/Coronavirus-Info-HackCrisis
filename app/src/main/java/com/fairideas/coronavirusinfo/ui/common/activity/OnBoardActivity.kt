package com.fairideas.coronavirusinfo.ui.common.activity

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.fairideas.coronavirusinfo.R
import com.fairideas.coronavirusinfo.data.DataStorage
import com.fairideas.coronavirusinfo.ui.fragment.onboard.adapter.LOCALIZATION_ONBOARD_FRAGMENT
import com.fairideas.coronavirusinfo.ui.fragment.onboard.adapter.OnBoardPagerAdapter
import com.fairideas.coronavirusinfo.util.PrefHelper
import com.fairideas.coronavirusinfo.util.requestPermissionsCompat
import kotlinx.android.synthetic.main.activity_onboard.*

class OnBoardActivity : AppCompatActivity(R.layout.activity_onboard) {

    var isStorageFragmentFinished = MutableLiveData<Boolean>()
    var isLocationFragmentFinished = MutableLiveData<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        observeLiveData()
    }

    private fun initView() {
        onBoardViewPager.adapter = OnBoardPagerAdapter(supportFragmentManager)
        indicator.setViewPager(onBoardViewPager)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {


        when (requestCode) {
            DataStorage.APP_STORAGE_REQUEST -> {
                isStorageFragmentFinished.postValue(true)
            }

            DataStorage.APP_LOCATION_REQUEST -> {
                isLocationFragmentFinished.postValue(true)
            }
        }
    }

    fun requestStoragePermission() = requestPermissionsCompat(
        arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ),
        DataStorage.APP_STORAGE_REQUEST
    )


    fun requestLocationPermission() = requestPermissionsCompat(
        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
        DataStorage.APP_LOCATION_REQUEST
    )

    private fun observeLiveData() {
        isStorageFragmentFinished.observe(this, Observer { isFinished ->
            if (isFinished) onBoardViewPager.currentItem = LOCALIZATION_ONBOARD_FRAGMENT
        })

        isLocationFragmentFinished.observe(this, Observer { isFinished ->
            if (isFinished) {
                PrefHelper.isFirstInit = false
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        })
    }
}
