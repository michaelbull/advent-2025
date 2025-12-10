package com.github.michaelbull.advent2025.day10

import com.github.michaelbull.advent2025.math.rational.Rational
import com.github.michaelbull.advent2025.math.rational.RationalMatrix
import com.github.michaelbull.advent2025.math.rational.gaussJordanElimination
import com.github.michaelbull.advent2025.math.rational.rationalMatrixOf

fun String.toMachine(): Machine {
    val match = requireNotNull(Machine.REGEX.matchEntire(this)) {
        "$this must match ${Machine.REGEX}"
    }

    val (diagram, buttons, joltageRequirements) = match.destructured

    return Machine(
        targetLights = diagram.toTargetLights(),
        buttons = buttons.toButtons(),
        joltageRequirements = joltageRequirements.toJoltageRequirements(),
    )
}

data class Machine(
    val targetLights: Int,
    val buttons: List<Button>,
    val joltageRequirements: List<Int>,
) {
    private val counterCount = joltageRequirements.size
    private val buttonCount = buttons.size

    fun minButtonPresses(): Long {
        var min = -1L

        repeat(1 shl buttonCount) { mask ->
            val state = combinedToggleMask(mask)
            val presses = mask.countOneBits().toLong()

            if (state == targetLights && (min == -1L || presses < min)) {
                min = presses
            }
        }

        check(min != -1L)
        return min
    }

    fun minJoltagePresses(): Long {
        return buttonMatrix().minPresses()
    }

    private fun combinedToggleMask(mask: Int): Int {
        var combined = 0
        var remaining = mask

        while (remaining != 0) {
            val buttonIndex = remaining.countTrailingZeroBits()
            combined = combined xor buttons[buttonIndex].toggleMask
            remaining = remaining and (remaining - 1)
        }

        return combined
    }

    private fun buttonMatrix(): RationalMatrix {
        return rationalMatrixOf(
            rows = counterCount,
            columns = buttonCount + 1,
            init = ::buttonCoefficient
        )
    }

    private fun buttonCoefficient(counter: Int, column: Int): Rational {
        return if (column >= buttonCount) {
            Rational(joltageRequirements[counter])
        } else if (buttons[column] toggles counter) {
            Rational.ONE
        } else {
            Rational.ZERO
        }
    }

    private fun RationalMatrix.minPresses(): Long {
        val pivotButtons = gaussJordanElimination()
        val freeButtons = buttons.indices.filterNot(pivotButtons::contains)

        val maxRequirement = joltageRequirements.max()

        val freePresses = LongArray(freeButtons.size)
        val partialPresses = LongArray(freeButtons.size + 1)

        var min = -1L
        var depth = 0

        while (depth >= 0) {
            val buttonPresses = freePresses.getOrElse(depth) { 0 }
            val totalPresses = partialPresses[depth] + buttonPresses

            when {
                /* leaf: all free buttons assigned, test solution */
                depth == freeButtons.size -> {
                    val pivotPresses = pivotPresses(pivotButtons.size, freeButtons, freePresses)

                    if (pivotPresses != -1L) {
                        val presses = totalPresses + pivotPresses

                        if (min == -1L || presses < min) {
                            min = presses
                        }
                    }

                    depth--

                    if (depth >= 0) {
                        freePresses[depth]++
                    }
                }

                /* bound exceeded: button presses out of range, backtrack */
                buttonPresses > maxRequirement -> {
                    freePresses[depth] = 0
                    depth--

                    if (depth >= 0) {
                        freePresses[depth]++
                    }
                }

                /* prune: total presses already exceeds best, skip */
                min != -1L && totalPresses >= min -> {
                    freePresses[depth]++
                }

                /* branch: explore next free button */
                else -> {
                    partialPresses[depth + 1] = totalPresses
                    depth++
                }
            }
        }

        check(min != -1L)
        return min
    }

    private fun RationalMatrix.pivotPresses(
        count: Int,
        freeButtons: List<Int>,
        freePresses: LongArray,
    ): Long {
        var presses = 0L

        repeat(count) { counter ->
            var remaining = this[counter][buttonCount]

            for ((index, freeButton) in freeButtons.withIndex()) {
                val coefficient = this[counter][freeButton]
                val buttonPresses = Rational(freePresses[index])
                remaining -= coefficient * buttonPresses
            }

            if (remaining.isFraction() || remaining.isNegative()) {
                return -1
            }

            presses += remaining.numerator
        }

        return presses
    }

    companion object {
        val REGEX = """\[([.#]+)](.+)\{([^}]+)}""".toRegex()
    }
}

private fun String.toTargetLights(): Int {
    return indices
        .filter { this[it] == '#' }
        .toBitmask()
}

private fun String.toJoltageRequirements(): List<Int> {
    return split(",").map(String::toInt)
}

private fun String.toButtons(): List<Button> {
    val buttons = Button.REGEX.findAll(this).map { result ->
        val (indices) = result.destructured
        indices.toButton()
    }

    return buttons.toList()
}
