package com.github.michaelbull.advent2025.math.rational

typealias RationalMatrix = Array<Array<Rational>>

inline fun rationalMatrixOf(
    rows: Int,
    columns: Int,
    init: (row: Int, column: Int) -> Rational,
): RationalMatrix {
    return Array(rows) { row ->
        Array(columns) { column ->
            init(row, column)
        }
    }
}
