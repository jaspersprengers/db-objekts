package com.dbobjekts.util

import com.dbobjekts.api.exception.CodeGenerationException
import com.dbobjekts.codegen.metadata.ReservedKeywords
import javax.lang.model.SourceVersion

object ObjectNameValidator {

    fun validate(txt: String): Boolean =
        !SourceVersion.isKeyword(txt.lowercase())
                && !ReservedKeywords.isKeyword(txt.lowercase())
                && SourceVersion.isIdentifier(txt)

    fun validate(txt: String, errorMessage: String) {
        if (SourceVersion.isKeyword(txt) || ReservedKeywords.isKeyword(txt)) {
            throw CodeGenerationException(errorMessage + " It is a restricted Java/Kotlin keyword.")
        } else if (!SourceVersion.isIdentifier(txt)) {
            throw CodeGenerationException(errorMessage + " It is not a valid Java/Kotlin identifier.")
        }
    }

}
