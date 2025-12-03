package cz.glubo.adventofcode.utils

import cz.glubo.adventofcode.utils.Direction.DOWN
import cz.glubo.adventofcode.utils.Direction.LEFT
import cz.glubo.adventofcode.utils.Direction.RIGHT
import cz.glubo.adventofcode.utils.Direction.UP
import io.klogging.noCoLogger
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

data class IVec2(
    val x: Int,
    val y: Int,
) {
    operator fun plus(other: IVec2) = IVec2(this.x + other.x, this.y + other.y)

    operator fun minus(other: IVec2) = IVec2(this.x - other.x, this.y - other.y)

    operator fun times(scale: Int) = IVec2(scale * this.x, scale * this.y)

    infix fun dot(other: IVec2) = this.x * other.x + this.y * other.y

    /**
     * Calculates the taxicab distance between this vector and the given vector.
     *
     * The taxicab distance is the sum of the absolute differences of the corresponding coordinates of the vectors.
     *
     * @param other The vector to calculate the taxicab distance to.
     * @return The taxicab distance between this vector and the given vector.
     */
    infix fun tcDistance(other: IVec2) = abs(this.x - other.x) + abs(this.y - other.y)
}

enum class FullDirection(
    val vector: IVec2,
    val char: Char,
) {
    UP(
        IVec2(0, -1),
        '↑',
    ),
    UP_LEFT(
        IVec2(-1, -1),
        '↖',
    ),
    UP_RIGHT(
        IVec2(1, -1),
        '↗',
    ),
    DOWN(
        IVec2(0, 1),
        '↓',
    ),
    DOWN_LEFT(
        IVec2(-1, 1),
        '↙',
    ),
    DOWN_RIGHT(
        IVec2(1, 1),
        '↘',
    ),
    LEFT(
        IVec2(-1, 0),
        '←',
    ),
    RIGHT(
        IVec2(1, 0),
        '→',
    ),
}

enum class Direction(
    val vector: IVec2,
    val char: Char,
) {
    UP(
        IVec2(0, -1),
        '↑',
    ),
    DOWN(
        IVec2(0, 1),
        '↓',
    ),
    LEFT(
        IVec2(-1, 0),
        '←',
    ),
    RIGHT(
        IVec2(1, 0),
        '→',
    ),
    ;

    fun opposite() =
        when (this) {
            UP -> DOWN
            DOWN -> UP
            LEFT -> RIGHT
            RIGHT -> LEFT
        }

    fun perpendicular() =
        when (this) {
            UP -> listOf(LEFT, RIGHT)
            DOWN -> listOf(LEFT, RIGHT)
            LEFT -> listOf(UP, DOWN)
            RIGHT -> listOf(UP, DOWN)
        }

    fun turnRight() =
        when (this) {
            UP -> RIGHT
            DOWN -> LEFT
            LEFT -> UP
            RIGHT -> DOWN
        }

    fun turnLeft() =
        when (this) {
            UP -> LEFT
            DOWN -> RIGHT
            LEFT -> DOWN
            RIGHT -> UP
        }

    fun toCommandChar() =
        when (this) {
            UP -> '^'
            DOWN -> 'v'
            LEFT -> '<'
            RIGHT -> '>'
        }

    companion object {
        fun fromCommandChar(commandChar: Char) =
            when (commandChar) {
                '<' -> LEFT
                '>' -> RIGHT
                '^' -> UP
                'v' -> DOWN
                else -> error("unexpected direction $commandChar")
            }
    }
}

enum class Orientation(
    val directions: List<Direction>,
) {
    HORIZONTAL(listOf(LEFT, RIGHT)),

    VERTICAL(listOf(UP, DOWN)),
    ;

    fun switch() =
        when (this) {
            HORIZONTAL -> VERTICAL
            VERTICAL -> HORIZONTAL
        }

    companion object {
        fun of(direction: Direction) =
            when (direction) {
                UP -> VERTICAL
                DOWN -> VERTICAL
                LEFT -> HORIZONTAL
                RIGHT -> HORIZONTAL
            }
    }
}

