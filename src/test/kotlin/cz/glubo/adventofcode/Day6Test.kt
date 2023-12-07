@file:OptIn(ExperimentalCoroutinesApi::class)

package cz.glubo.adventofcode.day2

import cz.glubo.adventofcode.day4.day4part1
import cz.glubo.adventofcode.day4.day4part2
import cz.glubo.adventofcode.day4.pow2
import cz.glubo.adventofcode.day5.MapElement
import cz.glubo.adventofcode.day5.day5part1
import cz.glubo.adventofcode.day5.day5part2
import cz.glubo.adventofcode.day6.day6part1
import cz.glubo.adventofcode.day6.day6part2
import io.kotest.core.spec.style.FreeSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf

/**
 * https://adventofcode.com/2023/day/5
 */
class Day6Test : FreeSpec({
    "We can solve the example" {
        flowOf(
            "Time:      7  15   30",
            "Distance:  9  40  200",
        ).day6part1() shouldBe 288
    }


    "We can solve the example part 2" {
        flowOf(
            "Time:      7  15   30",
            "Distance:  9  40  200",
        ).day6part2() shouldBe 71503
    }
})
