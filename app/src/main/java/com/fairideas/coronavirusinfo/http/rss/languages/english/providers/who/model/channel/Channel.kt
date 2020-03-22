package com.fairideas.coronavirusinfo.http.rss.languages.english.providers.who.model.channel

import com.fairideas.coronavirusinfo.http.rss.languages.english.providers.who.model.item.Item
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Path


data class Channel @JvmOverloads constructor(
    @field:Element(name = "title", required = false)
    @param:Element(name = "title", required = false)
    val title: String = "",

    @field:Element(name = "description", required = false)
    @param:Element(name = "description", required = false)
    val description: String = "",

    @field:Element(name = "link", required = false)
    @param:Element(name = "link", required = false)
    @field:Path("link[1]")
    @param:Path("link[1]")
    val baseUrl: String? = "",

    @field:ElementList(entry = "contributor", inline = true, required = false)
    @param:ElementList(entry = "contributor", inline = true, required = false)
    val contributors: List<Contributor> = emptyList(),

    @field:Element(name = "link", required = false)
    @param:Element(name = "link", required = false)
    @field:Path("link[2]")
    @param:Path("link[2]")
    val link: Link? = Link(),

    @field:ElementList(entry = "item", inline = true, required = false)
    @param:ElementList(entry = "item", inline = true, required = false)
    val items: List<Item> = emptyList()
)