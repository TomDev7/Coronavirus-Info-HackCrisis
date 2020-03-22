package com.fairideas.coronavirusinfo.data.model.constants.polish


enum class CoronavirusKeyword(val searchPhrases: List<String>) {
    CORONAVIRUS(listOf("coronavirus", "koronawirus")),
    COVID_19(listOf("COVID-19","CoV-2")),
    SARS2(listOf("SARS2","SARS-CoV-2")),
}