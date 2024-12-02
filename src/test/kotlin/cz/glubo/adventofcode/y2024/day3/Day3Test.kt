package cz.glubo.adventofcode.y2024.day3

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.flowOf

/**
 * https://adventofcode.com/2023/day/1
 */
@Suppress("UNUSED")
class Day3Test : StringSpec({

    "day3 example part 1 matches" {
        y2024day3part1(
            flowOf(
                "pqr3stu8vwx",
                "a1b2c3d4e5f",
                "treb7uchet",
            )
        ) shouldBe 0
    }

    "day3 example part 2 matches" {
        y2024day3part2(
            flowOf(
                "pqr3stu8vwx",
                "a1b2c3d4e5f",
                "treb7uchet",
            )
        ) shouldBe 0
    }

})
