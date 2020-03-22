package com.fairideas.coronavirusinfo.ui.common.activity

import android.Manifest.permission
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.fairideas.coronavirusinfo.R
import com.fairideas.coronavirusinfo.data.DataStorage
import com.fairideas.coronavirusinfo.data.DataStorage.APP_PERMISSIONS_REQUEST
import com.fairideas.coronavirusinfo.http.rss.service.RSSNewsFeedService
import com.fairideas.coronavirusinfo.http.scrapper.service.HTTPScrappingService
import com.fairideas.coronavirusinfo.notificationfetch.FetchNotificationJobService
import com.fairideas.coronavirusinfo.notificationfetch.NotificationHandler
import com.fairideas.coronavirusinfo.ui.common.viewmodel.BaseViewModel
import com.fairideas.coronavirusinfo.ui.fragment.aboutapp.AboutAppDialog
import com.fairideas.coronavirusinfo.ui.fragment.announcements.AnnouncementsFragment
import com.fairideas.coronavirusinfo.ui.fragment.chart.ChartFragment
import com.fairideas.coronavirusinfo.ui.fragment.content.ContentFragment
import com.fairideas.coronavirusinfo.ui.fragment.map.MapFragment
import com.fairideas.coronavirusinfo.ui.fragment.news.NewsFragment
import com.fairideas.coronavirusinfo.util.ConnectionLiveData
import com.fairideas.coronavirusinfo.util.checkSelfPermissionCompat
import com.fairideas.coronavirusinfo.util.requestPermissionsCompat
import com.fairideas.coronavirusinfo.util.shouldShowRequestPermissionRationaleCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class MainActivity : AppCompatActivity(R.layout.activity_main),
    ActivityCompat.OnRequestPermissionsResultCallback {

    private lateinit var baseViewModel: BaseViewModel
    private lateinit var networkConnection: ConnectionLiveData
    private lateinit var fragmentTransaction: FragmentTransaction
    private val clickDelay: Long = 300L
    private var isBottomClickPossible = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            openFragment(MapFragment.newInstance())
        }
        networkConnection = ConnectionLiveData(context = this)
        handleBottomNavigation()
        initViewModels()
        showPermissionsPreview()
        setToolbarMenu()
        NotificationHandler(this).registerNotificationChannel()
        startJobScheduler()
        initObserver()
    }

    private fun initObserver() {
        DataStorage.rssNewsFeeds.observe(this , Observer {
            it?.forEach { rssNewsFeed -> Log.d("RSS_NEWS_FEED", rssNewsFeed.toString()) }
        })
        networkConnection.observe(this, Observer { isAvailable ->
            if (isAvailable) {
                RSSNewsFeedService().fetchLocalizedFeedsAndAddToDataStorage()
                HTTPScrappingService().fetchLocalizedFeedsAndAddToDataStorage()
                val temp = DataStorage
                temp.getContaminationAreas()
                temp.getTips()
                temp.getNews()
                temp.getArticles()
            }
        })
    }


    private fun handleBottomNavigation() {
        bottom_navigation.menu.findItem(R.id.navigation_map).setChecked(true)
        bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun initViewModels() {
        baseViewModel =
            ViewModelProviders.of(this).get(BaseViewModel::class.java)
    }

    fun openFragment(fragment: Fragment, isRoot: Boolean = true) {
        if (!isFinishing) {
            val canonicalName = fragment.javaClass.canonicalName
            fragmentTransaction = supportFragmentManager.beginTransaction()
            when {
                isRoot -> fragmentTransaction.replace(R.id.main_container, fragment, canonicalName)
                else -> {
                    fragmentTransaction.replace(R.id.main_container, fragment, canonicalName)
                    fragmentTransaction.addToBackStack(canonicalName)
                }
            }
            fragmentTransaction.commit()
        }

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == APP_PERMISSIONS_REQUEST) {
            if (grantResults.size == 3 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED
                && grantResults[2] == PackageManager.PERMISSION_GRANTED
            ) {
                baseViewModel.isPermissionsGranted.postValue(true)
            } else {
                baseViewModel.isPermissionsGranted.postValue(false)

                //When user click "Don't ask again" on permission then open the settings page of the app
                if (!shouldShowRequestPermissionRationaleCompat(permission.ACCESS_FINE_LOCATION)
                    || !shouldShowRequestPermissionRationaleCompat(permission.WRITE_EXTERNAL_STORAGE)
                    || !shouldShowRequestPermissionRationaleCompat(permission.READ_EXTERNAL_STORAGE)
                ) {
                    val intent = Intent()
                    val uri: Uri = Uri.fromParts("package", this.packageName, null)
                    intent.apply {
                        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        data = uri
                    }
                    startActivity(intent)
                }

            }
        }
    }

    fun showPermissionsPreview() {
        if (checkSelfPermissionCompat(permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED
            && checkSelfPermissionCompat(permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
            && checkSelfPermissionCompat(permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        ) {
            baseViewModel.isPermissionsGranted.postValue(true)
        } else {
            requestPermissions()
        }
    }

    private fun requestPermissions() {
        if (shouldShowRequestPermissionRationaleCompat(permission.ACCESS_FINE_LOCATION)
            && shouldShowRequestPermissionRationaleCompat(permission.WRITE_EXTERNAL_STORAGE)
            && shouldShowRequestPermissionRationaleCompat(permission.READ_EXTERNAL_STORAGE)
        ) {
            requestPermissionsCompat(
                arrayOf(
                    permission.ACCESS_FINE_LOCATION,
                    permission.WRITE_EXTERNAL_STORAGE,
                    permission.READ_EXTERNAL_STORAGE
                ),
                APP_PERMISSIONS_REQUEST
            )

        } else {
            requestPermissionsCompat(
                arrayOf(
                    permission.ACCESS_FINE_LOCATION,
                    permission.WRITE_EXTERNAL_STORAGE,
                    permission.READ_EXTERNAL_STORAGE
                ),
                APP_PERMISSIONS_REQUEST
            )
        }
    }

    private var mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            if (isBottomClickPossible) {
                when (item.itemId) {
                    R.id.navigation_knowledge -> {
                        openFragment(ContentFragment.newInstance(1))
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.navigation_map -> {
                        openFragment(MapFragment.newInstance())
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.navigation_tips -> {
                        openFragment(AnnouncementsFragment.newInstance())
                        return@OnNavigationItemSelectedListener true
                    }

                    R.id.navigation_stats -> {
                        openFragment(ChartFragment.newInstance())
                        return@OnNavigationItemSelectedListener true
                    }

                    R.id.navigation_news -> {
                        openFragment(NewsFragment.newInstance())
                        return@OnNavigationItemSelectedListener true
                    }

                    else -> {
                    }
                }
                setBottomClickDelay()
            }
            false
        }

    private fun setBottomClickDelay() {
        isBottomClickPossible = false
        Handler().postDelayed({
            isBottomClickPossible = true
        }, clickDelay)
    }

    fun setToolbarTitle(text: String) {
        toolbar.tv_toolbar_title.text = text
    }

    private fun setToolbarMenu() {
        toolbar.inflateMenu(R.menu.main)
        toolbar.setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener {

            when (it.itemId) {

                R.id.action_information -> {

                    val aboutAppDialog = AboutAppDialog.newInstance(
                        resources.getString(R.string.app_name),
                        resources.getString(R.string.contact_info)
                    )
                    aboutAppDialog.show(
                        supportFragmentManager.beginTransaction(),
                        AboutAppDialog.TAG
                    )
                    true
                }

                R.id.action_share -> {

                    shareApp()
                    true
                }

                else -> {
                    false
                }
            }
        })
    }

    private fun shareApp() {

        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.setType("text/plain")
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, resources.getString(R.string.app_name))
        sharingIntent.putExtra(Intent.EXTRA_TEXT, resources.getString(R.string.share_app_text))
        startActivity(Intent.createChooser(sharingIntent, "Share via"))
    }

    private fun startJobScheduler() {

        val jobScheduler: JobScheduler =
            applicationContext.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val componentName = ComponentName(this, FetchNotificationJobService::class.java)
        val jobInfo: JobInfo =
            JobInfo.Builder(1, componentName).setPeriodic(7200000).setPersisted(true)
                .build() //15 min is the minimum set by Android
        jobScheduler.schedule(jobInfo)
    }

    companion object {
        val TAG = "MainActivity"
    }
}
