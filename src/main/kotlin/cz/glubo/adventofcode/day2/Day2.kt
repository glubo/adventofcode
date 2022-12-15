package cz.glubo.adventofcode.day2

import cz.glubo.adventofcode.day2.Hand.Paper
import cz.glubo.adventofcode.day2.Hand.Rock
import cz.glubo.adventofcode.day2.Hand.Scissor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.runningFold
import java.lang.RuntimeException

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

    private fun scoreForResult(round: Round): Int = when {
        round.opponentMove == round.myMove -> 3
        beatsMap[round.myMove] == round.opponentMove -> 6
        else -> 0
    }
}

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

private val roundRegex = """(?<opponentMove>\S+)\s(?<myMove>\S+)""".toRegex()
private fun handBy(value: String, selector: (Hand) -> String) = Hand.values().first { hand ->
    value == selector(hand)
}

fun Flow<String>.parseRounds(): Flow<Round> = map { line ->
    val match = roundRegex.matchEntire(line) ?: throw RoundParseError()
    Round(
        handBy(match.groups["opponentMove"]!!.value) { it.opponentRepresentation },
        handBy(match.groups["myMove"]!!.value) { it.myRepresentation },
    )
}


suspend fun Flow<String>.day2(): Int = this.parseRounds()
    .runningFold(Game.unitGame()) {accumulator, value ->
        accumulator.addRound(value)
    }.last()
    .score

class RoundParseError : RuntimeException("There was a problem parsing a round")
