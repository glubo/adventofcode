package cz.glubo.adventofcode.y2024.day2

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.flowOf

/**
 * https://adventofcode.com/2023/day/1
 */
@Suppress("UNUSED")
class Day2Test : StringSpec({

    "dayN example part 1 matches" {
        y2024day2part1(
            flowOf(
                "7 6 4 2 1",
                "1 2 7 8 9",
                "9 7 6 2 1",
                "1 3 2 4 5",
                "8 6 4 4 1",
                "1 3 6 7 9",
            )
        ) shouldBe 2
    }

    "dayN example part 2 matches" {
        y2024day2part2(
            flowOf(
                "7 6 4 2 1",
                "1 2 7 8 9",
                "9 7 6 2 1",
                "1 3 2 4 5",
                "8 6 4 4 1",
                "1 3 6 7 9",
            )
        ) shouldBe 4
    }

})
