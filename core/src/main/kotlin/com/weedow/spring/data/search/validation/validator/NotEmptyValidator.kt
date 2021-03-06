package com.weedow.spring.data.search.validation.validator

import com.weedow.spring.data.search.expression.FieldExpression
import com.weedow.spring.data.search.validation.DataSearchErrors
import com.weedow.spring.data.search.validation.DataSearchValidator

/**
 * [DataSearchValidator][com.weedow.spring.data.search.validation.DataSearchValidator] implementation to check if there is at least one field expression.
 */
class NotEmptyValidator(
        private val errorCode: String = DEFAULT_ERROR_CODE,
        private val errorMessage: String = DEFAULT_ERROR_MESSAGE
) : DataSearchValidator {

    companion object {
        /** Default error code */
        const val DEFAULT_ERROR_CODE = "not-empty"
        /** Default error message */
        const val DEFAULT_ERROR_MESSAGE = "The search must contain at least one field expression."
    }

    override fun validate(fieldExpressions: Collection<FieldExpression>, errors: DataSearchErrors) {
        if (fieldExpressions.isEmpty()) {
            errors.reject(errorCode, errorMessage)
        }
    }

}