package com.fairideas.coronavirusinfo.http.rss.languages.english.providers.who.model.channel

import org.simpleframework.xml.Attribute

data class Link @JvmOverloads constructor(
    @field:Attribute(name = "rel", required = false)
    @param:Attribute(name = "rel", required = false)
    val rel: String = "",

    @field:Attribute(name = "type", required = false)
    @param:Attribute(name = "type", required = false)
    val type: String = "",

    @field:Attribute(name = "href", required = false)
    @param:Attribute(name = "href", required = false)
    val href: String = ""
)