package com.github.michaelbull.advent2025

import com.github.michaelbull.advent2025.day01.Day01
import com.github.michaelbull.advent2025.day02.Day02
import com.github.michaelbull.advent2025.day03.Day03
import kotlin.time.measureTimedValue

fun main() {
    val puzzles = listOf(
        Day01,
        Day02,
        Day03,
    )

    for (puzzle in puzzles) {
        puzzle.solve()
    }
}

private fun <T : Any, V : Any> Puzzle<T, V>.solve() {
    println("")
    println("Day $day:")

    for ((index, solution) in solutions().withIndex()) {
        val (answer, duration) = measureTimedValue { solve(solution) }
        println("  part ${index + 1}:")
        println("    duration: $duration")
        println("    answer:   $answer")
    }
}
