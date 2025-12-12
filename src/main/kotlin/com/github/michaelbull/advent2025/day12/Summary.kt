package com.github.michaelbull.advent2025.day12

data class Summary(
    val orientations: List<Set<Shape>>,
    val regions: List<Region>,
) {
    fun countFittingRegions(): Int {
        return regions.count { region ->
            region fitsAll orientations
        }
    }
}

fun Sequence<String>.toSummary(): Summary {
    val sections = toList()
        .splitOn(String::isEmpty)
        .filter(List<String>::isNotEmpty)

    val (shapeSections, regionSections) = sections.partition { section ->
        Shape.HEADER_REGEX.matches(section.first())
    }

    val orientations = shapeSections.map { section ->
        section.drop(1).toShape().orientations()
    }

    val regions = regionSections.flatMap { section ->
        section.map(String::toRegion)
    }

    return Summary(orientations, regions)
}

private inline fun <T> List<T>.splitOn(predicate: (T) -> Boolean): List<List<T>> {
    return buildList {
        var current = mutableListOf<T>()

        for (element in this@splitOn) {
            if (predicate(element)) {
                add(current)
                current = mutableListOf()
            } else {
                current.add(element)
            }
        }

        add(current)
    }
}
