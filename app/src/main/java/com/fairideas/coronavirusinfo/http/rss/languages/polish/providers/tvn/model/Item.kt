package com.fairideas.coronavirusinfo.http.rss.languages.polish.providers.tvn.model

import org.simpleframework.xml.Element

data class Item @JvmOverloads constructor(
    @field:Element(name = "title", required = false, data = true)
    @param:Element(name = "title", required = false, data = true)
    val title: String = "",

    @field:Element(name = "description", required = false, data = true)
    @param:Element(name = "description", required = false, data = true)
    val description: String = "",

    @field:Element(name = "link", required = false)
    @param:Element(name = "link", required = false)
    val link: String = "",

    @field:Element(name = "pubDate", required = false)
    @param:Element(name = "pubDate", required = false)
    val pubDate: String = "",

    @field:Element(name = "guid", required = false)
    @param:Element(name = "guid", required = false)
    val guid: String = "") {
}