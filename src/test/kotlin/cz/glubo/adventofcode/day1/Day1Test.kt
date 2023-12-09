package cz.glubo.adventofcode.day1

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList

/**
 * https://adventofcode.com/2023/day/1
 */
@Suppress("UNUSED")
class Day1Test : StringSpec({
    "We can parse simple example" {
        parseCalibrationInput(
            flowOf(
                "x1a2b3d",
            ),
        ).first() shouldBe 13
    }

    "We can parse whole example" {
        parseCalibrationInput(
            flowOf(
                "1abc2",
                "pqr3stu8vwx",
                "a1b2c3d4e5f",
                "treb7uchet",
            ),
        ).toList() shouldBe
            listOf(
                12,
                38,
                15,
                77,
            )
    }

    "Example matches expected value" {
        flowOf(
            "1abc2",
            "pqr3stu8vwx",
            "a1b2c3d4e5f",
            "treb7uchet",
        ).day1part1() shouldBe 142
    }

    "Simple normalisation example" {
        parseCalibrationInput(
            normaliseCalibrationInput(
                flowOf(
                    "7pqrstsixteen",
                ),
            ),
        ).first() shouldBe 76
    }

    "All normalisation examples" {
        parseCalibrationInput(
            normaliseCalibrationInput(
                flowOf(
                    "two1nine",
                    "eightwothree",
                    "abcone2threexyz",
                    "xtwone3four",
                    "4nineeightseven2",
                    "zoneight234",
                    "7pqrstsixteen",
                ),
            ),
        ).toList() shouldBe
            listOf(
                29, 83, 13, 24, 42, 14, 76,
            )
    }

    "complete day1p2 example" {
        flowOf(
            "two1nine",
            "eightwothree",
            "abcone2threexyz",
            "xtwone3four",
            "4nineeightseven2",
            "zoneight234",
            "7pqrstsixteen",
        ).day1part2() shouldBe 281
    }
})
