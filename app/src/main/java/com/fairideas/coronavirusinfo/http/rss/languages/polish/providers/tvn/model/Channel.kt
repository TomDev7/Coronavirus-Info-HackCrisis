package com.fairideas.coronavirusinfo.http.rss.languages.polish.providers.tvn.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList

data class Channel @JvmOverloads constructor(
    @field:Element(name = "title", required = false)
    @param:Element(name = "title", required = false)
    val title: String = "",

    @field:Element(name = "description", required = false)
    @param:Element(name = "description", required = false)
    val description: String = "",

    @field:Element(name = "link", required = false)
    @param:Element(name = "link", required = false)
    val link: String = "",

    @field:Element(name = "language", required = false)
    @param:Element(name = "language", required = false)
    val language: String = "",

    @field:Element(name = "copyright", required = false)
    @param:Element(name = "copyright", required = false)
    val copyright: String = "",

    @field:Element(name = "lastBuildDate", required = false)
    @param:Element(name = "lastBuildDate", required = false)
    val lastBuildDate: String = "",

    @field:ElementList(entry = "item", inline = true, required = false)
    @param:ElementList(entry = "item", inline = true, required = false)
    val items: List<Item> = emptyList()
    )