package cz.glubo.adventofcode.utils.input

import cz.glubo.adventofcode.utils.Grid
import kotlinx.coroutines.flow.Flow

interface Input {
    fun lineFlow(): Flow<String>

    suspend fun <TileType> grid(tileMapper: (Char) -> TileType): Grid<TileType> {
        val tiles = mutableListOf<TileType>()
        var height = 0
        var width = 0
        lineFlow().collect { line ->
            width = line.length
            height++
            tiles.addAll(
                line.map {
                    tileMapper(it)
                },
            )
        }

        val tileGrid =
            Grid(
                width = width,
                height = height,
                fields = tiles,
            )
        return tileGrid
    }
}
