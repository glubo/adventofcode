package cz.glubo.adventofcode.y2024.day7

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.flowOf

/**
 * https://adventofcode.com/2024/day/7
 */
@Suppress("UNUSED")
class Day7Test :
    StringSpec({

        "day7 example part 1 matches" {
            y2024day7part1(
                flowOf(
                    "190: 10 19",
                    "3267: 81 40 27",
                    "83: 17 5",
                    "156: 15 6",
                    "7290: 6 8 6 15",
                    "161011: 16 10 13",
                    "192: 17 8 14",
                    "21037: 9 7 18 13",
                    "292: 11 6 16 20",
                ),
            ) shouldBe 3749
        }

        "day7 example part 2 matches" {
            y2024day7part2(
                flowOf(
                    "190: 10 19",
                    "3267: 81 40 27",
                    "83: 17 5",
                    "156: 15 6",
                    "7290: 6 8 6 15",
                    "161011: 16 10 13",
                    "192: 17 8 14",
                    "21037: 9 7 18 13",
                    "292: 11 6 16 20",
                ),
            ) shouldBe 11387
        }
    })
