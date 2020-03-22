package com.fairideas.coronavirusinfo.http.rss.languages.english.providers.who.model.channel

import org.simpleframework.xml.Element

data class Contributor @JvmOverloads constructor(
    @field:Element(name = "name", required = false, data = true)
    @param:Element(name = "name", required = false, data = true)
    val name: String = ""
)