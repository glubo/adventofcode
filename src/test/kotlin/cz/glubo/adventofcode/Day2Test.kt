@file:OptIn(ExperimentalCoroutinesApi::class)

package cz.glubo.adventofcode

import cz.glubo.adventofcode.day2.Game
import cz.glubo.adventofcode.day2.Hand
import cz.glubo.adventofcode.day2.Hand.Paper
import cz.glubo.adventofcode.day2.Hand.Rock
import cz.glubo.adventofcode.day2.Hand.Scissor
import cz.glubo.adventofcode.day2.Round
import cz.glubo.adventofcode.day2.addRound
import cz.glubo.adventofcode.day2.day2
import cz.glubo.adventofcode.day2.parseRounds
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.assertEquals

/**
 * https://adventofcode.com/2022/day/2
 */
class Day2Test {
    @Test
    fun `We can add Round to a Game`() {
        val initialGame = Game.unitGame()
        val finalGame = initialGame.addRound(Round(Rock, Scissor))

        assertNotNull(finalGame)
    }

    @ParameterizedTest
    @MethodSource("singleRoundData")
    fun `Single round`(
        round: Round,
        expectedScore: Int,
    ) {
        val initialGame = Game.unitGame()
        val finalGame = initialGame.addRound(round)

        assertEquals(expectedScore, finalGame.score)
    }

    @Test
    fun `Two rounds`() {
        val initialGame = Game.unitGame()
        val finalGame = initialGame.addRound(Round(Rock, Rock))
            .addRound(Round(Scissor, Scissor))

        assertEquals(10, finalGame.score)
    }

    @ParameterizedTest
    @MethodSource("parsingData")
    fun `Round parsing`(
        inputLine: String,
        expectedRound: Round,
    ) = runTest {
        val parsedRounds = flowOf(inputLine)
            .parseRounds()
            .toCollection(mutableListOf())

        assertAll(
            { assertEquals(1, parsedRounds.size) },
            { assertEquals(expectedRound, parsedRounds.first()) },
        )
    }

    @Test
    fun `Day 2 example`()= runTest {
        val score = flowOf(
            "A Y",
            "B X",
            "C Z",
        ).day2()

        assertEquals(15, score)
    }

    companion object {
        @JvmStatic
        fun singleRoundData() = listOf(
            Arguments.of(Round(Rock, Rock), 4),
            Arguments.of(Round(Paper, Paper), 5),
            Arguments.of(Round(Scissor, Scissor), 6),

            Arguments.of(Round(Scissor, Rock), 7),
            Arguments.of(Round(Rock, Paper), 8),
            Arguments.of(Round(Paper, Scissor), 9),

            Arguments.of(Round(Paper, Rock), 1),
            Arguments.of(Round(Scissor, Paper), 2),
            Arguments.of(Round(Rock, Scissor), 3),
        )

        @JvmStatic
        fun parsingData() = listOf(
            Arguments.of("A X", Round(Rock, Rock)),
            Arguments.of("B Y", Round(Paper, Paper)),
            Arguments.of("C Z", Round(Scissor, Scissor)),

            Arguments.of("C X", Round(Scissor, Rock)),
            Arguments.of("A Y", Round(Rock, Paper)),
            Arguments.of("B Z", Round(Paper, Scissor)),

            Arguments.of("B X", Round(Paper, Rock)),
            Arguments.of("C Y", Round(Scissor, Paper)),
            Arguments.of("A Z", Round(Rock, Scissor)),
        )
    }

}