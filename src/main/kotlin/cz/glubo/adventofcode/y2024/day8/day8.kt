package cz.glubo.adventofcode.y2024.day8

import cz.glubo.adventofcode.utils.IVec2
import cz.glubo.adventofcode.utils.input.Input
import org.slf4j.LoggerFactory

val logger = LoggerFactory.getLogger("y2024d87")

suspend fun y2024day8part1(input: Input): Long {
    val grid =
        input.grid {
            it
        }

    val antennaMap = mutableMapOf<Char, MutableList<IVec2>>()

    grid.allIVec2().forEach { pos ->
        val c = grid[pos]
        if (c == null || c == '.') return@forEach

        antennaMap.compute(c) { a, b ->
            val ret = b ?: mutableListOf()
            ret.add(pos)
            ret
        }
    }

    val antinodes = mutableListOf<IVec2>()
    antennaMap.toList().forEach { (char, positions) ->
        positions.indices.forEach { i ->
            (i + 1..<positions.size).forEach { j ->
                var a = positions[i]
                var b = positions[j]
                val diff = a - b

                if (!grid.outside(a + diff)) antinodes.add(a + diff)
                if (!grid.outside(b - diff)) antinodes.add(b - diff)
            }
        }
    }

    return antinodes
        .map { it.x + it.y * grid.width }
        .toSet()
        .size
        .toLong()
}

suspend fun y2024day8part2(input: Input): Long {
    val grid = input.grid { it }

    val antennaMap = mutableMapOf<Char, MutableList<IVec2>>()
    grid.allIVec2().forEach { pos ->
        val c = grid[pos]
        if (c == null || c == '.') return@forEach

        antennaMap.compute(c) { a, b ->
            val ret = b ?: mutableListOf()
            ret.add(pos)
            ret
        }
    }

    val antinodes = mutableListOf<IVec2>()
    antennaMap.toList().forEach { (char, positions) ->
        positions.indices.forEach { i ->
            (i + 1..<positions.size).forEach { j ->
                var a = positions[i]
                var b = positions[j]
                val diff = a - b

                var pos = a
                while (!grid.outside(pos)) {
                    antinodes.add(pos)
                    pos += diff
                }

                pos = a - diff
                while (!grid.outside(pos)) {
                    antinodes.add(pos)
                    pos -= diff
                }
            }
        }
    }

    return antinodes
        .map { it.x + it.y * grid.width }
        .toSet()
        .size
        .toLong()
}
