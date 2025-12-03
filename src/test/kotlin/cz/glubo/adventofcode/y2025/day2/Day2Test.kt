package cz.glubo.adventofcode.y2025.day2

import cz.glubo.adventofcode.utils.input.TestInput
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

/**
 * https://adventofcode.com/2025/day/2
 */
class Day2Test :
    StringSpec({

        "day2 example part 1 matches" {
            y2025day2part1(
                TestInput(
                    """
                    11-22,95-115,998-1012,1188511880-1188511890,222220-222224,
                    1698522-1698528,446443-446449,38593856-38593862,565653-565659,
                    824824821-824824827,2121212118-2121212124
                    """.trimIndent(),
                ),
            ) shouldBe 1227775554L
        }

        "isSymmetric" {
            isSymmetricId("11") shouldBe true
            isSymmetricId("1012") shouldBe false
            isSymmetricId("123123") shouldBe true
            isSymmetricId("2323") shouldBe true
        }

        "isRepeated" {
            isRepeatedId("11") shouldBe true
            isRepeatedId("1012") shouldBe false
            isRepeatedId("123123") shouldBe true
            isRepeatedId("2323") shouldBe true
            isRepeatedId("232323") shouldBe true
            isRepeatedId("232423") shouldBe false
            isRepeatedId("999") shouldBe true
        }

        "day2 example part 2 partial" {
            y2025day2part2(
                TestInput(
                    """
                    2121212118-2121212124
                    """.trimIndent(),
                ),
            ) shouldBe 2121212121
            y2025day2part2(
                TestInput(
                    """
                    824824821-824824827
                    """.trimIndent(),
                ),
            ) shouldBe 824824824
        }
        "day2 example part 2 matches" {
            y2025day2part2(
                TestInput(
                    """
                    11-22,95-115,998-1012,1188511880-1188511890,222220-222224,
                    1698522-1698528,446443-446449,38593856-38593862,565653-565659,
                    824824821-824824827,2121212118-2121212124
                    """.trimIndent(),
                ),
            ) shouldBe 4174379265
        }
    })
