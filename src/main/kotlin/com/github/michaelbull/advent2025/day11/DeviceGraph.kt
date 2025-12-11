package com.github.michaelbull.advent2025.day11

import com.github.michaelbull.itertools.permutations

fun Sequence<String>.toDeviceGraph(): DeviceGraph {
    val outputsByDevice = associate { line ->
        val (device, outputs) = line.split(": ")
        device to outputs.split(" ")
    }

    return DeviceGraph(outputsByDevice)
}

class DeviceGraph(
    private val outputsByDevice: Map<String, Iterable<String>>,
) {

    fun countPaths(source: String, target: String): Long {
        return countPaths(source, target, mutableMapOf())
    }

    fun countPaths(source: String, target: String, intermediates: List<String>): Long {
        return intermediates
            .routesBetween(source, target)
            .sumOf(::countPathsVia)
    }

    private fun countPaths(source: String, target: String, cache: MutableMap<String, Long>): Long {
        return if (source == target) {
            1L
        } else {
            fun paths(source: String) = countPaths(source, target, cache)

            cache.getOrPut(source) {
                when (val outputs = outputsByDevice[source]) {
                    null -> 0L
                    else -> outputs.sumOf(::paths)
                }
            }
        }
    }

    private fun List<String>.routesBetween(source: String, target: String): Sequence<List<String>> {
        val intermediates = this

        return permutations(size) { indices, k ->
            buildList(k + 2) {
                add(source)

                for (i in 0..<k) {
                    add(intermediates[indices[i]])
                }

                add(target)
            }
        }
    }

    private fun countPathsVia(route: List<String>): Long {
        return route
            .zipWithNext(::countPaths)
            .fold(1L, Long::times)
    }
}
