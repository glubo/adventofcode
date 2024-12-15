package cz.glubo.adventofcode.y2024.day14

import cz.glubo.adventofcode.utils.IVec2
import cz.glubo.adventofcode.utils.input.TestInput
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

/**
 * https://adventofcode.com/2024/day/14
 */
class Day14Test :
    StringSpec({

        "day14 small example part 1 matches" {
            y2024day14part1(
                TestInput(
                    """
                    p=2,4 v=2,-3
                    """.trimIndent(),
                ),
                IVec2(11, 7),
            ) shouldBe 0
        }

        "day14 example part 1 matches" {
            y2024day14part1(
                TestInput(
                    """
                    p=0,4 v=3,-3
                    p=6,3 v=-1,-3
                    p=10,3 v=-1,2
                    p=2,0 v=2,-1
                    p=0,0 v=1,3
                    p=3,0 v=-2,-2
                    p=7,6 v=-1,-3
                    p=3,0 v=-1,-2
                    p=9,3 v=2,3
                    p=7,3 v=-1,2
                    p=2,4 v=2,-3
                    p=9,5 v=-3,-3
                    """.trimIndent(),
                ),
                IVec2(11, 7),
            ) shouldBe 12
        }

        "day14 example part 2 matches" {
            y2024day14part2(
                TestInput(
                    """
                    """.trimIndent(),
                ),
                IVec2(11, 7),
            ) shouldBe 0
        }
    })
