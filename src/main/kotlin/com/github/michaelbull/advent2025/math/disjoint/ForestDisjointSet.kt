package com.github.michaelbull.advent2025.math.disjoint

class ForestDisjointSet<T> private constructor(
    private val indexToElement: List<T>,
    private val elementToIndex: Map<T, Int>,
    private val parents: IntArray,
    private val ranks: IntArray,
) : MutableDisjointSet<T> {

    constructor(elements: List<T>) : this(
        indexToElement = elements,
        elementToIndex = elements.withIndex().associate { (index, element) -> element to index },
        parents = IntArray(elements.size) { it },
        ranks = IntArray(elements.size) { 0 },
    )

    override val size: Int
        get() = indexToElement.size

    override fun isEmpty(): Boolean {
        return indexToElement.isEmpty()
    }

    override fun contains(element: T): Boolean {
        return element in elementToIndex
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        return elements.all(::contains)
    }

    override fun iterator(): Iterator<T> {
        return indexToElement.iterator()
    }

    override fun find(element: T): T {
        val rootIndex = rootOf(element)
        return elementAt(rootIndex)
    }

    override fun union(a: T, b: T): Boolean {
        val rootA = rootOf(a)
        val rootB = rootOf(b)

        if (rootA == rootB) {
            return false
        }

        val rankA = ranks[rootA]
        val rankB = ranks[rootB]

        when {
            rankA < rankB -> {
                parents[rootA] = rootB
            }

            rankA > rankB -> {
                parents[rootB] = rootA
            }

            else -> {
                parents[rootB] = rootA
                ranks[rootA]++
            }
        }

        return true
    }

    override fun sameSet(a: T, b: T): Boolean {
        return rootOf(a) == rootOf(b)
    }

    override fun partitions(): Set<Set<T>> {
        return parents.indices
            .groupBy(::findRoot)
            .values
            .mapTo(mutableSetOf(), ::toElementSet)
    }

    private fun rootOf(element: T): Int {
        return findRoot(indexOf(element))
    }

    private fun indexOf(element: T): Int {
        return elementToIndex.getValue(element)
    }

    private fun elementAt(index: Int): T {
        return indexToElement[index]
    }

    private fun toElementSet(indices: List<Int>): Set<T> {
        return indices.mapTo(mutableSetOf(), ::elementAt)
    }

    override fun partitionSizes(): Collection<Int> {
        return parents.indices
            .groupingBy(::findRoot)
            .eachCount()
            .values
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }

        if (other !is DisjointSet<*>) {
            return false
        }

        return partitions() == other.partitions()
    }

    override fun hashCode(): Int {
        return partitions().hashCode()
    }

    override fun toString(): String {
        return partitions().toString()
    }

    private fun findRoot(index: Int): Int {
        return findRootOf(index).also { root ->
            compressPath(from = index, to = root)
        }
    }

    private tailrec fun findRootOf(index: Int): Int {
        return if (parents[index] == index) {
            index
        } else {
            findRootOf(parents[index])
        }
    }

    private tailrec fun compressPath(from: Int, to: Int) {
        if (from == to) {
            return
        }

        val next = parents[from]
        parents[from] = to
        compressPath(next, to)
    }
}

fun <T> List<T>.toDisjointSet(): DisjointSet<T> {
    return ForestDisjointSet(this)
}

fun <T> List<T>.toMutableDisjointSet(): MutableDisjointSet<T> {
    return ForestDisjointSet(this)
}
