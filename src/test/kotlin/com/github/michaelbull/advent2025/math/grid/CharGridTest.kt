package com.github.michaelbull.advent2025.math.grid

import com.github.michaelbull.advent2025.math.Vector2
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CharGridTest {

    @Test
    fun `get within bounds`() {
        val map = CharGrid(width = 10, height = 10) { (x, y) ->
            if (x == 3 && y == 6) 'a' else 'b'
        }

        assertEquals('a', map[Vector2(3, 6)])
        assertEquals('b', map[Vector2(2, 5)])
        assertEquals('b', map[Vector2(0, 0)])
        assertEquals('b', map[Vector2(9, 9)])
    }

    @Test
    fun `get below x bounds`() {
        val map = CharGrid(width = 10, height = 20)

        val exception = assertFailsWith<IllegalArgumentException> {
            map[Vector2(-5, 0)]
        }

        assertEquals(exception.message, "x must be in 0..9, but was -5")
    }

    @Test
    fun `get above x bounds`() {
        val map = CharGrid(width = 10, height = 20)

        val exception = assertFailsWith<IllegalArgumentException> {
            map[Vector2(15, 0)]
        }

        assertEquals(exception.message, "x must be in 0..9, but was 15")
    }

    @Test
    fun `get below y bounds`() {
        val map = CharGrid(width = 10, height = 20)

        val exception = assertFailsWith<IllegalArgumentException> {
            map[Vector2(0, -7)]
        }

        assertEquals(exception.message, "y must be in 0..19, but was -7")
    }

    @Test
    fun `get above y bounds`() {
        val map = CharGrid(width = 10, height = 20)

        val exception = assertFailsWith<IllegalArgumentException> {
            map[Vector2(0, 23)]
        }

        assertEquals(exception.message, "y must be in 0..19, but was 23")
    }

    @Test
    fun `getOrDefault within bounds`() {
        val map = CharGrid(width = 10, height = 20) { (x, _) ->
            if ((x % 2) == 0) 'a' else 'b'
        }

        assertEquals('a', map.getOrDefault(Vector2(4, 6), Char.MIN_VALUE))
        assertEquals('b', map.getOrDefault(Vector2(3, 6), Char.MIN_VALUE))
    }

    @Test
    fun `getOrDefault below x bounds`() {
        val map = CharGrid(width = 10, height = 20) { (x, _) ->
            if ((x % 2) == 0) 'a' else 'b'
        }

        assertEquals(Char.MIN_VALUE, map.getOrDefault(Vector2(-4, 6), Char.MIN_VALUE))
    }

    @Test
    fun `getOrDefault above x bounds`() {
        val map = CharGrid(width = 10, height = 20) { (x, _) ->
            if ((x % 2) == 0) 'a' else 'b'
        }

        assertEquals(Char.MIN_VALUE, map.getOrDefault(Vector2(22, 6), Char.MIN_VALUE))
    }

    @Test
    fun `getOrDefault below y bounds`() {
        val map = CharGrid(width = 10, height = 20) { (x, _) ->
            if ((x % 2) == 0) 'a' else 'b'
        }

        assertEquals(Char.MIN_VALUE, map.getOrDefault(Vector2(4, -5), Char.MIN_VALUE))
    }

    @Test
    fun `getOrDefault above y bounds`() {
        val map = CharGrid(width = 10, height = 20) { (x, _) ->
            if ((x % 2) == 0) 'a' else 'b'
        }

        assertEquals(Char.MIN_VALUE, map.getOrDefault(Vector2(4, 28), Char.MIN_VALUE))
    }

    @Test
    fun `getOrElse within bounds`() {
        val map = CharGrid(width = 10, height = 20) { (x, _) ->
            if ((x % 2) == 0) 'a' else 'b'
        }

        assertEquals('a', map.getOrElse(Vector2(4, 6)) { Char.MIN_VALUE })
        assertEquals('b', map.getOrElse(Vector2(3, 6)) { Char.MIN_VALUE })
    }

    @Test
    fun `getOrElse below x bounds`() {
        val map = CharGrid(width = 10, height = 20) { (x, _) ->
            if ((x % 2) == 0) 'a' else 'b'
        }

        assertEquals(Char.MIN_VALUE, map.getOrElse(Vector2(-4, 6)) { Char.MIN_VALUE })
    }

    @Test
    fun `getOrElse above x bounds`() {
        val map = CharGrid(width = 10, height = 20) { (x, _) ->
            if ((x % 2) == 0) 'a' else 'b'
        }

        assertEquals(Char.MIN_VALUE, map.getOrElse(Vector2(22, 6)) { Char.MIN_VALUE })
    }

    @Test
    fun `getOrElse below y bounds`() {
        val map = CharGrid(width = 10, height = 20) { (x, _) ->
            if ((x % 2) == 0) 'a' else 'b'
        }

        assertEquals(Char.MIN_VALUE, map.getOrElse(Vector2(4, -5)) { Char.MIN_VALUE })
    }

    @Test
    fun `getOrElse above y bounds`() {
        val map = CharGrid(width = 10, height = 20) { (x, _) ->
            if ((x % 2) == 0) 'a' else 'b'
        }

        assertEquals(Char.MIN_VALUE, map.getOrElse(Vector2(4, 28)) { Char.MIN_VALUE })
    }
}
