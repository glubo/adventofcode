package cz.glubo.adventofcode.y2024.day9

import cz.glubo.adventofcode.utils.input.TestInput
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

/**
 * https://adventofcode.com/2024/day/9
 */
@Suppress("UNUSED")
class Day9Test :
    StringSpec({

        "day9 example part 1 matches" {
            y2024day9part1(
                TestInput(
                    """
                    2333133121414131402
                    """.trimIndent(),
                ),
            ) shouldBe 1928
        }

        "day9 example part 2 matches" {
            y2024day9part2(
                TestInput(
                    """
                    2333133121414131402
                    """.trimIndent(),
                ),
            ) shouldBe 2858
        }
    })
