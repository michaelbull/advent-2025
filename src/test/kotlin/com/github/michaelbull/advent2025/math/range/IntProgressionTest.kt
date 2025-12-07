package com.github.michaelbull.advent2025.math.range

import kotlin.test.Test
import kotlin.test.assertEquals

class IntProgressionTest {

    @Test
    fun `symmetricRange creates range from negative to positive`() {
        assertEquals(-3..3, 3.symmetricRange())
    }

    @Test
    fun `symmetricRange of 0 creates single element range`() {
        assertEquals(0..0, 0.symmetricRange())
    }

    @Test
    fun `rem within bounds returns same value`() {
        assertEquals(5, 5.rem(0..9))
    }

    @Test
    fun `rem exceeding upper bound wraps`() {
        assertEquals(0, 10.rem(0..9))
    }

    @Test
    fun `rem of negative value preserves sign`() {
        assertEquals(-1, (-1).rem(0..9))
    }

    @Test
    fun `rem works with non-zero-based progression`() {
        assertEquals(5, 15.rem(5..14))
    }

    @Test
    fun `mod within bounds returns same value`() {
        assertEquals(5, 5.mod(0..9))
    }

    @Test
    fun `mod exceeding upper bound wraps`() {
        assertEquals(0, 10.mod(0..9))
    }

    @Test
    fun `mod of negative value wraps to positive`() {
        assertEquals(9, (-1).mod(0..9))
    }

    @Test
    fun `mod works with non-zero-based progression`() {
        assertEquals(5, 15.mod(5..14))
    }

    @Test
    fun `mod below lower bound wraps to upper`() {
        assertEquals(14, 4.mod(5..14))
    }
}
