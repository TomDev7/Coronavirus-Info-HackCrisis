package com.fairideas.coronavirusinfo.data.model

data class ContaminationArea(val id: String, val country: String, val latitude: Float, val longitude: Float, val radius: Int, val num_of_infected: Int, val num_of_recoveries: Int, val num_of_deaths: Int)