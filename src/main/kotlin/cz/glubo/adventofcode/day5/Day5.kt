package cz.glubo.adventofcode.day5

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.fold
import kotlin.math.max
import kotlin.math.min

val categories =
    listOf(
        "seed-to-soil map:",
        "soil-to-fertilizer map:",
        "fertilizer-to-water map:",
        "water-to-light map:",
        "light-to-temperature map:",
        "temperature-to-humidity map:",
        "humidity-to-location map:",
    )

data class ListElement(
    val from: Long,
    val length: Long,
    val applied: Boolean,
) {
    fun start() = from

    fun end() = from + length - 1
}

data class MapElement(
    val from: Long,
    val to: Long,
    val length: Long,
) {
    fun apply(input: Long) = if (contains(input)) input - from + to else input

    fun applyElement(input: ListElement): List<ListElement> {
        if (input.applied) {
            return listOf(input)
        }
        val outputs =
            listOfNotNull(
                if (input.start() < this.start()) {
                    val start = input.start()
                    val end = min(input.end(), this.start())
                    val len = end - start + 1
                    ListElement(start, len, false)
                } else {
                    null
                },
                if (this.start() <= input.end() && this.end() >= input.start()) {
                    val start = max(this.start(), input.start())
                    val end = min(this.end(), input.end())
                    val len = end - start + 1
                    ListElement(apply(start), len, true)
                } else {
                    null
                },
                if (input.end() > this.end()) {
                    val start = max(this.end(), input.start())
                    val end = input.end()
                    val len = end - start + 1
                    ListElement(start, len, false)
                } else {
                    null
                },
            )
        return outputs
    }

    fun contains(input: Long) = (from..<from + length).contains(input)

    fun start() = from

    fun end() = from + length - 1

    fun range() = (start()..end())

    companion object {
        fun parse(line: String): MapElement {
            val numbers =
                line.split(" ")
                    .map { it.toLong() }
            return MapElement(
                from = numbers[1],
                to = numbers[0],
                length = numbers[2],
            )
        }
    }
}

data class Model(
    val currentValues: List<Long>,
    val currentMap: List<MapElement>,
) {
    fun next() =
        Model(
            this.currentValues.map { value ->
                val matchingMap = this.currentMap.firstOrNull { it.contains(value) }
                matchingMap?.apply(value) ?: value
            },
            emptyList(),
        )
}

suspend fun Flow<String>.day5part1(): Int {
    val model =
        this.fold(
            Model(
                emptyList(),
                emptyList(),
            ),
        ) { acc, line ->
            when {
                line.startsWith("seeds:") -> {
                    Model(
                        line.removePrefix("seeds: ")
                            .split(" ")
                            .filter { it.isNotBlank() }
                            .map { it.toLong() },
                        acc.currentMap,
                    )
                }

                categories.contains(line) -> {
                    acc
                }

                line == "" -> {
                    acc.next()
                }

                else -> {
                    val currentMapElement = MapElement.parse(line)
                    Model(
                        acc.currentValues,
                        acc.currentMap + currentMapElement,
                    )
                }
            }
        }.next()

    return model.currentValues.min().toInt()
}

data class Model2(
    val currentValues: List<ListElement>,
    val currentMap: List<MapElement>,
) {
    fun next() =
        Model2(
            this.currentMap.fold(currentValues) { acc, mapElement ->
                acc.flatMap { listElement ->
                    mapElement.applyElement(listElement)
                }
            }.map { ListElement(it.from, it.length, false) },
            emptyList(),
        )
}

suspend fun Flow<String>.day5part2(): Int {
    val model =
        this.fold(
            Model2(
                emptyList(),
                emptyList(),
            ),
        ) { acc, line ->
            when {
                line.startsWith("seeds:") -> {
                    Model2(
                        line.removePrefix("seeds: ")
                            .split(" ")
                            .filter { it.isNotBlank() }
                            .chunked(2)
                            .map { ListElement(it[0].toLong(), it[1].toLong(), false) },
                        acc.currentMap,
                    )
                }

                categories.contains(line) -> {
                    acc
                }

                line == "" -> {
                    acc.next()
                }

                else -> {
                    val currentMapElement = MapElement.parse(line)
                    Model2(
                        acc.currentValues,
                        acc.currentMap + currentMapElement,
                    )
                }
            }
        }.next()

    return model.currentValues.map { it.start() }
        .min()
        .toInt()
}
