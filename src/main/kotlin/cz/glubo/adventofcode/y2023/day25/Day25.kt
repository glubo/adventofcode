package cz.glubo.adventofcode.y2023.day25

import cz.glubo.adventofcode.utils.orMax
import io.klogging.noCoLogger
import kotlinx.coroutines.flow.Flow
import kotlin.math.min

private val logger = noCoLogger({}.javaClass.`package`.toString())

suspend fun Flow<String>.y2023day25part1(): Long {
    val (nodes, edges) = parseGraphData()

    val edgeUsage = mutableMapOf<Int, Int>()
    for (notUsed in (1..1000)) {
        val start = nodes.random()
        val end = nodes.filterNot { it == start }.random()
        logger.debug { "Finding path from $start to $end" }

        val path = findPath(edges, start, end)
        path.forEach {
            edgeUsage.compute(it) { _, sum -> (sum ?: 0) + 1 }
        }
        logger.debug { "Found path" + path.joinToString { edges[it].toString() } }
    }
    logger.debug { "usage map: ${edgeUsage.toSortedMap()}" }

    val toRemove =
        edgeUsage.entries.sortedByDescending { it.value }
            .take(3)
            .map { it.key }
    logger.debug {
        "Going to remove: " +
            toRemove.joinToString { "$it=>${edges[it]}" }
    }

    val reducedEdges =
        edges.filterIndexed { index, _ ->
            index !in toRemove
        }
    logger.debug { "original edges: $edges" }
    logger.debug { "reduced edges:  $reducedEdges" }

    val visited = visitNodes(reducedEdges)

    logger.debug { "nodes: $nodes" }
    logger.debug { "visited: $visited" }

    return visited.size.toLong() * (nodes.size - visited.size)
}

suspend fun Flow<String>.parseGraphData(): Pair<Set<String>, List<Edge>> {
    val nodes = mutableSetOf<String>()
    val edges = mutableListOf<Pair<String, String>>()

    this.collect { line ->
        val (node, edgeEnds) = line.split(": ")
        nodes += node
        val endPoints =
            edgeEnds
                .split(' ')
        nodes.addAll(endPoints)
        edges.addAll(
            endPoints.map { node to it },
        )
    }
    return Pair(
        nodes,
        edges.mapIndexed { i, e ->
            Edge(
                i,
                e.first,
                e.second,
            )
        },
    )
}

private fun visitNodes(edges: List<Edge>): List<String> {
    val firstEdge = edges.first()
    val visited = mutableListOf(firstEdge.first)
    val toExplore = mutableListOf(firstEdge to firstEdge.first)
    while (toExplore.isNotEmpty()) {
        val (edge, currentNode) = toExplore.removeFirst()

        val nextNode: String = edge.other(currentNode)
        if (nextNode !in visited) {
            visited.add(nextNode)
            toExplore.addAll(
                edges.filterCrossing(nextNode).map {
                    it to nextNode
                },
            )
        }
    }
    return visited
}

data class Edge(
    val index: Int,
    val first: String,
    val second: String,
) {
    fun other(it: String) =
        if (this.first == it) {
            this.second
        } else {
            this.first
        }
}

fun findPath(
    edges: List<Edge>,
    start: String,
    end: String,
): List<Int> {
    data class Explore(
        val tail: String,
        val pathTaken: List<Edge>,
    )

    val toExplore = mutableListOf<Explore>()
    toExplore.addAll(
        edges.filterCrossing(start)
            .map { edge ->
                Explore(edge.other(start), listOf(edge))
            },
    )
    val visitedNodes = mutableMapOf(start to 0)

    while (toExplore.isNotEmpty()) {
        val explore = toExplore.removeFirst()

        when {
            explore.tail == end -> {
                return explore.pathTaken.map { it.index }
            }

            visitedNodes[explore.tail].orMax() <= explore.pathTaken.size -> {}

            else -> {
                visitedNodes.compute(explore.tail) { _, c ->
                    min(explore.pathTaken.size, c.orMax())
                }
                edges.filterCrossing(explore.tail)
                    .filter { edge ->
                        edge !in explore.pathTaken
                    }.forEach { edge ->
                        val nextTail = edge.other(explore.tail)
                        val newPathTaken = explore.pathTaken + edge
                        toExplore.add(Explore(nextTail, newPathTaken))
                    }
            }
        }
    }
    throw RuntimeException("No Path found")
}

fun List<Edge>.filterCrossing(node: String) =
    this.filter {
        it.first == node || it.second == node
    }
