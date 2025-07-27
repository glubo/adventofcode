package cz.glubo.adventofcode.y2024.day25

import cz.glubo.adventofcode.utils.input.TestInput
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

/**
 * https://adventofcode.com/2024/day/25
 */
class Day25Test :
    StringSpec({

        "day25 example part 1 matches" {
            y2024day25part1(
                TestInput(
                    """
                    #####
                    .####
                    .####
                    .####
                    .#.#.
                    .#...
                    .....

                    #####
                    ##.##
                    .#.##
                    ...##
                    ...#.
                    ...#.
                    .....

                    .....
                    #....
                    #....
                    #...#
                    #.#.#
                    #.###
                    #####

                    .....
                    .....
                    #.#..
                    ###..
                    ###.#
                    ###.#
                    #####

                    .....
                    .....
                    .....
                    #....
                    #.#..
                    #.#.#
                    #####
                    """.trimIndent(),
                ),
            ) shouldBe 3
        }

        "day25 example part 2 matches" {
            y2024day25part2(
                TestInput(
                    """
                    """.trimIndent(),
                ),
            ) shouldBe 0
        }
    })
