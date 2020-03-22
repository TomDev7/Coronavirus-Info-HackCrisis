package com.fairideas.coronavirusinfo.http.rss.languages.english.providers.who.model.item

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Text

data class Guid @JvmOverloads constructor(
    @field:Attribute(name = "isPermaLink", required = false)
    @param:Attribute(name = "isPermaLink", required = false)
    val isPermaLink: Boolean = false,

    @field:Text(required = false)
    @param:Text(required = false)
    val value: String = ""
)