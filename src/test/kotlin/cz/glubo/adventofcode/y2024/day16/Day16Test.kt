package cz.glubo.adventofcode.y2024.day16

import cz.glubo.adventofcode.utils.input.TestInput
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

/**
 * https://adventofcode.com/2024/day/16
 */
class Day16Test :
    StringSpec({

        "day16 example part 1 matches" {
            y2024day16part1(
                TestInput(
                    """
                    ###############
                    #.......#....E#
                    #.#.###.#.###.#
                    #.....#.#...#.#
                    #.###.#####.#.#
                    #.#.#.......#.#
                    #.#.#####.###.#
                    #...........#.#
                    ###.#.#####.#.#
                    #...#.....#.#.#
                    #.#.#.###.#.#.#
                    #.....#...#.#.#
                    #.###.#.#.#.#.#
                    #S..#.....#...#
                    ###############
                    """.trimIndent(),
                ),
            ) shouldBe 7036
        }

        "day16 example part 2 matches" {
            y2024day16part2(
                TestInput(
                    """
                    ###############
                    #.......#....E#
                    #.#.###.#.###.#
                    #.....#.#...#.#
                    #.###.#####.#.#
                    #.#.#.......#.#
                    #.#.#####.###.#
                    #...........#.#
                    ###.#.#####.#.#
                    #...#.....#.#.#
                    #.#.#.###.#.#.#
                    #.....#...#.#.#
                    #.###.#.#.#.#.#
                    #S..#.....#...#
                    ###############
                    """.trimIndent(),
                ),
            ) shouldBe 45
        }
        "day16 my example part 2 matches" {
            y2024day16part2(
                TestInput(
                    """
                    #####
                    ###E#
                    #...#
                    #.#.#
                    #...#
                    #S###
                    #####
                    """.trimIndent(),
                ),
            ) shouldBe 10
        }
    })
