package com.github.michaelbull.advent2025.day06

typealias Operand = Long

const val EMPTY_OPERAND: Operand = -1L

fun Operand.isNotEmpty(): Boolean {
    return this != EMPTY_OPERAND
}

fun List<String>.toOperand(column: Int): Operand {
    val digits = mapNotNull { row ->
        row.getOrNull(column)?.digitToIntOrNull()
    }

    return digits.toOperand()
}

fun String.toOperand(columnRange: IntRange): Operand {
    return safeSubstring(columnRange)
        .trim()
        .toOperand()
}

private fun List<Int>.toOperand(): Operand {
    return if (isEmpty()) {
        EMPTY_OPERAND
    } else {
        fold(0L) { acc, digit ->
            (acc * 10) + digit
        }
    }
}

private fun String.toOperand(): Operand {
    return if (isEmpty()) {
        EMPTY_OPERAND
    } else {
        toLong()
    }
}
