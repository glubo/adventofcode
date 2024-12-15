package cz.glubo.adventofcode.y2024.day14

import cz.glubo.adventofcode.utils.IVec2
import cz.glubo.adventofcode.utils.input.Input
import io.klogging.noCoLogger
import kotlinx.coroutines.flow.toList

val logger = noCoLogger({}.javaClass.toString())

data class Robot(
    var pos: IVec2,
    val vel: IVec2,
)

fun List<Robot>.debug(area: IVec2) {
    (0..<area.y).forEach { y ->
        val line =
            (0..<area.x)
                .map { x ->
                    val pos = IVec2(x, y)
                    val count = this.count { it.pos == pos }
                    when {
                        count > 9 -> '#'
                        count > 0 -> count.toString().last()
                        else -> '.'
                    }
                }.joinToString("")
        logger.debug(line)
    }
    logger.debug { "------" }
}

suspend fun y2024day14part1(
    input: Input,
    area: IVec2,
): Long {
    logger.info("year 2024 day 14 part 1")
    val robots =
        input
            .lineFlow()
            .toList()
            .map { line ->
                val (ps, vs) = line.split(" ")

                val (px, py) =
                    ps
                        .removePrefix("p=")
                        .split(",")
                        .map { it.toInt() }
                val (vx, vy) =
                    vs
                        .removePrefix("v=")
                        .split(",")
                        .map { it.toInt() }

                Robot(IVec2(px, py), IVec2(vx, vy))
            }

    robots.debug(area)
    repeat(100) { _ ->
        robots.forEach { r ->
            val tmp = r.pos + r.vel

            r.pos =
                IVec2(
                    (tmp.x + area.x) % area.x,
                    (tmp.y + area.y) % area.y,
                )
        }
//        robots.debug(area)
    }
    robots.debug(area)

    val q = IVec2(area.x / 2, area.y / 2)
    val o = IVec2(0, 0)
    val quads =
        listOf(
            o to q,
            area - q to area,
            IVec2(0, area.y - q.y) to IVec2(q.x, area.y),
            IVec2(area.x - q.x, 0) to IVec2(area.x, q.y),
        )

    return quads
        .fold(1L) { acc, (from, to) ->
            val count =
                robots.count { r ->
                    r.pos.x in (from.x..<to.x) &&
                        r.pos.y in (from.y..<to.y)
                }

            logger.debug { "quad $from -> $to: $count" }
            acc * count
        }.toLong()
}

suspend fun y2024day14part2(
    input: Input,
    area: IVec2,
): Long {
    logger.info("year 2024 day 14 part 2")
    val robots =
        input
            .lineFlow()
            .toList()
            .map { line ->
                val (ps, vs) = line.split(" ")

                val (px, py) =
                    ps
                        .removePrefix("p=")
                        .split(",")
                        .map { it.toInt() }
                val (vx, vy) =
                    vs
                        .removePrefix("v=")
                        .split(",")
                        .map { it.toInt() }

                Robot(IVec2(px, py), IVec2(vx, vy))
            }

    repeat(10000) { i ->
        robots.forEach { r ->
            val tmp = r.pos + r.vel

            r.pos =
                IVec2(
                    (tmp.x + area.x) % area.x,
                    (tmp.y + area.y) % area.y,
                )
        }
        val map =
            robots
                .groupBy { it.pos }
                .mapValues { it.value.size }

        if (map.values.none { it > 1 }) {
            logger.debug { i }
            robots.debug(area)
        }
    }

    return 0
}
