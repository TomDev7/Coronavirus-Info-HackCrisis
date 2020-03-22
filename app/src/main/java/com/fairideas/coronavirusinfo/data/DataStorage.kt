package com.fairideas.coronavirusinfo.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.fairideas.coronavirusinfo.R
import com.fairideas.coronavirusinfo.data.model.*
import com.google.firebase.firestore.FirebaseFirestore
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import java.text.DecimalFormat

object DataStorage {

    val APP_PERMISSIONS_REQUEST = 0
    val APP_STORAGE_REQUEST = 1
    val APP_LOCATION_REQUEST = 2

    val firebase: FirebaseFirestore
    var contaminationAreas: ArrayList<ContaminationArea>?
    var tips: ArrayList<Tip>?
    var articles: ArrayList<Article>?
    var news: ArrayList<NewsPiece>?
    var areasLiveData = MutableLiveData<Boolean>()
    var listOfMapStyle: ArrayList<MapStyle> = ArrayList()
    var currentMapStyle = TileSourceFactory.MAPNIK
    val rssNewsFeeds : MutableLiveData<ArrayList<NewsFeed>>
    val announcementsNewsFeeds : MutableLiveData<ArrayList<NewsFeed>>
    init {

        firebase = FirebaseFirestore.getInstance()
        contaminationAreas = ArrayList()
        tips = ArrayList()
        articles = ArrayList()
        news = ArrayList()
        rssNewsFeeds = MutableLiveData()
        rssNewsFeeds.value = ArrayList()
        announcementsNewsFeeds = MutableLiveData()
        announcementsNewsFeeds.value = ArrayList()
        initMapStyles()
    }

    fun initMapStyles() {
        listOfMapStyle.addAll(
            arrayListOf(
                MapStyle(
                    title = "MAPNIK",
                    mapStyle = TileSourceFactory.MAPNIK,
                    imgId = R.drawable.img_mapnik
                ),
                MapStyle(
                    title = "USGS SAT",
                    mapStyle = TileSourceFactory.USGS_SAT,
                    imgId = R.drawable.img_usgs_sat
                ),
                MapStyle(
                    title = "USGS TOPO",
                    mapStyle = TileSourceFactory.USGS_TOPO,
                    imgId = R.drawable.img_usgs_topo
                ),
                MapStyle(
                    title = "OpenTopo",
                    mapStyle = TileSourceFactory.OpenTopo,
                    imgId = R.drawable.img_opentopo
                ),
                MapStyle(
                    title = "BASE OVERLAY NL",
                    mapStyle = TileSourceFactory.BASE_OVERLAY_NL,
                    imgId = R.drawable.img_base_overlay
                )
            )
        )
    }

    fun getContaminationAreas() {

        firebase.collection("contamination_areas")
            .get()
            .addOnSuccessListener { result ->

                contaminationAreas?.clear()

                result.forEach { document ->
                    Log.d("firebase", "${document.id} => ${document.data}")

                    val df = DecimalFormat("#")
                    //TODO why actually does marker_type sometimes appear to be null when fetched from Firebase? it schould not happen, and indeed is not in database itself
                    //below hack allows to handle situation when marker_type is null
//                    var marker_type = 1
//                    document.data["marker_type"]?.let {
//                        marker_type = df.format(it).toInt()
//                    }
                    if (document.data["country"].toString() != "NaN") {
                        val ca =
                            ContaminationArea(
                                document.id,
                                document.data["country"].toString(),
                                document.data["latitude"].toString().toFloat(),
                                document.data["longitude"].toString().toFloat(),
                                df.format(document.data["radius"]).toInt(),
                                df.format(document.data["num_of_infected"]).toInt(),
                                df.format(document.data["num_of_recoveries"]).toInt(),
                                df.format(document.data["num_of_deaths"]).toInt()
                                // marker_type
                            )

                        contaminationAreas?.add(ca)

                        when {
                            document == result.last() -> areasLiveData.postValue(true)
                        }
                    }

                }


            }
            .addOnFailureListener { exception ->
                Log.w("firebase", "Error getting documents.", exception)

                areasLiveData.postValue(false)
            }
    }

    fun getTips() {

        firebase.collection("tips")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("firebase", "${document.id} => ${document.data}")

                    val ca = Tip(
                        document.id,
                        document.data["title"].toString(),
                        document.data["content_text"].toString(),
                        document.data["graphic"].toString(),
                        document.data["threat_level"].toString().toInt()
                    )
                    tips?.add(ca)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("firebase", "Error getting documents.", exception)
            }
    }

    fun getNews() {

        firebase.collection("news")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("firebase", "${document.id} => ${document.data}")

                    val ca = NewsPiece(
                        document.id,
                        document.data["title"].toString(),
                        document.data["content_text"].toString(),
                        document.data["zone"].toString().toInt()
                    )
                    news?.add(ca)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("firebase", "Error getting documents.", exception)
            }
    }

    fun getArticles() {

        firebase.collection("articles")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("firebase", "${document.id} => ${document.data}")

                    val ca = Article(
                        document.id,
                        document.data["title"].toString(),
                        document.data["content_text"].toString(),
                        document.data["graphic"].toString()
                    )
                    articles?.add(ca)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("firebase", "Error getting documents.", exception)
            }
    }
}


