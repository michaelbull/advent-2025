package com.github.michaelbull.advent2025.day08

import com.github.michaelbull.advent2025.math.Vector2
import com.github.michaelbull.advent2025.math.Vector3
import com.github.michaelbull.advent2025.math.disjoint.toMutableDisjointSet
import com.github.michaelbull.itertools.combinations

fun Sequence<String>.toPlayground(): Playground {
    val positions = map(String::toPosition).toList()
    return Playground(positions)
}

private fun String.toPosition(): Vector3 {
    val (x, y, z) = split(",").map(String::toInt)
    return Vector3(x, y, z)
}

class Playground(
    val positions: List<Vector3>,
) {

    fun largestCircuitsProduct(connections: Int): Long {
        val set = positions.indices
            .toList()
            .toMutableDisjointSet()

        sortedConnections()
            .take(connections)
            .forEach { (a, b) -> set.union(a, b) }

        return set.partitionSizes()
            .sortedDescending()
            .take(3)
            .fold(1L) { acc, size -> acc * size }
    }

    fun finalConnectionProduct(): Long {
        val set = positions.indices
            .toList()
            .toMutableDisjointSet()

        val (finalX, finalY) = sortedConnections()
            .filter { (a, b) -> set.union(a, b) }
            .take(positions.size - 1)
            .last()

        return positions[finalX].x.toLong() * positions[finalY].x.toLong()
    }

    private fun sortedConnections(): Sequence<Vector2> {
        val indices = positions.indices.toList()

        return indices
            .combinations(k = 2, indices::connection)
            .sortedBy(::straightLineDistance)
    }

    private fun straightLineDistance(connection: Vector2): Long {
        return positions[connection.x] squaredEuclideanDistanceTo positions[connection.y]
    }
}

private fun List<Int>.connection(indices: IntArray, k: Int): Vector2 {
    require(k == 2) { "k must be 2, but was $k" }

    val (first, second) = indices

    return Vector2(
        x = get(first),
        y = get(second),
    )
}

private infix fun Vector3.squaredEuclideanDistanceTo(other: Vector3): Long {
    val delta = this - other
    return delta dot delta
}
