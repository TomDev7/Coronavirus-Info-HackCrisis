package com.fairideas.coronavirusinfo.data.model.constants.english

enum class CoronavirusKeyword(val searchPhrases: List<String>) {

    CORONAVIRUS(listOf("coronavirus")),
    COVID_19(listOf("COVID-19", "CoV-2")),
    SARS2(listOf("SARS2", "SARS-CoV-2")),
}