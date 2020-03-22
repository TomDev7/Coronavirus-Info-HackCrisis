package com.fairideas.coronavirusinfo.http.rss.languages.english.providers.who.model.item

import org.simpleframework.xml.Element

data class Item @JvmOverloads constructor(
    @field:Element(name = "guid", required = false, data = true)
    @param:Element(name = "guid", required = false, data = true)
    val guid: Guid = Guid(),

    @field:Element(name = "link", required = false)
    @param:Element(name = "link", required = false)
    val link: String = "",

    @field:Element(name = "author", required = false, data = true)
    @param:Element(name = "author", required = false, data = true)
    val author: Author = Author(),

    @field:Element(name = "title", required = false)
    @param:Element(name = "title", required = false)
    val title: String = "",

    @field:Element(name = "description", required = false)
    @param:Element(name = "description", required = false)
    val description: String = "",

    @field:Element(name = "pubDate", required = false)
    @param:Element(name = "pubDate", required = false)
    val pubDate: String = "",

    @field:Element(name = "content", required = false)
    @param:Element(name = "content", required = false)
    val content: Content = Content()) {
}