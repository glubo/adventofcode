package cz.glubo.adventofcode.day7

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList

val cards =
    listOf(
        '2',
        '3',
        '4',
        '5',
        '6',
        '7',
        '8',
        '9',
        'T',
        'J',
        'Q',
        'K',
        'A',
    )

val cardValue =
    cards.mapIndexed { index, s -> s to index.toLong() }
        .toMap()

val cards2 =
    listOf(
        'J',
        '2',
        '3',
        '4',
        '5',
        '6',
        '7',
        '8',
        '9',
        'T',
        'Q',
        'K',
        'A',
    )

val cardValue2 =
    cards2.mapIndexed { index, s -> s to index.toLong() }
        .toMap()

val combinationMultiplier = 24883200L

enum class Combination(
    val value: Long,
) {
    FIVE_OF_KIND(7L),
    FOUR_OF_KIND(6L),
    FULL_HOUSE(5L),
    THREE_OF_KIND(4L),
    TWO_PAIR(3L),
    TWO_OF_KIND(2L),
    HIGH_CARD(1L),
}

fun String.toCombination(): Combination {
    val groups =
        this.groupBy { it }
            .toList()
            .sortedByDescending { it.second.size }
    val big = groups[0].second.size
    val small = groups.getOrNull(1)?.second?.size ?: 0
    return when {
        big == 5 -> Combination.FIVE_OF_KIND
        big == 4 -> Combination.FOUR_OF_KIND
        big == 3 && small == 2 -> Combination.FULL_HOUSE
        big == 3 -> Combination.THREE_OF_KIND
        big == 2 && small == 2 -> Combination.TWO_PAIR
        big == 2 -> Combination.TWO_OF_KIND
        else -> Combination.HIGH_CARD
    }
}

fun String.toCombination2(): Combination {
    val groups =
        this.groupBy { it }
            .toList()
            .sortedByDescending { it.second.size }
    val jokerCount = this.count { it == 'J' }
    val big = groups[0].second.size
    val bigJoker = groups[0].first == 'J'
    val small = groups.getOrNull(1)?.second?.size ?: 0
    return when {
        big == 5 -> Combination.FIVE_OF_KIND
        !bigJoker && (big + jokerCount == 5) -> Combination.FIVE_OF_KIND
        bigJoker && (small + jokerCount == 5) -> Combination.FIVE_OF_KIND
        big == 4 -> Combination.FOUR_OF_KIND
        !bigJoker && (big + jokerCount == 4) -> Combination.FOUR_OF_KIND
        bigJoker && (small + jokerCount == 4) -> Combination.FOUR_OF_KIND
        big == 3 && small == 2 -> Combination.FULL_HOUSE
        !bigJoker && (big + jokerCount == 3 && small == 2) -> Combination.FULL_HOUSE
        big == 3 -> Combination.THREE_OF_KIND
        !bigJoker && (big + jokerCount == 3) -> Combination.THREE_OF_KIND
        bigJoker && (small + jokerCount == 3) -> Combination.THREE_OF_KIND
        big == 2 && small == 2 -> Combination.TWO_PAIR
        big == 2 -> Combination.TWO_OF_KIND
        !bigJoker && (big + jokerCount == 2) -> Combination.TWO_OF_KIND
        bigJoker && (small + jokerCount == 2) -> Combination.TWO_OF_KIND
        else -> Combination.HIGH_CARD
    }
}

fun String.toCardValue(): Long {
    var m: Long = 1
    var sum: Long = 0L
    this.reversed().forEach {
        sum += m * cardValue[it]!!
        m *= (cards.size + 1)
    }
    return sum
}

fun String.toCardValue2(): Long {
    var m: Long = 1
    var sum: Long = 0L
    this.reversed().forEach {
        sum += m * cardValue2[it]!!
        m *= (cards.size + 1)
    }
    return sum
}

suspend fun Flow<String>.day7part1(): Int {
    val lines = this.toList()

    data class Hand(
        val cards: String,
        val bid: Long,
        val combination: Combination,
    )

    val hands =
        lines.map { line ->
            val parts = line.split(" ")
            Hand(
                parts[0],
                parts[1].toLong(),
                parts[0].toCombination(),
            )
        }.sortedBy {
            val l = it.combination.value * combinationMultiplier + it.cards.toCardValue()
            println("$it $l")
            l
        }

    return hands.mapIndexed { index, hand ->
        (index + 1) * hand.bid
    }.sum().toInt()
}

suspend fun Flow<String>.day7part2(): Int {
    val lines = this.toList()

    data class Hand(
        val cards: String,
        val bid: Long,
        val combination: Combination,
    )

    val hands =
        lines.map { line ->
            val parts = line.split(" ")
            Hand(
                parts[0],
                parts[1].toLong(),
                parts[0].toCombination2(),
            )
        }.sortedBy {
            val l = it.combination.value * combinationMultiplier + it.cards.toCardValue2()
            println("$it $l")
            l
        }

    return hands.mapIndexed { index, hand ->
        (index + 1) * hand.bid
    }.sum().toInt()
}
