@file:OptIn(ExperimentalCoroutinesApi::class)

package cz.glubo.adventofcode.day2

import cz.glubo.adventofcode.day5.MapElement
import cz.glubo.adventofcode.day5.day5part1
import cz.glubo.adventofcode.day5.day5part2
import io.kotest.core.spec.style.FreeSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf

/**
 * https://adventofcode.com/2023/day/5
 */
class Day5Test : FreeSpec({
    "We can solve the example" {
        flowOf(
            "seeds: 79 14 55 13",
            "",
            "seed-to-soil map:",
            "50 98 2",
            "52 50 48",
            "",
            "soil-to-fertilizer map:",
            "0 15 37",
            "37 52 2",
            "39 0 15",
            "",
            "fertilizer-to-water map:",
            "49 53 8",
            "0 11 42",
            "42 0 7",
            "57 7 4",
            "",
            "water-to-light map:",
            "88 18 7",
            "18 25 70",
            "",
            "light-to-temperature map:",
            "45 77 23",
            "81 45 19",
            "68 64 13",
            "",
            "temperature-to-humidity map:",
            "0 69 1",
            "1 0 69",
            "",
            "humidity-to-location map:",
            "60 56 37",
            "56 93 4",
        ).day5part1() shouldBe 35
    }

    "simple map elements" - {
        withData(
            Pair("1 2 3", 0) to 0,
            Pair("1 2 3", 2) to 1,
            Pair("52 50 48", 53) to 55,
            Pair("50 98 2", 99) to 51,
        ) {
            MapElement.parse(it.first.first).apply(it.first.second.toLong()) shouldBe it.second
        }
    }

    "We can solve the example part 2" {
        flowOf(
            "seeds: 79 14 55 13",
            "",
            "seed-to-soil map:",
            "50 98 2",
            "52 50 48",
            "",
            "soil-to-fertilizer map:",
            "0 15 37",
            "37 52 2",
            "39 0 15",
            "",
            "fertilizer-to-water map:",
            "49 53 8",
            "0 11 42",
            "42 0 7",
            "57 7 4",
            "",
            "water-to-light map:",
            "88 18 7",
            "18 25 70",
            "",
            "light-to-temperature map:",
            "45 77 23",
            "81 45 19",
            "68 64 13",
            "",
            "temperature-to-humidity map:",
            "0 69 1",
            "1 0 69",
            "",
            "humidity-to-location map:",
            "60 56 37",
            "56 93 4",
        ).day5part2() shouldBe 46
    }
})
