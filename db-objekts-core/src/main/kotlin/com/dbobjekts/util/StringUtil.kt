package com.dbobjekts.util

import org.apache.commons.lang3.StringUtils
import org.apache.commons.text.CaseUtils
import kotlin.reflect.KClass

object StringUtil {

    val TAB = "    "

    fun nonEmpty(str: String?): String? = if (str == null || str.isBlank()) null else str

    fun initLowerCase(str: String): String =
        if (str.isBlank()) "" else str.substring(0, 1).lowercase().plus(str.substring(1))

    fun initUpperCase(str: String): String =
        if (str.isBlank()) "" else str.substring(0, 1).uppercase().plus(str.substring(1))

    fun capitalCamel(str: String): String = snakeToCamel(str, true)

    fun snakeToCamel(str: String, capitalizeFirst: Boolean = false): String = if (str.isBlank()) ""
    else
        CaseUtils.toCamelCase(str, capitalizeFirst, '\u005f')

    fun lowerCamel(str: String): String = snakeToCamel(str, false)

    fun ensureLeadingSpace(str: String): String =
        (if (!str.startsWith(" ")) " " else "") + str

    fun concat(elements: List<String>): String =
        elements.filter { StringUtils.isNotEmpty(it) }.map { it.trim() }.joinToString(" ")

    fun <T> concatLists(vararg el: List<T>): List<T> {
        val buffer = mutableListOf<T>()
        el.forEach { buffer.addAll(it) }
        return buffer.toList()
    }

    @Deprecated("replace with list + list")
    fun <T> concatToList(els: List<T>, el: T): List<T> {
        val buffer = mutableListOf<T>()
        buffer.addAll(els)
        buffer.add(el)
        return buffer.toList()
    }

    fun <T> joinBy(items: List<T>, getter: (T) -> String, separator: String = ","): String =
        items.map { getter(it) }.joinToString(separator)

    fun joinBy(items: List<Any>, separator: String = ","): String = items.map { it.toString() }.joinToString(separator)

    fun <T> toNonEmptyOptional(str: T): T? = if (str == null || str.toString().length == 0) null else str

    fun classToString(clz: KClass<*>): String =
        if (clz.javaPrimitiveType?.isPrimitive ?: false)
            StringUtil.initUpperCase(clz.simpleName!!)
        else {
            clz.qualifiedName!!
                .replace("java.lang.", "")
                .replace("kotlin.", "")
        }

    fun classToString(clz: Class<*>): String {
        return if (clz.isPrimitive)
            StringUtil.initUpperCase(clz.getSimpleName())
        else if (clz == ByteArray::class.java) {
            "ByteArray"
        } else if (clz.getName().contains("[Ljava.lang.Object;")) {
            "Array<Any>"
        } else if (clz == Object::class.java) {
            "Any"
        } else {
            clz.getName()
                .replace("java.lang.", "")
                .replace("kotlin.", "")
        }
    }

}
