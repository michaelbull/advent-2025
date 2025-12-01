package com.github.michaelbull.advent2025.math.grid

import com.github.michaelbull.advent2025.math.Vector2
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BooleanGridTest {

    @Test
    fun `get within bounds`() {
        val map = BooleanGrid(width = 10, height = 10) { (x, y) ->
            x == 3 && y == 6
        }

        assertTrue(map[Vector2(3, 6)])
        assertFalse(map[Vector2(2, 5)])
        assertFalse(map[Vector2(0, 0)])
        assertFalse(map[Vector2(9, 9)])
    }

    @Test
    fun `get below x bounds`() {
        val map = BooleanGrid(width = 10, height = 20)

        val exception = assertFailsWith<IllegalArgumentException> {
            map[Vector2(-5, 0)]
        }

        assertEquals(exception.message, "x must be in 0..9, but was -5")
    }

    @Test
    fun `get above x bounds`() {
        val map = BooleanGrid(width = 10, height = 20)

        val exception = assertFailsWith<IllegalArgumentException> {
            map[Vector2(15, 0)]
        }

        assertEquals(exception.message, "x must be in 0..9, but was 15")
    }

    @Test
    fun `get below y bounds`() {
        val map = BooleanGrid(width = 10, height = 20)

        val exception = assertFailsWith<IllegalArgumentException> {
            map[Vector2(0, -7)]
        }

        assertEquals(exception.message, "y must be in 0..19, but was -7")
    }

    @Test
    fun `get above y bounds`() {
        val map = BooleanGrid(width = 10, height = 20)

        val exception = assertFailsWith<IllegalArgumentException> {
            map[Vector2(0, 23)]
        }

        assertEquals(exception.message, "y must be in 0..19, but was 23")
    }

    @Test
    fun `getOrDefault within bounds`() {
        val map = BooleanGrid(width = 10, height = 20) { (x, _) ->
            (x % 2) == 0
        }

        assertTrue(map.getOrDefault(Vector2(4, 6), false))
        assertFalse(map.getOrDefault(Vector2(3, 6), true))
    }

    @Test
    fun `getOrDefault below x bounds`() {
        val map = BooleanGrid(width = 10, height = 20) { (x, _) ->
            (x % 2) == 0
        }

        assertFalse(map.getOrDefault(Vector2(-4, 6), false))
    }

    @Test
    fun `getOrDefault above x bounds`() {
        val map = BooleanGrid(width = 10, height = 20) { (x, _) ->
            (x % 2) == 0
        }

        assertFalse(map.getOrDefault(Vector2(22, 6), false))
    }

    @Test
    fun `getOrDefault below y bounds`() {
        val map = BooleanGrid(width = 10, height = 20) { (x, _) ->
            (x % 2) == 0
        }

        assertFalse(map.getOrDefault(Vector2(4, -5), false))
    }

    @Test
    fun `getOrDefault above y bounds`() {
        val map = BooleanGrid(width = 10, height = 20) { (x, _) ->
            (x % 2) == 0
        }

        assertFalse(map.getOrDefault(Vector2(4, 28), false))
    }

    @Test
    fun `getOrElse within bounds`() {
        val map = BooleanGrid(width = 10, height = 20) { (x, _) ->
            (x % 2) == 0
        }

        assertTrue(map.getOrElse(Vector2(4, 6)) { false })
        assertFalse(map.getOrElse(Vector2(3, 6)) { true })
    }

    @Test
    fun `getOrElse below x bounds`() {
        val map = BooleanGrid(width = 10, height = 20) { (x, _) ->
            (x % 2) == 0
        }

        assertFalse(map.getOrElse(Vector2(-4, 6)) { false })
    }

    @Test
    fun `getOrElse above x bounds`() {
        val map = BooleanGrid(width = 10, height = 20) { (x, _) ->
            (x % 2) == 0
        }

        assertFalse(map.getOrElse(Vector2(22, 6)) { false })
    }

    @Test
    fun `getOrElse below y bounds`() {
        val map = BooleanGrid(width = 10, height = 20) { (x, _) ->
            (x % 2) == 0
        }

        assertFalse(map.getOrElse(Vector2(4, -5)) { false })
    }

    @Test
    fun `getOrElse above y bounds`() {
        val map = BooleanGrid(width = 10, height = 20) { (x, _) ->
            (x % 2) == 0
        }

        assertFalse(map.getOrElse(Vector2(4, 28)) { false })
    }
}
