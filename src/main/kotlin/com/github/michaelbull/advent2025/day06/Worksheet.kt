package com.github.michaelbull.advent2025.day06

class Worksheet(
    val problems: List<Problem>,
) {

    inline fun sumAnswers(operands: (Problem) -> List<Operand>): Long {
        return problems.sumOf { problem ->
            problem.solve(operands(problem))
        }
    }

    companion object {
        val EMPTY = Worksheet(emptyList())
    }
}

fun Sequence<String>.toWorksheet(): Worksheet {
    val rows = toList()

    return if (rows.isEmpty()) {
        Worksheet.EMPTY
    } else {
        val maxWidth = rows.maxOf(String::length)
        val operatorRow = rows.last()
        val numberRows = rows.dropLast(1)

        val allColumns = 0..<maxWidth

        val problems = buildList {
            var remainingColumns = allColumns

            while (true) {
                val columnRange = remainingColumns.nextColumnRange(rows) ?: break

                val problem = Problem(
                    numberRows = numberRows,
                    columnRange = columnRange,
                    operation = operatorRow.toOperation(columnRange),
                )

                add(problem)

                val start = columnRange.last + 1
                val endExclusive = allColumns.last + 1

                remainingColumns = start..<endExclusive
            }
        }

        Worksheet(problems)
    }
}

private fun IntRange.nextColumnRange(rows: List<String>): IntRange? {
    val start = firstProblemColumn(rows) ?: return null
    val range = start..last
    val afterLastColumn = last + 1
    val endExclusive = range.firstSeparatorColumn(rows) ?: afterLastColumn
    return start..<endExclusive
}

private fun IntRange.firstProblemColumn(rows: List<String>): Int? {
    return firstOrNull(rows::isProblemColumn)
}

private fun IntRange.firstSeparatorColumn(rows: List<String>): Int? {
    return firstOrNull(rows::isSeparatorColumn)
}

private fun List<String>.isProblemColumn(column: Int): Boolean {
    return !isSeparatorColumn(column)
}

private fun List<String>.isSeparatorColumn(column: Int): Boolean {
    return all { row ->
        val char = row.getOrNull(column)
        char == null || char == ' '
    }
}
