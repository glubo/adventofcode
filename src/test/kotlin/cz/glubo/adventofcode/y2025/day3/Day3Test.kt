package cz.glubo.adventofcode.y2025.day3

import cz.glubo.adventofcode.utils.input.TestInput
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

/**
 * https://adventofcode.com/2025/day/3
 */
class Day3Test :
    StringSpec({

        "day3 example part 1 matches" {
            y2025day3part1(
                TestInput(
                    """
                    987654321111111
                    811111111111119
                    234234234234278
                    818181911112111
                    """.trimIndent(),
                ),
            ) shouldBe 357
        }

        "day3 example part 2 matches" {
            y2025day3part2(
                TestInput(
                    """
                    987654321111111
                    811111111111119
                    234234234234278
                    818181911112111
                    """.trimIndent(),
                ),
            ) shouldBe 3121910778619L
        }
    })
