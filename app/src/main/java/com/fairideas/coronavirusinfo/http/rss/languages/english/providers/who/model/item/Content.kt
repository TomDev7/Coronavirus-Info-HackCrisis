package com.fairideas.coronavirusinfo.http.rss.languages.english.providers.who.model.item

import com.fairideas.coronavirusinfo.http.rss.languages.english.constants.Namespace.Companion.A_10
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Text

@Namespace(prefix = A_10)
data class Content @JvmOverloads constructor(
    @field:Attribute(name = "type", required = false)
    @param:Attribute(name = "type", required = false)
    val type: String = "",

    @field:Text(required = false)
    @param:Text(required = false)
    val value: String = ""
)