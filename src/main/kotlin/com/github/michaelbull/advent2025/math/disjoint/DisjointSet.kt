package com.github.michaelbull.advent2025.math.disjoint

interface DisjointSet<T> : Set<T> {
    fun find(element: T): T
    fun sameSet(a: T, b: T): Boolean
    fun partitions(): Set<Set<T>>
    fun partitionSizes(): Collection<Int>
}

interface MutableDisjointSet<T> : DisjointSet<T> {
    fun union(a: T, b: T): Boolean
}
