package cz.glubo.adventofcode.y2024.dayN

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList

/**
 * https://adventofcode.com/2024/day/N
 */
@Suppress("UNUSED")
class DayNTest : StringSpec({

    "dayN example part 1 matches" {
        y2024dayNpart1(
            flowOf(
                "pqr3stu8vwx",
                "a1b2c3d4e5f",
                "treb7uchet",
            )
        ) shouldBe 0
    }

    "dayN example part 2 matches" {
        y2024dayNpart2(
            flowOf(
                "pqr3stu8vwx",
                "a1b2c3d4e5f",
                "treb7uchet",
            )
        ) shouldBe 0
    }

})
