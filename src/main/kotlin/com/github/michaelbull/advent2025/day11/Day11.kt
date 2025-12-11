package com.github.michaelbull.advent2025.day11

import com.github.michaelbull.advent2025.Puzzle

object Day11 : Puzzle<DeviceGraph, Long>(day = 11) {

    override fun parse(lines: Sequence<String>): DeviceGraph {
        return lines.toDeviceGraph()
    }

    override fun solutions() = listOf(
        ::part1,
        ::part2,
    )

    fun part1(graph: DeviceGraph): Long {
        return graph.countPaths(
            source = "you",
            target = "out",
        )
    }

    fun part2(graph: DeviceGraph): Long {
        return graph.countPaths(
            source = "svr",
            target = "out",
            intermediates = listOf(
                "dac",
                "fft",
            ),
        )
    }
}
