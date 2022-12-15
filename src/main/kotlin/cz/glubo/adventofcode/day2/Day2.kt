package cz.glubo.adventofcode.day2

import cz.glubo.adventofcode.day2.Hand.Paper
import cz.glubo.adventofcode.day2.Hand.Rock
import cz.glubo.adventofcode.day2.Hand.Scissor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.runningFold

enum class Hand(
    val value: Int,
    val opponentRepresentation: String,
    val myRepresentation: String,
) {
    Rock(1, "A", "X"),
    Paper(2, "B", "Y"),
    Scissor(3, "C", "Z"),
}

private object Referee {
    /**
     * key beats value
     */
    val beatsMap = mapOf(
        Paper to Rock,
        Rock to Scissor,
        Scissor to Paper,
    )

    fun score(round: Round): Int {
        return scoreForResult(round) + round.myMove.value
    }

    fun scoreExpectation(roundExpectation: RoundExpectation): Int {
        val round = roundForExpectation(roundExpectation)
        return score(round)
    }

    private fun roundForExpectation(roundExpectation: RoundExpectation): Round {
        val opponentMove = roundExpectation.oponentMove

        return when (roundExpectation.result) {
            Result.Draw -> Round(opponentMove, opponentMove)
            Result.Lose -> Round(opponentMove, losingHand(opponentMove))
            Result.Win -> Round(opponentMove, winningHand(opponentMove))
        }
    }

    private fun winningHand(opponentMove: Hand) = beatsMap.firstNotNullOf {
        if (it.value == opponentMove) it.key else null
    }

    private fun losingHand(opponentMove: Hand) = beatsMap[opponentMove]!!

    private fun scoreForResult(round: Round): Int = when {
        round.opponentMove == round.myMove -> 3
        beatsMap[round.myMove] == round.opponentMove -> 6
        else -> 0
    }
}

enum class Result(
    val representation: String,
) {
    Lose("X"),
    Draw("Y"),
    Win("Z"),
}

data class RoundExpectation(
    val oponentMove: Hand,
    val result: Result,
)

data class Round(
    val opponentMove: Hand,
    val myMove: Hand,
)

data class Game(
    val score: Int,
) {
    companion object {
        fun unitGame() = Game(0)
    }
}

fun Game.addRound(round: Round) = Game(
    score = this.score + Referee.score(round)
)

fun Game.addRoundExpectation(roundExpectation: RoundExpectation) = Game(
    this.score + Referee.scoreExpectation(roundExpectation)
)

private val roundRegex = """(?<opponentMove>\S+)\s(?<myMove>\S+)""".toRegex()

private fun handBy(value: String, selector: (Hand) -> String) = Hand.values().first { hand ->
    value == selector(hand)
}

private fun resultBy(value: String) = Result.values().first { result ->
    value == result.representation
}

fun Flow<String>.parseRounds(): Flow<Round> = map { line ->
    val match = roundRegex.matchEntire(line) ?: throw RoundParseError()
    Round(
        handBy(match.groups["opponentMove"]!!.value) { it.opponentRepresentation },
        handBy(match.groups["myMove"]!!.value) { it.myRepresentation },
    )
}

fun Flow<String>.parseRoundsExpectation(): Flow<RoundExpectation> = map { line ->
    val match = roundRegex.matchEntire(line) ?: throw RoundParseError()
    RoundExpectation(
        handBy(match.groups["opponentMove"]!!.value) { it.opponentRepresentation },
        resultBy(match.groups["myMove"]!!.value),
    )
}

suspend fun Flow<String>.day2part1(): Int = this.parseRounds()
    .runningFold(Game.unitGame()) { accumulator, value ->
        accumulator.addRound(value)
    }.last()
    .score

suspend fun Flow<String>.day2part2(): Int = this.parseRoundsExpectation()
    .runningFold(Game.unitGame()) { accumulator, value ->
        accumulator.addRoundExpectation(value)
    }.last()
    .score

class RoundParseError : RuntimeException("There was a problem parsing a round")
