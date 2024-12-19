package cz.glubo.adventofcode.y2024.day19

import cz.glubo.adventofcode.utils.input.TestInput
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

/**
 * https://adventofcode.com/2024/day/19
 */
class Day19Test :
    StringSpec({

        "day19 example part 1 matches" {
            y2024day19part1(
                TestInput(
                    """
                    r, wr, b, g, bwu, rb, gb, br

                    brwrr
                    bggr
                    gbbr
                    rrbgbr
                    ubwu
                    bwurrg
                    brgr
                    bbrgwb
                    """.trimIndent(),
                ),
            ) shouldBe 6
        }

        "day19 example part 2 matches" {
            y2024day19part2(
                TestInput(
                    """
                    r, wr, b, g, bwu, rb, gb, br

                    brwrr
                    bggr
                    gbbr
                    rrbgbr
                    ubwu
                    bwurrg
                    brgr
                    bbrgwb
                    """.trimIndent(),
                ),
            ) shouldBe 16
        }
    })
