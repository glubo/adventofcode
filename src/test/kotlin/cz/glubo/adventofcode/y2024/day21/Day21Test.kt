package cz.glubo.adventofcode.y2024.day21

import cz.glubo.adventofcode.utils.input.TestInput
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe

/**
 * https://adventofcode.com/2024/day/21
 */
class Day21Test :
    StringSpec({

        "Numeric Keyboard example" {
            val output = buildLayers(0).getMovesForInput("029A")
            output shouldContainExactlyInAnyOrder
                listOf(
                    "<A^A>^^AvvvA",
                    "<A^A^>^AvvvA",
                    "<A^A^^>AvvvA",
                )
        }

        "Numeric Keyboard example a" {
            val output = buildLayers(0).getShortestLength("029A")
            output shouldBe "<A^A>^^AvvvA".length
        }

        "asdasd" {
        }

        "Layer Keyboard example" {
            val output = buildLayers(1).getShortestLength("029A")
            output shouldBe "v<<A>>^A<A>AvA<^AA>A<vAAA>^A".length
        }
        "Layer 2 Keyboard example" {
            val output = buildLayers(2).getShortestLength("029A")
            output shouldBe "<vA<AA>>^AvAA<^A>A<v<A>>^AvA^A<vA>^A<v<A>^A>AAvA^A<v<A>A>^AAAvA<^A>A".length
        }

        "day21 example part 1 matches" {
            y2024day21part1(
                TestInput(
                    """
                    029A
                    """.trimIndent(),
                ),
            ) shouldBe 68 * 29

            y2024day21part1(
                TestInput(
                    """
                    029A
                    980A
                    179A
                    456A
                    379A
                    """.trimIndent(),
                ),
            ) shouldBe 126384
        }
    })
