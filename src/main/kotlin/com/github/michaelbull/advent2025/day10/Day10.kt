package com.github.michaelbull.advent2025.day10

import com.github.michaelbull.advent2025.Puzzle

object Day10 : Puzzle<List<Machine>, Long>(day = 10) {

    override fun parse(lines: Sequence<String>): List<Machine> {
        return lines.map(String::toMachine).toList()
    }

    override fun solutions() = listOf(
        ::part1,
        ::part2,
    )

    fun part1(machines: List<Machine>): Long {
        return machines.sumOf(Machine::minButtonPresses)
    }

    fun part2(machines: List<Machine>): Long {
        return machines.sumOf(Machine::minJoltagePresses)
    }
}
