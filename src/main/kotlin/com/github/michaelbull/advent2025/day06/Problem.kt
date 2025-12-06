package com.github.michaelbull.advent2025.day06

data class Problem(
    val numberRows: List<String>,
    val columnRange: IntRange,
    val operation: Operation,
) {
    fun operands(): List<Operand> {
        return numberRows
            .map { row -> row.toOperand(columnRange) }
            .filter(Operand::isNotEmpty)
    }

    fun cephalopodOperands(): List<Operand> {
        return columnRange
            .reversed()
            .map { column -> numberRows.toOperand(column) }
            .filter(Operand::isNotEmpty)
    }

    fun solve(operands: List<Operand>): Long {
        return operands.reduce(operation)
    }
}
