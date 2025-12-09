package com.github.michaelbull.advent2025.math.disjoint

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class ForestDisjointSetTest {

    @Test
    fun `size returns element count`() {
        val set = listOf("a", "b", "c").toMutableDisjointSet()
        assertEquals(3, set.size)
    }

    @Test
    fun `isEmpty returns true when empty`() {
        val set = emptyList<String>().toMutableDisjointSet()
        assertTrue(set.isEmpty())
    }

    @Test
    fun `isEmpty returns false when not empty`() {
        val set = listOf("a").toMutableDisjointSet()
        assertFalse(set.isEmpty())
    }

    @Test
    fun `contains returns true for element in set`() {
        val set = listOf("a", "b", "c").toMutableDisjointSet()
        assertTrue(set.contains("b"))
    }

    @Test
    fun `contains returns false for element not in set`() {
        val set = listOf("a", "b", "c").toMutableDisjointSet()
        assertFalse(set.contains("d"))
    }

    @Test
    fun `containsAll returns true when all elements present`() {
        val set = listOf("a", "b", "c").toMutableDisjointSet()
        assertTrue(set.containsAll(listOf("a", "c")))
    }

    @Test
    fun `containsAll returns false when element missing`() {
        val set = listOf("a", "b", "c").toMutableDisjointSet()
        assertFalse(set.containsAll(listOf("a", "d")))
    }

    @Test
    fun `iterator returns all elements`() {
        val set = listOf("a", "b", "c").toMutableDisjointSet()
        assertEquals(listOf("a", "b", "c"), set.toList())
    }

    @Test
    fun `find returns element itself when no unions`() {
        val set = listOf("a", "b", "c").toMutableDisjointSet()
        assertEquals("a", set.find("a"))
    }

    @Test
    fun `union returns true when joining different sets`() {
        val set = listOf("a", "b", "c").toMutableDisjointSet()
        assertTrue(set.union("a", "b"))
    }

    @Test
    fun `union returns false when already in same set`() {
        val set = listOf("a", "b", "c").toMutableDisjointSet()
        set.union("a", "b")
        assertFalse(set.union("a", "b"))
    }

    @Test
    fun `union is transitive`() {
        val set = listOf("a", "b", "c").toMutableDisjointSet()
        set.union("a", "b")
        set.union("b", "c")
        assertTrue(set.sameSet("a", "c"))
    }

    @Test
    fun `union with equal ranks attaches second to first`() {
        val set = listOf("a", "b").toMutableDisjointSet()
        set.union("a", "b")
        assertEquals("a", set.find("b"))
    }

    @Test
    fun `union with smaller rank first attaches first to second`() {
        val set = listOf("a", "b", "c").toMutableDisjointSet()
        set.union("a", "b")
        set.union("c", "a")
        assertEquals("a", set.find("c"))
    }

    @Test
    fun `union with larger rank first attaches second to first`() {
        val set = listOf("a", "b", "c").toMutableDisjointSet()
        set.union("a", "b")
        set.union("a", "c")
        assertEquals("a", set.find("c"))
    }

    @Test
    fun `sameSet returns true for same element`() {
        val set = listOf("a", "b", "c").toMutableDisjointSet()
        assertTrue(set.sameSet("a", "a"))
    }

    @Test
    fun `sameSet returns false for different sets`() {
        val set = listOf("a", "b", "c").toMutableDisjointSet()
        assertFalse(set.sameSet("a", "b"))
    }

    @Test
    fun `sameSet returns true after union`() {
        val set = listOf("a", "b", "c").toMutableDisjointSet()
        set.union("a", "b")
        assertTrue(set.sameSet("a", "b"))
    }

    @Test
    fun `partitions returns singleton sets when no unions`() {
        val set = listOf("a", "b", "c").toMutableDisjointSet()
        assertEquals(setOf(setOf("a"), setOf("b"), setOf("c")), set.partitions())
    }

    @Test
    fun `partitions returns merged sets after union`() {
        val set = listOf("a", "b", "c").toMutableDisjointSet()
        set.union("a", "b")
        assertEquals(setOf(setOf("a", "b"), setOf("c")), set.partitions())
    }

    @Test
    fun `partitionSizes returns ones when no unions`() {
        val set = listOf("a", "b", "c").toMutableDisjointSet()
        assertEquals(listOf(1, 1, 1), set.partitionSizes().sorted())
    }

    @Test
    fun `partitionSizes returns merged sizes after union`() {
        val set = listOf("a", "b", "c").toMutableDisjointSet()
        set.union("a", "b")
        assertEquals(listOf(1, 2), set.partitionSizes().sorted())
    }

    @Test
    fun `equals returns true for same partitions`() {
        val set1 = listOf("a", "b", "c").toMutableDisjointSet()
        val set2 = listOf("a", "b", "c").toMutableDisjointSet()
        set1.union("a", "b")
        set2.union("b", "a")
        assertEquals(set1, set2)
    }

    @Test
    fun `equals returns false for different partitions`() {
        val set1 = listOf("a", "b", "c").toMutableDisjointSet()
        val set2 = listOf("a", "b", "c").toMutableDisjointSet()
        set1.union("a", "b")
        set2.union("a", "c")
        assertNotEquals(set1, set2)
    }

    @Test
    fun `hashCode is same for equal sets`() {
        val set1 = listOf("a", "b", "c").toMutableDisjointSet()
        val set2 = listOf("a", "b", "c").toMutableDisjointSet()
        set1.union("a", "b")
        set2.union("b", "a")
        assertEquals(set1.hashCode(), set2.hashCode())
    }

    @Test
    fun `toString shows partitions`() {
        val set = listOf("a", "b").toMutableDisjointSet()
        set.union("a", "b")
        assertEquals("[[a, b]]", set.toString())
    }
}
