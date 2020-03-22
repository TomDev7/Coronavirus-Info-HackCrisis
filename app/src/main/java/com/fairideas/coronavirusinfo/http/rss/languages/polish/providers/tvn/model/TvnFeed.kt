package com.fairideas.coronavirusinfo.http.rss.languages.polish.providers.tvn.model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

@Root(strict = false)
@Namespace(reference = "http://www.w3.org/2005/Atom", prefix = "atom")
data class TvnFeed @JvmOverloads constructor(
    @field:Element(name = "channel", required = false)
    @param:Element(name = "channel", required = false)
    val channel: Channel = Channel(),

    @field:Attribute(name = "version", required = false)
    @param:Attribute(name = "version", required = false)
    val version: String
) {

}