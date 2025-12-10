package com.github.michaelbull.advent2025.math.rational

/**
 * Performs in-place Gauss-Jordan elimination with partial pivoting to
 * transform this augmented matrix into reduced row echelon form (RREF).
 *
 * Returns the set of pivot column indices.
 *
 * @see <a href="https://web.mit.edu/10.001/Web/Course_Notes/GaussElimPivoting.html">MIT - Gauss Elimination with Partial Pivoting</a>
 */
fun RationalMatrix.gaussJordanElimination(): Set<Int> {
    val matrix = this
    val rows = matrix.size
    val columns = matrix.first().size - 1

    return buildSet {
        var pivotRow = 0
        var column = 0

        while (column < columns && pivotRow < rows) {
            val candidateRows = (pivotRow + 1)..<rows

            val maxRow = candidateRows.fold(pivotRow) { max, candidate ->
                val value = matrix[candidate][column].absoluteValue
                val maxValue = matrix[max][column].absoluteValue

                if (value > maxValue) {
                    candidate
                } else {
                    max
                }
            }

            if (!matrix[maxRow][column].isZero()) {
                matrix.swapRows(pivotRow, maxRow)
                matrix.normalizePivotRow(pivotRow, column)
                matrix.eliminateColumn(pivotRow, column)

                add(column)
                pivotRow++
            }

            column++
        }
    }
}

private fun RationalMatrix.swapRows(i: Int, j: Int) {
    this[i] = this[j].also {
        this[j] = this[i]
    }
}

private fun RationalMatrix.normalizePivotRow(pivotRow: Int, pivotColumn: Int) {
    val pivotValue = this[pivotRow][pivotColumn]

    for (column in pivotColumn..<this[pivotRow].size) {
        this[pivotRow][column] /= pivotValue
    }
}

private fun RationalMatrix.eliminateColumn(pivotRow: Int, pivotColumn: Int) {
    for (row in indices) {
        val factor = this[row][pivotColumn]

        if (row != pivotRow && !factor.isZero()) {
            for (column in pivotColumn..<this[row].size) {
                this[row][column] -= factor * this[pivotRow][column]
            }
        }
    }
}
