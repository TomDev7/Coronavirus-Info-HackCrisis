package com.fairideas.coronavirusinfo.http.rss.languages.english.providers.who.model

import com.fairideas.coronavirusinfo.http.rss.languages.english.constants.Namespace.Companion.A_10
import com.fairideas.coronavirusinfo.http.rss.languages.english.providers.who.model.channel.Channel
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
@Namespace(reference = "http://www.w3.org/2005/Atom", prefix = A_10)
data class WhoFeed @JvmOverloads constructor(

    @field:Element(name = "channel", required = false)
    @param:Element(name = "channel", required = false)
    val channel: Channel = Channel(),

    @field:Attribute(name = "version", required = false)
    @param:Attribute(name = "version", required = false)
    val version: String
)