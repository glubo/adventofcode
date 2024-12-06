package cz.glubo.adventofcode.y2024.day5

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.flowOf

/**
 * https://adventofcode.com/2024/day/5
 */
@Suppress("UNUSED")
class Day5Test :
    StringSpec({

        "day5 example part 1 matches" {
            y2024day5part1(
                flowOf(
                    "47|53",
                    "97|13",
                    "97|61",
                    "97|47",
                    "75|29",
                    "61|13",
                    "75|53",
                    "29|13",
                    "97|29",
                    "53|29",
                    "61|53",
                    "97|53",
                    "61|29",
                    "47|13",
                    "75|47",
                    "97|75",
                    "47|61",
                    "75|61",
                    "47|29",
                    "75|13",
                    "53|13",
                    "",
                    "75,47,61,53,29",
                    "97,61,53,29,13",
                    "75,29,13",
                    "75,97,47,61,53",
                    "61,13,29",
                    "97,13,75,29,47",
                ),
            ) shouldBe 143
        }

        "day5 example part 2 matches" {
            y2024day5part2(
                flowOf(
                    "47|53",
                    "97|13",
                    "97|61",
                    "97|47",
                    "75|29",
                    "61|13",
                    "75|53",
                    "29|13",
                    "97|29",
                    "53|29",
                    "61|53",
                    "97|53",
                    "61|29",
                    "47|13",
                    "75|47",
                    "97|75",
                    "47|61",
                    "75|61",
                    "47|29",
                    "75|13",
                    "53|13",
                    "",
                    "75,47,61,53,29",
                    "97,61,53,29,13",
                    "75,29,13",
                    "75,97,47,61,53",
                    "61,13,29",
                    "97,13,75,29,47",
                ),
            ) shouldBe 123
        }
    })
