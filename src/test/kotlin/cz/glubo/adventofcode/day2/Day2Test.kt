package cz.glubo.adventofcode.day2

import cz.glubo.adventofcode.day2.Color.BLUE
import cz.glubo.adventofcode.day2.Color.GREEN
import cz.glubo.adventofcode.day2.Color.RED
import io.kotest.core.spec.style.FreeSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.flowOf

/**
 * https://adventofcode.com/2023/day/2
 */
@Suppress("UNUSED")
class Day2Test : FreeSpec({
    "We can parse game id" {
        getGameId(
            "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green",
        ) shouldBe 1
    }

    "We can parse game id from all input" - {
        withData(
            "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green" to 1,
            "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue" to 2,
            "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red" to 3,
            "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red" to 4,
            "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green" to 5,
        ) {
            getGameId(
                it.first,
            ) shouldBe it.second
        }
    }

    "We can parse max color counts  all input" - {
        withData(
            "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green" to mapOf(BLUE to 6, RED to 4, GREEN to 2),
            "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue" to mapOf(BLUE to 4, RED to 1, GREEN to 3),
            "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red" to mapOf(BLUE to 6, RED to 20, GREEN to 13),
            "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red" to mapOf(BLUE to 15, RED to 14, GREEN to 3),
            "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green" to mapOf(BLUE to 2, RED to 6, GREEN to 3),
        ) {
            getMaxColorCounts(
                it.first,
            ) shouldBe it.second
        }
    }

    "Day2p1 example works" {
        flowOf(
            "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green",
            "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue",
            "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red",
            "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red",
            "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green",
        ).day2part1() shouldBe 8
    }

    "Day2p2 example works" {
        flowOf(
            "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green",
            "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue",
            "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red",
            "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red",
            "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green",
        ).day2part2() shouldBe 2286
    }
})
