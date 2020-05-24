
package de.takeweiland.sqlast

enum class UnaryOperator {

    MINUS,
    PLUS,
    NOT

}

enum class BinaryOperator {

    PLUS,
    MINUS,
    MULTIPLY,
    DIVIDE,
    MODULUS,
    GT,
    LT,
    GTE,
    LTE,
    EQ,
    NEQ,
    AND,
    OR,
    LIKE,
    NOT_LIKE
    /*LSH,
    RSH,
    BIT_AND,
    BIT_OR,
    IS,
    IS_NOT*/
}