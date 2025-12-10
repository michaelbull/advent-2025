package com.github.michaelbull.advent2025.day01

data class Rotation(
    val direction: Direction,
    val distance: Int,
) {
    companion object {
        val REGEX = """(${Direction.REGEX})(\d+)""".toRegex()
    }
}

fun Sequence<Rotation>.turnSequence(initial: Dial = Dial.DEFAULT): Sequence<Dial> {
    return runningFold(initial, Dial::turn)
}

fun String.toRotation(): Rotation {
    val match = requireNotNull(Rotation.REGEX.matchEntire(this)) {
        "$this must match ${Rotation.REGEX}"
    }

    val (direction, distance) = match.destructured

    return Rotation(
        direction = direction.single().toDirection(),
        distance = distance.toInt(),
    )
}
