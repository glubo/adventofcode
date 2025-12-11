package cz.glubo.adventofcode.y2025.day10

import cz.glubo.adventofcode.utils.input.TestInput
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

/**
 * https://adventofcode.com/2025/day/10
 */
class Day10Test :
    StringSpec({

        "day10 example part 1 matches" {
            y2025day10part1(
                TestInput(
                    """
                    [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
                    [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
                    [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
                    """.trimIndent(),
                ),
            ) shouldBe 7
        }

        "state tests" {
            var a = State()
            var b = State()
            a.isOver(b) shouldBe false
            a = a.increment(4)
            a.isOver(b) shouldBe true
            b = b.increment(4)
            b = b.increment(4)
            logger.info { "$a $b" }
            a.isOver(b) shouldBe false
        }

        "day10 example part 2 matches" {
            y2025day10part2(
                TestInput(
                    """
                    [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
                    [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
                    [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
                    """.trimIndent(),
                ),
            ) shouldBe 33
        }
    })
