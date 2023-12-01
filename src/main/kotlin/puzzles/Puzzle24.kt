package puzzles

import java.io.File

private val lines = File("input/puzzle24/input.txt").readLines()

data class State(
    val position: Pair<Int, Int>,
    val map: MutableList<MutableList<MutableList<Char>>>,
    val index: Int
)

fun updateMap(map: MutableList<MutableList<MutableList<Char>>>): MutableList<MutableList<MutableList<Char>>> {
    val mapCopy = map.toMutableList()
    map.forEachIndexed { y, row ->
        mapCopy[y] = map[y].toMutableList()
        map[y].forEachIndexed { x, blizzards ->
            mapCopy[y][x] = blizzards.toMutableList()
        }
    }

    map.forEachIndexed { y, row ->
        row.forEachIndexed { x, blizzards ->
            blizzards.forEach { blizzard ->
                var newPosition = Pair(x, y) + blizzard.toDirection()

                if (newPosition.second < 0 || newPosition.second >= map.size
                    || newPosition.first < 0 || newPosition.first >= map[0].size
                ) {
                    //needs to wrap
                    when (blizzard) {
                        '<' -> newPosition = Pair(map[0].size - 1, newPosition.second)
                        '>' -> newPosition = Pair(0, newPosition.second)
                        'v' -> newPosition = Pair(newPosition.first, 0)
                        '^' -> newPosition = Pair(newPosition.first, map.size - 1)
                    }
                }

                mapCopy[y][x].remove(blizzard)
                mapCopy[newPosition.second][newPosition.first].add(blizzard)
            }
        }
    }

    return mapCopy
}

fun solve(
    startingPosition: Pair<Int, Int>,
    target: Pair<Int, Int>,
    map: MutableList<MutableList<MutableList<Char>>>
):
    Pair<Int, MutableList<MutableList<MutableList<Char>>>> {
    val queue = mutableSetOf(State(startingPosition, map, 1))
    while (true) {
        val current = queue.first()

        if (current.index % 10 == 0) println("${current.index}, ${queue.size}")
        queue.remove(current)

        val updatedMap = updateMap(current.map)

        val adjacents = listOf(
            current.position + Pair(1, 0),
            current.position + Pair(0, 1),
            current.position + Pair(-1, 0),
            current.position + Pair(0, -1),
            current.position
        )
        val nextPositions = mutableListOf<Pair<Int, Int>>()

        adjacents.forEach { adjacent ->
            if (adjacent == target) return Pair(current.index, updatedMap)
            if (
                adjacent.second >= 0 && adjacent.second < updatedMap.size &&
                adjacent.first >= 0 && adjacent.first < updatedMap[0].size &&
                updatedMap[adjacent.second][adjacent.first].isEmpty()
            ) nextPositions.add(adjacent)
        }

        queue.addAll(nextPositions.map { State(it, updatedMap, current.index + 1) })
        //visited.addAll(nextPositions.map { Pair(it, updatedMap) })
    }
}

fun puzzle24(): Int {
    val map = mutableListOf<MutableList<MutableList<Char>>>()

    lines.drop(1).dropLast(1).forEachIndexed { y, s ->
        map.add(y, mutableListOf())
        s.drop(1).dropLast(1).forEachIndexed { x, c ->
            map[y].add(mutableListOf())
            if (c != '#') {
                if (c != '.') {
                    map[y][x].add(c)
                }
            }
        }
    }

    val startingPosition = Pair(lines.first().indexOfFirst { it == '.' } - 1, -1)
    val endPosition = Pair(lines.last().indexOfFirst { it == '.' } - 1, lines.size - 2)

    var solve = solve(startingPosition, endPosition, map)
    var time = solve.first

    solve = solve(endPosition, startingPosition, solve.second)
    time += solve.first

    solve = solve(startingPosition, endPosition, solve.second)
    time += solve.first
    return time
}

data class Blizzard(
    val position: Pair<Int, Int>,
    val direction: Char
)

fun updateBlizzards(
    blizzards: List<Blizzard>,
    mapWidth: Int,
    mapHeight: Int
): MutableList<Blizzard> {
    val blizzardsCopy = blizzards.toMutableList()
    blizzards.forEach { blizzard ->
        var newPosition = blizzard.position + blizzard.direction.toDirection()

        if (newPosition.second < 0 || newPosition.second >= mapHeight
            || newPosition.first < 0 || newPosition.first >= mapWidth
        ) {
            //needs to wrap
            when (blizzard.direction) {
                '<' -> newPosition = Pair(mapWidth - 1, newPosition.second)
                '>' -> newPosition = Pair(0, newPosition.second)
                'v' -> newPosition = Pair(newPosition.first, 0)
                '^' -> newPosition = Pair(newPosition.first, mapHeight - 1)
            }
        }

        blizzardsCopy.remove(blizzard)
        blizzardsCopy.add(Blizzard(newPosition, blizzard.direction))
    }


    return blizzardsCopy
}

data class BlizzardState(
    val position: Pair<Int, Int>,
    val blizzards: List<Blizzard>,
    val index: Int
)

fun solveBlizzard(
    startingPosition: Pair<Int, Int>,
    target: Pair<Int, Int>,
    width: Int,
    height: Int,
    blizzards: List<Blizzard>
):
    Pair<Int, List<Blizzard>> {
    val queue = mutableSetOf(BlizzardState(startingPosition, blizzards, 1))
    while (true) {
        val current = queue.first()
        queue.remove(current)

        if (current.index % 10 == 0) println("${current.index}, ${queue.size}")

        val updatedBlizzards = updateBlizzards(current.blizzards, width, height)

        val adjacents = listOf(
            current.position + Pair(1, 0),
            current.position + Pair(0, 1),
            current.position + Pair(-1, 0),
            current.position + Pair(0, -1),
            current.position
        )
        val nextPositions = mutableListOf<Pair<Int, Int>>()

        adjacents.forEach { adjacent ->
            if (adjacent == target) return Pair(current.index, updatedBlizzards)
            if (
                adjacent == startingPosition || adjacent == target ||
                (adjacent.second in 0 until height &&
                    adjacent.first in 0 until width &&
                    updatedBlizzards.none { it.position == Pair(adjacent.first, adjacent.second) })
            ) nextPositions.add(adjacent)
        }

        queue.addAll(nextPositions.map { BlizzardState(it, updatedBlizzards, current.index + 1) })
    }
}

fun puzzle24alt(): Int {
    val blizzards = mutableListOf<Blizzard>()

    lines.drop(1).dropLast(1).forEachIndexed { y, s ->
        s.drop(1).dropLast(1).forEachIndexed { x, c ->
            if (c != '#') {
                if (c != '.') {
                    blizzards.add(Blizzard(Pair(x, y), c))
                }
            }
        }
    }

    val width = lines[0].length - 2
    val height = lines.size - 2

    val startingPosition = Pair(lines.first().indexOfFirst { it == '.' } - 1, -1)
    val endPosition = Pair(lines.last().indexOfFirst { it == '.' } - 1, lines.size - 2)

    var solve = solveBlizzard(startingPosition, endPosition, width, height, blizzards)
    var time = solve.first

    solve = solveBlizzard(endPosition, startingPosition, width, height, solve.second)
    time += solve.first

    solve = solveBlizzard(startingPosition, endPosition, width, height, solve.second)
    time += solve.first

    return time
}

fun Char.toDirection() = when {
    this == '<' -> Pair(-1, 0)
    this == '>' -> Pair(1, 0)
    this == 'v' -> Pair(0, 1)
    this == '^' -> Pair(0, -1)
    else -> throw UnsupportedOperationException("Invalid direction character")
}
