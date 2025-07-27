package cz.glubo.adventofcode.y2024.day17

import cz.glubo.adventofcode.utils.input.TestInput
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

/**
 * https://adventofcode.com/2024/day/17
 */
class Day17Test :
    StringSpec({

        "a" {
            val program =
                listOf(
                    OPCODE.BST to 6,
                )
            val state =
                State(
                    0.toLong(),
                    0.toLong(),
                    9.toLong(),
                    0,
                    "",
                )
            val nextState = advance(state, program)
            nextState shouldNotBe false
            state.B shouldBe 1.toLong()
        }

        "a2" {
            val program = "1,7".parseProgram()

            val state =
                State(
                    0.toLong(),
                    29.toLong(),
                    0.toLong(),
                    0,
                    "",
                )
            val nextState = advance(state, program)
            nextState shouldNotBe false
            state.B shouldBe 26.toLong()
        }
        "a3" {
            val program = "4,0".parseProgram()

            val state =
                State(
                    0.toLong(),
                    2024.toLong(),
                    43690.toLong(),
                    0,
                    "",
                )
            val nextState = advance(state, program)
            nextState shouldNotBe false
            state.B shouldBe 44354.toLong()
        }

        "day17 example 2 part 1 matches" {
            y2024day17part1(
                TestInput(
                    """
                    Register A: 10
                    Register B: 0
                    Register C: 0

                    Program: 5,0,5,1,5,4
                    """.trimIndent(),
                ),
            ) shouldBe "0,1,2"
        }

        "day17 example 3 part 1 matches" {
            y2024day17part1(
                TestInput(
                    """
                    Register A: 2024
                    Register B: 0
                    Register C: 0

                    Program: 0,1,5,4,3,0
                    """.trimIndent(),
                ),
            ) shouldBe "4,2,5,6,7,7,7,7,3,1,0"
        }

        "day17 example part 1 matches" {
            y2024day17part1(
                TestInput(
                    """
                    Register A: 729
                    Register B: 0
                    Register C: 0

                    Program: 0,1,5,4,3,0
                    """.trimIndent(),
                ),
            ) shouldBe "4,6,3,5,6,3,5,2,1,0"
        }

        "day17 example part 2 matches" {
            y2024day17part2(
                TestInput(
                    """
                    Register A: 2024
                    Register B: 0
                    Register C: 0

                    Program: 0,3,5,4,3,0
                    """.trimIndent(),
                ),
            ) shouldBe 117440.toLong()
        }
    })
