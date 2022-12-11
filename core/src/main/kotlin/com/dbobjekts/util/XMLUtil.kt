package com.dbobjekts.util

import org.w3c.dom.Element
import org.w3c.dom.NodeList
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory


object XMLUtil {

    fun read(file: File): Element {
        return newDocumentBuilder().parse(file).let {
            it.normalizeDocument()
            it.documentElement
        }
    }

    fun read(content: String): Element {
        return newDocumentBuilder().parse(content.byteInputStream()).let {
            it.normalizeDocument()
            it.documentElement
        }
    }

    private fun newDocumentBuilder() = DocumentBuilderFactory.newInstance().newDocumentBuilder()

    fun getChildElements(parent: Element?, childElementName: String): List<Element> {
        return if (parent == null) {
            listOf()
        } else {
            val nodes: NodeList? = parent.getElementsByTagName(childElementName)
            nodes?.let {
                if(nodes.length == 0 )
                    null
                else IntRange(0, nodes.length).map {
                    val el = nodes.item(it)
                    el as? Element
                }.filterNotNull()
            } ?: listOf()
        }
    }
}
