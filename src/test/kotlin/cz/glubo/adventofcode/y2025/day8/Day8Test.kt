package cz.glubo.adventofcode.y2025.day8

import cz.glubo.adventofcode.utils.input.TestInput
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

/**
 * https://adventofcode.com/2025/day/8
 */
class Day8Test :
    StringSpec({

        "day8 example part 1 matches" {
            y2025day8part1(
                TestInput(
                    """
                    """.trimIndent(),
                ),
            ) shouldBe 0
        }

        "day8 example part 2 matches" {
            y2025day8part2(
                TestInput(
                    """
                    """.trimIndent(),
                ),
            ) shouldBe 0
        }
    })
