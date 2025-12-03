package cz.glubo.adventofcode.y2025.dayN

import cz.glubo.adventofcode.utils.input.TestInput
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

/**
 * https://adventofcode.com/2025/day/N
 */
class DayNTest :
    StringSpec({

        "dayN example part 1 matches" {
            y2025dayNpart1(
                TestInput(
                    """
                    """.trimIndent(),
                ),
            ) shouldBe 0
        }

        "dayN example part 2 matches" {
            y2025dayNpart2(
                TestInput(
                    """
                    """.trimIndent(),
                ),
            ) shouldBe 0
        }
    })
