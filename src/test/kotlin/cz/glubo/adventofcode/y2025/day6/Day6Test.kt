package cz.glubo.adventofcode.y2025.day6

import cz.glubo.adventofcode.utils.input.TestInput
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

/**
 * https://adventofcode.com/2025/day/6
 */
class Day6Test :
    StringSpec({

        "day6 example part 1 matches" {
            y2025day6part1(
                TestInput(
                    """
                    """.trimIndent(),
                ),
            ) shouldBe 0
        }

        "day6 example part 2 matches" {
            y2025day6part2(
                TestInput(
                    """
                    """.trimIndent(),
                ),
            ) shouldBe 0
        }
    })
