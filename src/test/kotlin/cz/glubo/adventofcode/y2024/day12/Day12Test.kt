package cz.glubo.adventofcode.y2024.day12

import cz.glubo.adventofcode.utils.input.TestInput
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

/**
 * https://adventofcode.com/2024/day/12
 */
class Day12Test :
    StringSpec({

        "day12 example part 1 matches" {
            y2024day12part1(
                TestInput(
                    """
                    RRRRIICCFF
                    RRRRIICCCF
                    VVRRRCCFFF
                    VVRCCCJFFF
                    VVVVCJJCFE
                    VVIVCCJJEE
                    VVIIICJJEE
                    MIIIIIJJEE
                    MIIISIJEEE
                    MMMISSJEEE
                    """.trimIndent(),
                ),
            ) shouldBe 1930
        }

        "day12 example part 2 matches" {
            y2024day12part2(
                TestInput(
                    """
                    RRRRIICCFF
                    RRRRIICCCF
                    VVRRRCCFFF
                    VVRCCCJFFF
                    VVVVCJJCFE
                    VVIVCCJJEE
                    VVIIICJJEE
                    MIIIIIJJEE
                    MIIISIJEEE
                    MMMISSJEEE
                    """.trimIndent(),
                ),
            ) shouldBe 1206
        }

        "day12 example AABB" {
            y2024day12part2(
                TestInput(
                    """
                    AAAAAA
                    AAABBA
                    AAABBA
                    ABBAAA
                    ABBAAA
                    AAAAAA
                    """.trimIndent(),
                ),
            ) shouldBe 368
        }

        "day12 example E" {
            y2024day12part2(
                TestInput(
                    """
                    EEEEE
                    EXXXX
                    EEEEE
                    EXXXX
                    EEEEE
                    """.trimIndent(),
                ),
            ) shouldBe 236
        }
    })
