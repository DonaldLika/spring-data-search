package com.weedow.spring.data.search.expression

/**
 * Enum of operators
 */
enum class Operator {
    EQUALS,
    MATCHES,
    IMATCHES,
    LESS_THAN,
    LESS_THAN_OR_EQUALS,
    GREATER_THAN,
    GREATER_THAN_OR_EQUALS,
    // BETWEEN, // Not Necessary, we use LESS_THAN AND GREATER_THAN
    IN
}

