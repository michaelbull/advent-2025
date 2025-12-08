package com.github.michaelbull.advent2025.day08

import com.github.michaelbull.advent2025.math.Vector2
import com.github.michaelbull.advent2025.math.Vector3
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
        val graph = CircuitGraph(positions.size)

        sortedConnections()
            .take(connections)
            .forEach(graph::connect)

        return graph.sizes()
            .take(3)
            .fold(1L, Long::times)
    }

    fun finalConnectionProduct(): Long {
        val graph = CircuitGraph(positions.size)

        val (finalX, finalY) = sortedConnections()
            .filter(graph::connect)
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

private class CircuitGraph(size: Int) {
    private val circuits = IntArray(size) { it }
    private val depths = IntArray(size) { 0 }

    fun connect(connection: Vector2): Boolean {
        val circuitX = findCircuit(connection.x)
        val circuitY = findCircuit(connection.y)

        val depthX = depths[circuitX]
        val depthY = depths[circuitY]

        return when {
            circuitX == circuitY -> {
                false
            }

            depthX < depthY -> {
                circuits[circuitX] = circuitY
                true
            }

            depthX > depthY -> {
                circuits[circuitY] = circuitX
                true
            }

            else -> {
                circuits[circuitY] = circuitX
                depths[circuitX] += 1
                true
            }
        }
    }

    fun sizes(): List<Int> {
        return circuits.indices
            .groupingBy(::findCircuit)
            .eachCount()
            .values
            .sortedDescending()
    }

    private tailrec fun findCircuit(x: Int): Int {
        return if (circuits[x] == x) {
            x
        } else {
            findCircuit(circuits[x])
        }
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
