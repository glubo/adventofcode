package cz.glubo.adventofcode.y2024.day9

import cz.glubo.adventofcode.utils.input.Input
import kotlinx.coroutines.flow.first

suspend fun y2024day9part1(input: Input): Long {
    val line = input.lineFlow().first()

    val drive = mutableListOf<Int>()
    line.forEachIndexed { index, c ->
        val length = c.digitToInt()
        if (index % 2 == 1) {
            drive.addAll(
                (0..<length).map { -1 },
            )
        } else {
            drive.addAll(
                (0..<length).map { index / 2 },
            )
        }
    }

    var pos = 0
    do {
        if (drive[pos] >= 0) {
            pos++
        } else {
            var tail: Int
            do {
                tail = drive.removeLast()
            } while (tail < 0 && drive.size > pos)
            if (tail >= 0) {
                drive[pos] = tail
            }
            pos++
        }
    } while (drive.size > pos)

    var checksum = 0L
    drive.forEachIndexed { index, value ->
        checksum += index * value
    }

    return checksum
}

data class Cell(
    val length: Int,
    val value: Int,
) {
    fun isEmpty() = value < 0
}

suspend fun y2024day9part2(input: Input): Long {
    val line = input.lineFlow().first()

    val drive = mutableListOf<Cell>()
    line.forEachIndexed { index, c ->
        val length = c.digitToInt()
        if (index % 2 == 1) {
            drive.add(
                Cell(
                    length = length,
                    value = -1,
                ),
            )
        } else {
            drive.add(
                Cell(
                    length = length,
                    value = index / 2,
                ),
            )
        }
    }

    var pos = drive.size - 1
    do {
        val currentCell = drive[pos]
        if (currentCell.isEmpty()) {
            pos--
        } else {
            var spacePos = 0
            do {
                spacePos++
                val spaceCell = drive[spacePos]
                if (spaceCell.isEmpty() && spaceCell.length >= currentCell.length) {
                    val remainingSpace = spaceCell.length - currentCell.length
                    drive.removeAt(pos)
                    drive.add(pos, Cell(currentCell.length, -1))
                    drive.removeAt(spacePos)
                    if (remainingSpace > 0) {
                        drive.add(spacePos, Cell(remainingSpace, -1))
                    }
                    drive.add(spacePos, currentCell)
                    break
                }
            } while (spacePos < pos)
            pos--
        }
    } while (pos > 0)

    var checksum = 0L
    var position = 0L
    drive.forEach { cell ->
        (0..<cell.length).forEach { _ ->
            if (!cell.isEmpty()) {
                checksum += position * cell.value
            }
            position++
        }
    }

    return checksum
}
