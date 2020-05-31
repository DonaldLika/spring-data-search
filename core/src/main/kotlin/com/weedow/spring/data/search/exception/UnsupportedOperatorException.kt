package com.weedow.spring.data.search.exception

import com.weedow.spring.data.search.expression.Operator
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_IMPLEMENTED, reason = "Not Implemented Operator")
class UnsupportedOperatorException(operator: Operator) : Exception("Operator $operator is not supported") {

}