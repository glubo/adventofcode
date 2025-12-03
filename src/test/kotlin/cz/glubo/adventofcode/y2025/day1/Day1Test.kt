package cz.glubo.adventofcode.y2025.day1

import cz.glubo.adventofcode.utils.input.TestInput
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

/**
 * https://adventofcode.com/2025/day/1
 */
class Day1Test :
    StringSpec({

        "day1 example part 1 matches" {
            y2025day1part1(
                TestInput(
                    """
                    L68
                    L30
                    R48
                    L5
                    R60
                    L55
                    L1
                    L99
                    R14
                    L82
                    """.trimIndent(),
                ),
            ) shouldBe 3
        }

        "day1 example part 2 matches" {
            y2025day1part2(
                TestInput(
                    """
                    L68
                    L30
                    R48
                    L5
                    R60
                    L55
                    L1
                    L99
                    R14
                    L82
                    """.trimIndent(),
                ),
            ) shouldBe 6
        }
    })
