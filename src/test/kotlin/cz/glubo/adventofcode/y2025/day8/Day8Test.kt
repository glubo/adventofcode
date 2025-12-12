package cz.glubo.adventofcode.y2025.day8

import cz.glubo.adventofcode.utils.input.TestInput
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

/**
 * https://adventofcode.com/2025/day/8
 */
class Day8Test :
    StringSpec({

        "day8 example part 1 matches" {
            y2025day8part1(
                TestInput(
                    """
                    162,817,812
                    57,618,57
                    906,360,560
                    592,479,940
                    352,342,300
                    466,668,158
                    542,29,236
                    431,825,988
                    739,650,466
                    52,470,668
                    216,146,977
                    819,987,18
                    117,168,530
                    805,96,715
                    346,949,466
                    970,615,88
                    941,993,340
                    862,61,35
                    984,92,344
                    425,690,689
                    """.trimIndent(),
                ),
                10,
            ) shouldBe 40
        }

        "day8 example part 2 matches" {
            y2025day8part2(
                TestInput(
                    """
                    162,817,812
                    57,618,57
                    906,360,560
                    592,479,940
                    352,342,300
                    466,668,158
                    542,29,236
                    431,825,988
                    739,650,466
                    52,470,668
                    216,146,977
                    819,987,18
                    117,168,530
                    805,96,715
                    346,949,466
                    970,615,88
                    941,993,340
                    862,61,35
                    984,92,344
                    425,690,689
                    """.trimIndent(),
                ),
            ) shouldBe 25272
        }
    })
