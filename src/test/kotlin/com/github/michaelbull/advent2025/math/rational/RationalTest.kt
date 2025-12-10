package com.github.michaelbull.advent2025.math.rational

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RationalTest {

    @Test
    fun `Int toRational`() {
        assertEquals(Rational(5), 5.toRational())
    }

    @Test
    fun `Int over Int`() {
        assertEquals(Rational(1, 2), 1 over 2)
    }

    @Test
    fun `Long toRational`() {
        assertEquals(Rational(5L), 5L.toRational())
    }

    @Test
    fun `Long over Long`() {
        assertEquals(Rational(1L, 2L), 1L over 2L)
    }

    @Test
    fun `normalizes to lowest terms`() {
        assertEquals(Rational(1, 2), Rational(2, 4))
    }

    @Test
    fun `negative numerator preserved`() {
        assertEquals(-3L, Rational(-3, 4).numerator)
    }

    @Test
    fun `negative denominator moves sign to numerator`() {
        assertEquals(-3L, Rational(3, -4).numerator)
    }

    @Test
    fun `both negative becomes positive`() {
        assertEquals(3L, Rational(-3, -4).numerator)
    }

    @Test
    fun `zero denominator throws ArithmeticException`() {
        assertFailsWith<ArithmeticException> {
            Rational(1, 0)
        }
    }

    @Test
    fun `absoluteValue of positive unchanged`() {
        assertEquals(Rational(3, 4), Rational(3, 4).absoluteValue)
    }

    @Test
    fun `absoluteValue of negative becomes positive`() {
        assertEquals(Rational(3, 4), Rational(-3, 4).absoluteValue)
    }

    @Test
    fun `component1 returns numerator`() {
        val numerator = Rational(3, 4).component1()
        assertEquals(3L, numerator)
    }

    @Test
    fun `component2 returns denominator`() {
        val denominator = Rational(3, 4).component2()
        assertEquals(4L, denominator)
    }

    @Test
    fun `destructuring returns numerator and denominator`() {
        val (numerator, denominator) = Rational(3, 4)
        assertEquals(3L, numerator)
        assertEquals(4L, denominator)
    }

    @Test
    fun `unaryPlus returns same value`() {
        assertEquals(Rational(3, 4), +Rational(3, 4))
    }

    @Test
    fun `plus with same denominator`() {
        assertEquals(Rational(1, 2), Rational(1, 4) + Rational(1, 4))
    }

    @Test
    fun `plus with different denominators`() {
        assertEquals(Rational(7, 12), Rational(1, 3) + Rational(1, 4))
    }

    @Test
    fun `unaryMinus negates positive`() {
        assertEquals(Rational(-3, 4), -Rational(3, 4))
    }

    @Test
    fun `unaryMinus negates negative`() {
        assertEquals(Rational(3, 4), -Rational(-3, 4))
    }

    @Test
    fun `minus to zero`() {
        assertEquals(Rational.ZERO, Rational(1, 4) - Rational(1, 4))
    }

    @Test
    fun `minus with different denominators`() {
        assertEquals(Rational(1, 12), Rational(1, 3) - Rational(1, 4))
    }

    @Test
    fun `times returns correct result`() {
        assertEquals(Rational(1, 6), Rational(1, 2) * Rational(1, 3))
    }

    @Test
    fun `div returns correct result`() {
        assertEquals(Rational(3, 2), Rational(1, 2) / Rational(1, 3))
    }

    @Test
    fun `div by zero throws ArithmeticException`() {
        assertFailsWith<ArithmeticException> {
            Rational.ONE / Rational.ZERO
        }
    }

    @Test
    fun `isZero returns true for ZERO`() {
        assertTrue(Rational.ZERO.isZero())
    }

    @Test
    fun `isZero returns false for ONE`() {
        assertFalse(Rational.ONE.isZero())
    }

    @Test
    fun `isInteger returns true for whole number`() {
        assertTrue(Rational(5).isInteger())
    }

    @Test
    fun `isInteger returns true for reducible fraction`() {
        assertTrue(Rational(4, 2).isInteger())
    }

    @Test
    fun `isInteger returns false for fraction`() {
        assertFalse(Rational(1, 2).isInteger())
    }

    @Test
    fun `isFraction returns true for fraction`() {
        assertTrue(Rational(1, 2).isFraction())
    }

    @Test
    fun `isFraction returns false for whole number`() {
        assertFalse(Rational(5).isFraction())
    }

    @Test
    fun `isFraction returns false for reducible fraction`() {
        assertFalse(Rational(4, 2).isFraction())
    }

    @Test
    fun `isPositive returns true for positive`() {
        assertTrue(Rational(3, 4).isPositive())
    }

    @Test
    fun `isPositive returns false for zero`() {
        assertFalse(Rational.ZERO.isPositive())
    }

    @Test
    fun `isPositive returns false for negative`() {
        assertFalse(Rational(-3, 4).isPositive())
    }

    @Test
    fun `isNegative returns true for negative`() {
        assertTrue(Rational(-3, 4).isNegative())
    }

    @Test
    fun `isNegative returns false for zero`() {
        assertFalse(Rational.ZERO.isNegative())
    }

    @Test
    fun `isNegative returns false for positive`() {
        assertFalse(Rational(3, 4).isNegative())
    }

    @Test
    fun `copy with new numerator`() {
        assertEquals(Rational(3, 2), Rational(1, 2).copy(numerator = 3))
    }

    @Test
    fun `copy with new denominator`() {
        assertEquals(Rational(1, 4), Rational(1, 2).copy(denominator = 4))
    }

    @Test
    fun `reciprocal swaps numerator and denominator`() {
        assertEquals(Rational(2, 1), Rational(1, 2).reciprocal())
    }

    @Test
    fun `reciprocal of negative`() {
        assertEquals(Rational(-2, 1), Rational(-1, 2).reciprocal())
    }

    @Test
    fun `toLong returns numerator for integer`() {
        assertEquals(5L, Rational(5).toLong())
    }

    @Test
    fun `toLong throws for non-integer`() {
        assertFailsWith<IllegalStateException> {
            Rational(1, 2).toLong()
        }
    }

    @Test
    fun `toInt returns numerator for integer`() {
        assertEquals(5, Rational(5).toInt())
    }

    @Test
    fun `toString integer without denominator`() {
        assertEquals("5", Rational(5).toString())
    }

    @Test
    fun `toString fraction with denominator`() {
        assertEquals("1/2", Rational(1, 2).toString())
    }

    @Test
    fun `compareTo greater than`() {
        assertTrue(Rational(1, 2) > Rational(1, 3))
    }

    @Test
    fun `compareTo less than`() {
        assertTrue(Rational(1, 3) < Rational(1, 2))
    }

    @Test
    fun `compareTo equal`() {
        assertEquals(Rational(2, 4), Rational(1, 2))
    }

    @Test
    fun `ZERO numerator`() {
        assertEquals(0L, Rational.ZERO.numerator)
    }

    @Test
    fun `ZERO denominator`() {
        assertEquals(1L, Rational.ZERO.denominator)
    }

    @Test
    fun `ONE numerator`() {
        assertEquals(1L, Rational.ONE.numerator)
    }

    @Test
    fun `ONE denominator`() {
        assertEquals(1L, Rational.ONE.denominator)
    }
}
