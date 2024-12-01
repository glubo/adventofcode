package cz.glubo.adventofcode.y2024.day1

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.flowOf

/**
 * https://adventofcode.com/2023/day/1
 */
@Suppress("UNUSED")
class Day1Test : StringSpec({

    "day1 example part 1 matches" {
        y2024day1part1(
            flowOf(
                "3   4",
                "4   3",
                "2   5",
                "1   3",
                "3   9",
                "3   3",
            )
        ) shouldBe 11
    }

    "day1 example part 2 matches" {
        y2024day1part2(
            flowOf(
                "3   4",
                "4   3",
                "2   5",
                "1   3",
                "3   9",
                "3   3",
            )
        ) shouldBe 31
    }

})
