package cz.glubo.adventofcode.y2024.day23

import cz.glubo.adventofcode.utils.input.TestInput
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

/**
 * https://adventofcode.com/2024/day/23
 */
class Day23Test :
    StringSpec({

        "day23 example part 1 matches" {
            y2024day23part1(
                TestInput(
                    """
                    """.trimIndent(),
                ),
            ) shouldBe 0
        }

        "day23 example part 2 matches" {
            y2024day23part2(
                TestInput(
                    """
                    """.trimIndent(),
                ),
            ) shouldBe 0
        }
    })
