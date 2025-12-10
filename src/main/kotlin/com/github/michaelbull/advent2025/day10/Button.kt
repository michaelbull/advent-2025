package com.github.michaelbull.advent2025.day10

@JvmInline
value class Button(val toggleMask: Int) {

    infix fun toggles(index: Int): Boolean {
        return (toggleMask and (1 shl index)) != 0
    }

    companion object {
        val REGEX = """\(([^)]+)\)""".toRegex()
    }
}

fun String.toButton(): Button {
    val toggleMask = split(",")
        .map(String::toInt)
        .toBitmask()

    return Button(toggleMask)
}

fun Iterable<Int>.toBitmask(): Int {
    return fold(0) { acc, index ->
        acc or (1 shl index)
    }
}
