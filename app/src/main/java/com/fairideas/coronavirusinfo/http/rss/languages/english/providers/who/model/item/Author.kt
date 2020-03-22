package com.fairideas.coronavirusinfo.http.rss.languages.english.providers.who.model.item

import com.fairideas.coronavirusinfo.http.rss.languages.english.constants.Namespace.Companion.A_10
import org.simpleframework.xml.Element
import org.simpleframework.xml.Namespace

@Namespace(prefix = A_10)
data class Author @JvmOverloads constructor(
    @Namespace(prefix = A_10)
    @field:Element(name = "name", required = false)
    @param:Element(name = "name", required = false)
    val name: String = ""
)