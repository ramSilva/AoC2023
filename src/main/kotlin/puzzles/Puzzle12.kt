package puzzles

import java.io.File

private val lines = File("input/puzzle12/input.txt").readLines()

fun puzzle12(): Int {
    val grid = mutableListOf<MutableList<Char>>()
    var startingPosition = Pair(0, 0)
    val successPath: List<Pair<Int, Int>>

    // List of paths. The current node on each path is the last entry in the list
    val visitQueue = mutableListOf<List<Pair<Int, Int>>>()

    val visitedPositions = mutableSetOf<Pair<Int, Int>>()

    lines.forEachIndexed { y, s ->
        grid.add(y, mutableListOf())
        s.forEachIndexed { x, c ->
            if (c == 'S') startingPosition = Pair(x, y)

            grid[y].add(x, c)
        }
    }

    visitQueue.add(listOf(startingPosition))

    while (true) {
        val currentPath = visitQueue.first()
        val currentNode = currentPath.last()
        visitQueue.removeFirst()

        if (grid[currentNode.second][currentNode.first] == 'E') {
            successPath = currentPath
            break
        }

        val currentHeight = when {
            grid[currentNode.second][currentNode.first] == 'E' -> 'z'
            grid[currentNode.second][currentNode.first] == 'S' -> 'a'
            else -> grid[currentNode.second][currentNode.first]
        }

        val adjacentNodes = listOf(
            Pair(currentNode.first - 1, currentNode.second),
            Pair(currentNode.first + 1, currentNode.second),
            Pair(currentNode.first, currentNode.second - 1),
            Pair(currentNode.first, currentNode.second + 1)
        )

        adjacentNodes.forEach { adjacent ->
            if (adjacent.first >= 0
                && adjacent.first < grid[0].size
                && adjacent.second >= 0
                && adjacent.second < grid.size
                && !visitedPositions.contains(adjacent)
            ) {
                val adjacentHeight = when {
                    grid[adjacent.second][adjacent.first] == 'E' -> 'z'
                    grid[adjacent.second][adjacent.first] == 'S' -> 'a'
                    else -> grid[adjacent.second][adjacent.first]
                }

                if (currentHeight - adjacentHeight >= -1) {
                    visitQueue.add(currentPath.plus(adjacent))
                    visitedPositions.add(adjacent)
                }
            }
        }
    }

    return successPath.size - 1 // the first node doesn't count
}


fun puzzle12dot1(): Int {
    val grid = mutableListOf<MutableList<Char>>()
    val startingPositions = mutableListOf<Pair<Int, Int>>()
    val successPaths = mutableListOf<List<Pair<Int, Int>>>()

    // List of paths. The current node on each path is the last entry in the list
    val visitQueue = mutableListOf<List<Pair<Int, Int>>>()

    val visitedPositions = mutableSetOf<Pair<Int, Int>>()

    lines.forEachIndexed { y, s ->
        grid.add(y, mutableListOf())
        s.forEachIndexed { x, c ->
            if (c == 'S' || c == 'a') startingPositions.add(Pair(x, y))

            grid[y].add(x, c)
        }
    }

    startingPositions.forEach {
        visitQueue.clear()
        visitedPositions.clear()

        visitQueue.add(listOf(it))

        while (true) {
            if (visitQueue.isEmpty()) break

            val currentPath = visitQueue.first()
            val currentNode = currentPath.last()
            visitQueue.removeFirst()

            if (grid[currentNode.second][currentNode.first] == 'E') {
                successPaths.add(currentPath)
                break
            }

            val currentHeight = when {
                grid[currentNode.second][currentNode.first] == 'E' -> 'z'
                grid[currentNode.second][currentNode.first] == 'S' -> 'a'
                else -> grid[currentNode.second][currentNode.first]
            }

            val adjacentNodes = listOf(
                Pair(currentNode.first - 1, currentNode.second),
                Pair(currentNode.first + 1, currentNode.second),
                Pair(currentNode.first, currentNode.second - 1),
                Pair(currentNode.first, currentNode.second + 1)
            )

            adjacentNodes.forEach { adjacent ->
                if (adjacent.first >= 0
                    && adjacent.first < grid[0].size
                    && adjacent.second >= 0
                    && adjacent.second < grid.size
                    && !visitedPositions.contains(adjacent)
                ) {
                    val adjacentHeight = when {
                        grid[adjacent.second][adjacent.first] == 'E' -> 'z'
                        grid[adjacent.second][adjacent.first] == 'S' -> 'a'
                        else -> grid[adjacent.second][adjacent.first]
                    }

                    if (currentHeight - adjacentHeight >= -1) {
                        visitQueue.add(currentPath.plus(adjacent))
                        visitedPositions.add(adjacent)
                    }
                }
            }
        }
    }

    return (successPaths.minByOrNull { it.size }?.size ?: 0) - 1 // the first node doesn't count
}

