package cz.glubo.adventofcode.y2024.day15

import cz.glubo.adventofcode.utils.Direction
import cz.glubo.adventofcode.utils.input.Input
import io.klogging.noCoLogger

val logger = noCoLogger({}.javaClass.toString())

suspend fun y2024day15part1(input: Input): Long {
    logger.info("year 2024 day 15 part 1")
    val (grid, commandLines) = parseGridAndCommands(input)

    val world =
        World1(
            grid,
        )

    commandLines.forEach { line ->
        line.forEach { commandChar ->
            val direction =
                Direction.fromCommandChar(commandChar)
            world.move(grid, direction)
        }
    }

    return world.countGPS()
}

suspend fun y2024day15part2(input: Input): Long {
    logger.info("year 2024 day 15 part 2")

    val (grid, commandLines) = parseGridAndCommands(input)

    val world = World2.fromGrid(grid)

    commandLines.forEach { line ->
        line.forEach { commandChar ->
            val direction =
                Direction.fromCommandChar(commandChar)
            world.move(direction)
            world.debug()
        }
    }
    return world.countGPS()
}
