package cz.glubo.adventofcode.y2024.day6

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.flowOf

/**
 * https://adventofcode.com/2024/day/6
 */
@Suppress("UNUSED")
class Day6Test :
    StringSpec({

        "day6 example part 1 matches" {
            y2024day6part1(
                flowOf(
                    "....#.....",
                    ".........#",
                    "..........",
                    "..#.......",
                    ".......#..",
                    "..........",
                    ".#..^.....",
                    "........#.",
                    "#.........",
                    "......#...",
                ),
            ) shouldBe 41
        }

        "day6 example part 2 matches" {
            y2024day6part2(
                flowOf(
                    "....#.....",
                    ".........#",
                    "..........",
                    "..#.......",
                    ".......#..",
                    "..........",
                    ".#..^.....",
                    "........#.",
                    "#.........",
                    "......#...",
                ),
            ) shouldBe 6
        }
    })
