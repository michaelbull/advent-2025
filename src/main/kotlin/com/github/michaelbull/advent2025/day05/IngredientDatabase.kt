package com.github.michaelbull.advent2025.day05

typealias IngredientIdRange = LongRange
typealias IngredientId = Long

fun Sequence<String>.toIngredientDatabase(): IngredientDatabase {
    val iterator = iterator()

    val freshIdRanges = iterator.asSequence()
        .takeWhile(String::isNotEmpty)
        .map(String::toIngredientIdRange)
        .toList()

    val ids = iterator.asSequence()
        .map(String::toLong)
        .toList()

    return IngredientDatabase(
        ids = ids,
        freshIdRanges = freshIdRanges,
    )
}

private fun String.toIngredientIdRange(): IngredientIdRange {
    val (start, endInclusive) = split("-").map(String::toLong)
    return start..endInclusive
}

data class IngredientDatabase(
    val ids: List<IngredientId>,
    val freshIdRanges: List<IngredientIdRange>,
) {
    private val mergedRanges = freshIdRanges.merge()

    fun countFreshIds(): Long {
        return ids.count(mergedRanges::contains).toLong()
    }

    fun countTotalFreshIds(): Long {
        return mergedRanges.sumOf(IngredientIdRange::size)
    }
}

private fun List<IngredientIdRange>.merge(): List<IngredientIdRange> {
    return if (isEmpty()) {
        emptyList()
    } else {
        val sorted = sortedBy(IngredientIdRange::first)
        val merged = mutableListOf(sorted.first())

        for (range in sorted.drop(1)) {
            val previous = merged.last()
            val overlapsOrAdjacent = (previous.last + 1) >= range.first

            if (overlapsOrAdjacent) {
                val endInclusive = maxOf(previous.last, range.last)
                val expandedRange = previous.first..endInclusive
                merged[merged.lastIndex] = expandedRange
            } else {
                merged += range
            }
        }

        merged
    }
}

private operator fun List<IngredientIdRange>.contains(id: IngredientId): Boolean {
    return any { range ->
        id in range
    }
}

private val IngredientIdRange.size: Long
    get() = (last - first) + 1

