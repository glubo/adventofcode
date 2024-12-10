package cz.glubo.adventofcode.y2024.day11

import cz.glubo.adventofcode.utils.input.TestInput
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

/**
 * https://adventofcode.com/2024/day/11
 */
class Day11Test :
    StringSpec({

        "day11 example part 1 matches" {
            y2024day11part1(
                TestInput(
                    """
                    """.trimIndent(),
                ),
            ) shouldBe 0
        }

        "day11 example part 2 matches" {
            y2024day11part2(
                TestInput(
                    """
                    """.trimIndent(),
                ),
            ) shouldBe 0
        }
    })
