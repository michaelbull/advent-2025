package com.github.michaelbull.advent2025.day09

import com.github.michaelbull.advent2025.Puzzle
import kotlinx.coroutines.runBlocking

object Day09 : Puzzle<MovieTheater, Long>(day = 9) {

    override fun parse(lines: Sequence<String>): MovieTheater {
        return lines.toMovieTheater()
    }

    override fun solutions() = listOf(
        ::part1,
        ::part2,
    )

    fun part1(input: MovieTheater): Long {
        return input.largestRectangleArea()
    }

    fun part2(input: MovieTheater): Long = runBlocking {
        input.largestColoredRectangleArea()
    }
}
