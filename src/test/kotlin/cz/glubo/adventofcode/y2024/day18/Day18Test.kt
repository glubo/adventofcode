package cz.glubo.adventofcode.y2024.day18

import cz.glubo.adventofcode.utils.input.TestInput
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

/**
 * https://adventofcode.com/2024/day/18
 */
class Day18Test :
    StringSpec({

        "day18 example part 1 matches" {
            y2024day18part1(
                TestInput(
                    """
                    5,4
                    4,2
                    4,5
                    3,0
                    2,1
                    6,3
                    2,4
                    1,5
                    0,6
                    3,3
                    2,6
                    5,1
                    1,2
                    5,5
                    2,5
                    6,5
                    1,4
                    0,4
                    6,4
                    1,1
                    6,1
                    1,0
                    0,5
                    1,6
                    2,0
                    """.trimIndent(),
                ),
                12,
                6,
            ) shouldBe 22
        }

        "day18 example part 2 matches" {
            y2024day18part2(
                TestInput(
                    """
                    5,4
                    4,2
                    4,5
                    3,0
                    2,1
                    6,3
                    2,4
                    1,5
                    0,6
                    3,3
                    2,6
                    5,1
                    1,2
                    5,5
                    2,5
                    6,5
                    1,4
                    0,4
                    6,4
                    1,1
                    6,1
                    1,0
                    0,5
                    1,6
                    2,0
                    """.trimIndent(),
                ),
                12,
                6,
            ) shouldBe "6,1"
        }
    })
