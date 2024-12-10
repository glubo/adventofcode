package cz.glubo.adventofcode.y2024.day10

import cz.glubo.adventofcode.utils.input.TestInput
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

/**
 * https://adventofcode.com/2024/day/10
 */
@Suppress("UN1USED")
class Day10Test :
    StringSpec({

        "day10 example part 1 matches" {
            y2024day10part1(
                TestInput(
                    """
                    ...0...
                    ...1...
                    ...2...
                    6543456
                    7.....7
                    8.....8
                    9.....9
                    """.trimIndent(),
                ),
            ) shouldBe 2
        }

        "day10 example 2 part 1 matches" {
            y2024day10part1(
                TestInput(
                    """
                    10..9..
                    2...8..
                    3...7..
                    4567654
                    ...8..3
                    ...9..2
                    .....01
                    """.trimIndent(),
                ),
            ) shouldBe 3
        }

        "day10 example 3 part 1 matches" {
            y2024day10part1(
                TestInput(
                    """
                    89010123
                    78121874
                    87430965
                    96549874
                    45678903
                    32019012
                    01329801
                    10456732
                    """.trimIndent(),
                ),
            ) shouldBe 36
        }

        "day10 example part 2 matches" {
            y2024day10part2(
                TestInput(
                    """
                    89010123
                    78121874
                    87430965
                    96549874
                    45678903
                    32019012
                    01329801
                    10456732
                    """.trimIndent(),
                ),
            ) shouldBe 81
        }
    })
