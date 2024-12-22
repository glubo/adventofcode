package cz.glubo.adventofcode.y2024.day22

import cz.glubo.adventofcode.utils.input.TestInput
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

/**
 * https://adventofcode.com/2024/day/22
 */
class Day22Test :
    StringSpec({

        "first step" {
            step(123) shouldBe 15887950
        }

        "day22 example part 1 matches" {
            y2024day22part1(
                TestInput(
                    """
                    1
                    10
                    100
                    2024
                    """.trimIndent(),
                ),
            ) shouldBe 37327623
        }

        "day22 example part 2 matches" {
            y2024day22part2(
                TestInput(
                    """
                    1
                    2
                    3
                    2024
                    """.trimIndent(),
                ),
            ) shouldBe 23
        }
    })
