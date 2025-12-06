package cz.glubo.adventofcode.y2025.day5

import cz.glubo.adventofcode.utils.input.TestInput
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

/**
 * https://adventofcode.com/2025/day/5
 */
class Day5Test :
    StringSpec({

        "day5 example part 1 matches" {
            y2025day5part1(
                TestInput(
                    """
                    3-5
                    10-14
                    16-20
                    12-18

                    1
                    5
                    8
                    11
                    17
                    32
                    """.trimIndent(),
                ),
            ) shouldBe 3
        }

        "day5 example part 2 matches" {
            y2025day5part2(
                TestInput(
                    """
                    3-5
                    10-14
                    16-20
                    12-18

                    1
                    5
                    8
                    11
                    17
                    32
                    """.trimIndent(),
                ),
            ) shouldBe 14
        }
    })
