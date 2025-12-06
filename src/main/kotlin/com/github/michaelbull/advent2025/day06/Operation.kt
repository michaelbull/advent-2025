package com.github.michaelbull.advent2025.day06

typealias Operation = (Operand, Operand) -> Operand

fun String.toOperation(columnRange: IntRange): Operation {
    return safeSubstring(columnRange)
        .trim()
        .first()
        .toOperation()
}

fun Char.toOperation(): Operation = when (this) {
    '+' -> Long::plus
    '*' -> Long::times
    else -> throw IllegalArgumentException("unknown operator: $this")
}
