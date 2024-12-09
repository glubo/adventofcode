package cz.glubo.adventofcode.y2024.day9

import cz.glubo.adventofcode.utils.input.Input
import kotlinx.coroutines.flow.first

suspend fun y2024day9part1(input: Input): Long {
    val line = input.lineFlow().first()

    val drive = mutableListOf<Int>()
    populateDriveFromLinePart1(line, drive)

    defragPart1(drive)

    return part1Checksum(drive)
}

private fun defragPart1(drive: MutableList<Int>) {
    var pos = 0
    do {
        if (drive[pos] >= 0) {
            pos++
            continue
        }
        var tail = removeUntilNonEmptyTail(drive, pos)

        if (tail != null) {
            drive[pos] = tail
        }
        pos++
    } while (drive.size > pos)
}

private fun populateDriveFromLinePart1(
    line: String,
    drive: MutableList<Int>,
) = line.forEachIndexed { index, c ->
    val length = c.digitToInt()
    if (index % 2 == 1) {
        repeat(length) { _ ->
            drive.add(-1)
        }
    } else {
        repeat(length) { _ ->
            drive.add(index / 2)
        }
    }
}

fun part1Checksum(drive: List<Int>): Long =
    drive.foldIndexed(0L) { index: Int, acc: Long, value: Int ->
        acc + index * value
    }

fun removeUntilNonEmptyTail(
    drive: MutableList<Int>,
    minPos: Int,
): Int? {
    var tail: Int
    do {
        tail = drive.removeLast()
    } while (tail < 0 && drive.size > minPos)
    return if (tail < 0) null else tail
}

suspend fun y2024day9part2(input: Input): Long {
    val line = input.lineFlow().first()

    val drive = mutableListOf<Cell>()
    populateDriveFromLinePart2(line, drive)

    defragPart2(drive)

    var checksum = calculateChecksumPart2(drive)

    return checksum
}

private fun defragPart2(drive: MutableList<Cell>) {
    var pos = drive.size - 1
    do {
        val currentCell = drive[pos]
        if (!currentCell.isEmpty()) {
            var spacePos = 0
            do {
                spacePos++
                val spaceCell = drive[spacePos]
                if (spaceCell.isEmpty() && spaceCell.length >= currentCell.length) {
                    transferCellToSpace(spaceCell, currentCell, drive, pos, spacePos)
                    break
                }
            } while (spacePos < pos)
        }
        pos--
    } while (pos > 0)
}

private fun transferCellToSpace(
    spaceCell: Cell,
    currentCell: Cell,
    drive: MutableList<Cell>,
    originalPos: Int,
    spacePos: Int,
) {
    val remainingSpace = spaceCell.length - currentCell.length
    drive.removeAt(originalPos)
    drive.add(originalPos, Cell(currentCell.length, -1))
    drive.removeAt(spacePos)
    if (remainingSpace > 0) {
        drive.add(spacePos, Cell(remainingSpace, -1))
    }
    drive.add(spacePos, currentCell)
}

private fun calculateChecksumPart2(drive: MutableList<Cell>): Long {
    var checksum = 0L
    var position = 0L
    drive.forEach { cell ->
        repeat(cell.length) { _ ->
            if (!cell.isEmpty()) {
                checksum += position * cell.value
            }
            position++
        }
    }
    return checksum
}

private fun populateDriveFromLinePart2(
    line: String,
    drive: MutableList<Cell>,
) {
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
}

data class Cell(
    val length: Int,
    val value: Int,
) {
    fun isEmpty() = value < 0
}
