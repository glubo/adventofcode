package cz.glubo.adventofcode.y2024.day8

import cz.glubo.adventofcode.utils.input.TestInput
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.flowOf

/**
 * https://adventofcode.com/2024/day/8
 */
@Suppress("UNUSED")
class Day8Test :
    StringSpec({

        "day8 example part 1 matches" {
            y2024day8part1(
                TestInput(
                    flowOf(
                        "..........",
                        "..........",
                        "..........",
                        "....a.....",
                        "..........",
                        ".....a....",
                        "..........",
                        "..........",
                        "..........",
                        "..........",
                    ),
                ),
            ) shouldBe 2
        }

        "day8 example part 1 matches 2" {
            y2024day8part1(
                TestInput(
                    """
                    ..........
                    ..........
                    ..........
                    ....a.....
                    ........a.
                    .....a....
                    ..........
                    ..........
                    ..........
                    ..........
                    """.trimIndent(),
                ),
            ) shouldBe 4
        }
        "day8 example part 1 matches 3" {
            y2024day8part1(
                TestInput(
                    flowOf(
                        "............",
                        "........0...",
                        ".....0......",
                        ".......0....",
                        "....0.......",
                        "......A.....",
                        "............",
                        "............",
                        "........A...",
                        ".........A..",
                        "............",
                        "............",
                    ),
                ),
            ) shouldBe 14
        }

        "day8 example part 2 matches" {
            y2024day8part2(
                TestInput(
                    flowOf(
                        "T.........",
                        "...T......",
                        ".T........",
                        "..........",
                        "..........",
                        "..........",
                        "..........",
                        "..........",
                        "..........",
                        "..........",
                    ),
                ),
            ) shouldBe 9
        }

        "day8 example 2 part 2 matches" {
            y2024day8part2(
                TestInput(
                    flowOf(
                        "............",
                        "........0...",
                        ".....0......",
                        ".......0....",
                        "....0.......",
                        "......A.....",
                        "............",
                        "............",
                        "........A...",
                        ".........A..",
                        "............",
                        "............",
                    ),
                ),
            ) shouldBe 34
        }
    })