class GridWithFakeTile<T>(
    width: Int,
    height: Int,
    fields: MutableList<T>,
    val fakeTilePos: IVec2,
    val fakeTile: T,
) : Grid<T>(width, height, fields) {
    override fun get(at: IVec2): T? =
        when {
            fakeTilePos == at -> fakeTile
            else -> super.get(at)
        }
}

open class Grid<T>(
    val width: Int,
    val height: Int,
    val fields: MutableList<T>,
) {
    val topLeft = IVec2(0, 0)
    val bottomRight = IVec2(width - 1, height - 1)

    open operator fun get(at: IVec2) = if (outside(at)) null else fields[at.x + at.y * width]

    operator fun set(
        at: IVec2,
        it: T,
    ) {
        if (!outside(at)) {
            fields[at.x + at.y * width] = it
        }
    }

    fun allIVec2() =
        (0..<height).flatMap { y ->
            (0..<width).map { x ->
                IVec2(x, y)
            }
        }

    fun outside(it: IVec2) =
        when {
            it.x >= width -> true
            it.y >= height -> true
            it.x < 0 -> true
            it.y < 0 -> true
            else -> false
        }

    fun debug(map: (T, IVec2) -> Char) {
        noCoLogger(this.javaClass.toString()).debug {
            "Grid $width * $height\n" +
                allIVec2()
                    .chunked(width)
                    .map { line -> line.map { map(get(it)!!, it) }.joinToString("") }
                    .joinToString("\n")
        }
    }

    fun debug(map: (T) -> Char) {
        debug { it, _ -> map(it) }
    }
}

infix fun IntRange.rangeUnion(that: IntRange) =
    listOf(
        (min(this.start, that.start)..min(this.endInclusive, that.endInclusive)),
        (max(this.start, that.start)..max(this.endInclusive, that.endInclusive)),
    ).let { (lower, higher) ->
        when {
            lower.endInclusive + 1 >= higher.start -> listOf((lower.start..higher.endInclusive))
            else -> listOf(lower, higher)
        }
    }

fun List<IntRange>.rangeUnion() =
    this
        .sortedBy { it.first }
        .fold(listOf<IntRange>()) { acc, it ->
            val newTail =
                acc.lastOrNull()?.rangeUnion(it)
                    ?: listOf(it)
            acc.dropLast(1) + newTail
        }

fun IntRange.length() = (this.endInclusive - this.start + 1)

fun (MatchResult?).getGroup(group: String) = this!!.groups[group]!!.value

fun IntRange.split(boundary: Int): Pair<IntRange?, IntRange?> =
    when {
        boundary in (this.start + 1..this.endInclusive) -> {
            (this.start..<boundary) to (boundary..this.endInclusive)
        }

        boundary <= this.start -> {
            null to this
        }

        else -> {
            this to null
        }
    }

fun Int?.orMax() = this ?: Int.MAX_VALUE

fun <T> List<T>.isSame(other: List<T>) =
    (this.size == other.size) &&
        this.indices.all {
            this[it] == other[it]
        }

fun <E> Set<E>.allSubsets(): Sequence<Set<E>> =
    sequence {
        val original = this@allSubsets

        if (original.isEmpty()) return@sequence

        val first = original.first()
        yield(setOf(first))
        original
            .drop(1)
            .toSet()
            .allSubsets()
            .forEach {
                yield(it)
                yield(setOf(first) + it)
            }
    }

fun <E> Set<E>.allOrders(): Sequence<List<E>> =
    sequence {
        val list = this@allOrders.toList()
        if (list.isEmpty()) {
            yield(emptyList())
        } else {
            for (i in list.indices) {
                val otherSet =
                    list
                        .filterIndexed { it, _ -> it != i }
                        .toSet()
                otherSet.allOrders().forEach { item ->
                    yield(item + list[i])
                }
            }
        }
    }

fun Long.getBit(n: Int) = this.shr(n).and(1)

fun Long.bitDistance(other: Long): Int {
    var a = this
    var b = other
    var result = 0
    do {
        result += a.and(1).xor(b.and(1)).toInt()
        a = a.shr(1)
        b = b.shr(1)
    } while (a > 0 || b > 0)
    return result
}

fun pow10(n: Int): Long {
    var res = 1L
    repeat(n) {
        res *= 10
    }
    return res
}
