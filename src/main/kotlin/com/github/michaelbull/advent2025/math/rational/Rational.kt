package com.github.michaelbull.advent2025.math.rational

import com.github.michaelbull.advent2025.math.greatestCommonDivisor
import com.github.michaelbull.advent2025.math.leastCommonMultiple
import kotlin.math.absoluteValue

fun Int.toRational(): Rational {
    return Rational(this)
}

infix fun Int.over(denominator: Int): Rational {
    return Rational(this, denominator)
}

fun Rational(numerator: Int, denominator: Int = 1): Rational {
    return Rational(numerator.toLong(), denominator.toLong())
}

fun Long.toRational(): Rational {
    return Rational(this)
}

infix fun Long.over(denominator: Long): Rational {
    return Rational(this, denominator)
}

fun Rational(numerator: Long, denominator: Long = 1L): Rational {
    return if (denominator == 0L) {
        throw ArithmeticException("/ by zero")
    } else if (numerator == 0L) {
        Rational.ZERO
    } else if (numerator == denominator) {
        Rational.ONE
    } else if (numerator == -denominator) {
        Rational.NEGATIVE_ONE
    } else {
        val gcd = greatestCommonDivisor(
            a = numerator.absoluteValue,
            b = denominator.absoluteValue,
        )

        val packed = Rational.pack(
            numerator = reduceNumerator(numerator, denominator, gcd),
            denominator = reduceDenominator(numerator, denominator, gcd),
        )

        Rational.from(packed)
    }
}

/**
 * An exact rational number represented as a fraction (numerator/denominator).
 *
 * Uses exact arithmetic to avoid floating-point rounding errors. Useful for
 * algorithms like Gaussian elimination where precision is critical.
 *
 * The numerator and denominator are packed into a single [Long] for efficiency
 * (32 bits each), limiting the range to [Int.MIN_VALUE]..[Int.MAX_VALUE].
 *
 * Values are automatically reduced to the lowest terms via [greatestCommonDivisor].
 */
@JvmInline
value class Rational private constructor(
    private val packed: Long,
) : Comparable<Rational> {

    val numerator: Long
        get() = (packed shr TERM_BITS).toInt().toLong()

    val denominator: Long
        get() = (packed and TERM_MASK).toInt().toLong()

    val absoluteValue: Rational
        get() = if (numerator < 0) -this else this

    operator fun component1(): Long {
        return numerator
    }

    operator fun component2(): Long {
        return denominator
    }

    operator fun unaryPlus(): Rational {
        return this
    }

    operator fun plus(other: Rational): Rational {
        return withCommonDenominator(other, Long::plus)
    }

    operator fun unaryMinus(): Rational {
        return Rational(
            numerator = -numerator,
            denominator = denominator,
        )
    }

    operator fun minus(other: Rational): Rational {
        return withCommonDenominator(other, Long::minus)
    }

    operator fun times(other: Rational): Rational {
        val ad = greatestCommonDivisor(
            a = numerator.absoluteValue,
            b = other.denominator.absoluteValue,
        )

        val bc = greatestCommonDivisor(
            a = other.numerator.absoluteValue,
            b = denominator.absoluteValue,
        )

        val a = numerator / ad
        val b = denominator / bc

        val c = other.numerator / bc
        val d = other.denominator / ad

        val ac = a * c
        val bd = b * d

        return Rational(
            numerator = ac,
            denominator = bd,
        )
    }

    operator fun div(other: Rational): Rational {
        return if (other.isZero()) {
            throw ArithmeticException("/ by zero")
        } else {
            this * other.reciprocal()
        }
    }

    fun isZero(): Boolean {
        return numerator == 0L
    }

    fun isInteger(): Boolean {
        return denominator == 1L
    }

    fun isFraction(): Boolean {
        return denominator != 1L
    }

    fun isPositive(): Boolean {
        return numerator > 0L
    }

    fun isNegative(): Boolean {
        return numerator < 0L
    }

    fun copy(numerator: Long = this.numerator, denominator: Long = this.denominator): Rational {
        return Rational(
            numerator = numerator,
            denominator = denominator,
        )
    }

    fun reciprocal(): Rational {
        return Rational(
            numerator = denominator,
            denominator = numerator,
        )
    }

    fun toLong(): Long {
        check(isInteger()) { "rational must be an integer, but was $this" }
        return numerator
    }

    fun toInt(): Int {
        return toLong().toInt()
    }

    override fun toString(): String {
        return if (isInteger()) {
            "$numerator"
        } else {
            "$numerator/$denominator"
        }
    }

    override fun compareTo(other: Rational): Int {
        val left = numerator * other.denominator
        val right = other.numerator * denominator
        return left.compareTo(right)
    }

    private inline fun withCommonDenominator(other: Rational, block: (Long, Long) -> Long): Rational {
        val lcm = leastCommonMultiple(denominator, other.denominator)

        val scaledThis = numerator * (lcm / denominator)
        val scaledOther = other.numerator * (lcm / other.denominator)

        return Rational(
            numerator = block(scaledThis, scaledOther),
            denominator = lcm,
        )
    }

    companion object {
        private const val TERM_BITS = UInt.SIZE_BITS
        private val TERM_MASK = UInt.MAX_VALUE.toLong()

        val ZERO = Rational(pack(numerator = 0L, denominator = 1L))
        val ONE = Rational(pack(numerator = 1L, denominator = 1L))
        val NEGATIVE_ONE = Rational(pack(numerator = -1L, denominator = 1L))

        internal fun from(packed: Long): Rational {
            return Rational(packed)
        }

        internal fun pack(numerator: Long, denominator: Long): Long {
            return (numerator.toInt().toLong() shl TERM_BITS) or (denominator.toInt().toLong() and TERM_MASK)
        }
    }
}

private val NUMERATOR_RANGE = Int.MIN_VALUE..Int.MAX_VALUE

private fun reduceNumerator(numerator: Long, denominator: Long, gcd: Long): Long {
    val reduced = if (denominator < 0) {
        -numerator / gcd
    } else {
        numerator / gcd
    }

    return if (reduced in NUMERATOR_RANGE) {
        reduced
    } else {
        throw ArithmeticException("Rational($numerator, $denominator): reduced numerator must be in [$NUMERATOR_RANGE], but was $reduced")
    }
}

private val DENOMINATOR_RANGE = 1..Int.MAX_VALUE

private fun reduceDenominator(numerator: Long, denominator: Long, gcd: Long): Long {
    val reduced = denominator.absoluteValue / gcd

    return if (reduced in DENOMINATOR_RANGE) {
        reduced
    } else {
        throw ArithmeticException("Rational($numerator, $denominator): reduced denominator must be in [$DENOMINATOR_RANGE], but was $reduced")
    }
}
