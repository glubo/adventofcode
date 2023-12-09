package cz.glubo.adventofcode.day9

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.flowOf

/**
 * https://adventofcode.com/2023/day/9
 */
@Suppress("UNUSED")
class Day9Test : FreeSpec({
    "We can solve the example" {
        flowOf(
            "0 3 6 9 12 15",
            "1 3 6 10 15 21",
            "10 13 16 21 30 45",
        ).day9part1() shouldBe 114
    }

    "We can solve the example part 2" {
        flowOf(
            "0 3 6 9 12 15",
            "1 3 6 10 15 21",
            "10 13 16 21 30 45",
        ).day9part2() shouldBe 2
    }
})
