package cz.glubo.adventofcode.y2025.day4

import cz.glubo.adventofcode.utils.input.TestInput
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

/**
 * https://adventofcode.com/2025/day/4
 */
class Day4Test :
    StringSpec({

        "day4 example part 1 matches" {
            y2025day4part1(
                TestInput(
                    """
                    """.trimIndent(),
                ),
            ) shouldBe 0
        }

        "day4 example part 2 matches" {
            y2025day4part2(
                TestInput(
                    """
                    """.trimIndent(),
                ),
            ) shouldBe 0
        }
    })
