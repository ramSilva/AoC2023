package puzzles

import java.io.File
import kotlin.math.cos
import kotlin.math.sin

private val lines = File("input/puzzle10/testInput.txt").readLines()

fun puzzle10(): Int {
    val pipes = mapOf(
        '|' to Pair(Pair(0, -1), Pair(0, 1)),
        '-' to Pair(Pair(1, 0), Pair(-1, 0)),
        'L' to Pair(Pair(1, 0), Pair(0, -1)),
        'F' to Pair(Pair(0, 1), Pair(1, 0)),
        'J' to Pair(Pair(0, -1), Pair(-1, 0)),
        '7' to Pair(Pair(-1, 0), Pair(0, 1)),
        '.' to Pair(Pair(0, 0), Pair(0, 0)),
        'S' to Pair(Pair(1, 0), Pair(0, 1)), // by looking at my input the S must be an F pipe
    )

    val map = mutableMapOf<Pair<Int, Int>, Pair<Pair<Int, Int>, Pair<Int, Int>>>()
    var position = Pair(0, 0)
    lines.forEachIndexed { y, s ->
        s.forEachIndexed { x, c ->
            if (c == 'S') {
                position = Pair(x, y)
            }

            map[Pair(x, y)] = pipes[c]!!
        }
    }

    val visited = mutableListOf<Pair<Int, Int>>()
    var distance = 0
    while (true) {
        visited.add(position)
        distance++

        val operation = map[position]!!
        val possibleNext = listOf(
            Pair(position.first + operation.first.first, position.second + operation.first.second),
            Pair(
                position.first + operation.second.first,
                position.second + operation.second.second
            ),
        ).filter { !visited.contains(it) }

        if (possibleNext.isEmpty()) break

        position = possibleNext.first()
    }

    return distance / 2
}


fun rotateVector(vector: Pair<Int, Int>, angleRadians: Double): Pair<Int, Int> {
    val cosTheta = cos(angleRadians)
    val sinTheta = sin(angleRadians)

    val newX = cosTheta * vector.first - sinTheta * vector.second
    val newY = sinTheta * vector.first + cosTheta * vector.second

    return Pair(newX.toInt(), newY.toInt())
}
fun puzzle10dot1(): Int {
    val pipes = mapOf(
        '|' to Pair(Pair(0, -1), Pair(0, 1)),
        '-' to Pair(Pair(1, 0), Pair(-1, 0)),
        'L' to Pair(Pair(1, 0), Pair(0, -1)),
        'F' to Pair(Pair(0, 1), Pair(1, 0)),
        'J' to Pair(Pair(0, -1), Pair(-1, 0)),
        '7' to Pair(Pair(-1, 0), Pair(0, 1)),
        '.' to Pair(Pair(0, 0), Pair(0, 0)),
        'S' to Pair(Pair(0, 1), Pair(1, 0)), // by looking at my input the S must be an F pipe
    )

    val map = mutableMapOf<Pair<Int, Int>, Char>()
    var position = Pair(0, 0)
    var startingPosition = Pair(0, 0)
    lines.forEachIndexed { y, s ->
        s.forEachIndexed { x, c ->
            if (c == 'S') {
                startingPosition = Pair(x, y)
                position = Pair(x, y)
            }

            map[Pair(x, y)] = c
        }
    }

    val path = mutableListOf<Pair<Int, Int>>()
    while (true) {
        path.add(position)

        val operation = map[position]!!
        val possibleNext = listOf(
            Pair(position.first + pipes[operation]!!.first.first, position.second + pipes[operation]!!.first.second),
            Pair(
                position.first + pipes[operation]!!.second.first,
                position.second + pipes[operation]!!.second.second
            ),
        )
        val next = possibleNext.filter { !path.contains(it) }
        if (next.isEmpty()) break
        position = next.first()
    }

    // Oh shit here we go again
    var vector = Pair(0, -1)
    position = startingPosition

    val newPath = mutableListOf<Pair<Int, Int>>()
    val tilesInside = mutableSetOf<Pair<Int, Int>>()
    while (true) {
        newPath.add(position)

        var tilesPotentiallyInside = mutableListOf<Pair<Int, Int>>()
        var positionToCheck = position
        while (true) {
            positionToCheck =
                Pair(positionToCheck.first + vector.first, positionToCheck.second + vector.second)

            // We reached the edge of the map
            if (!map.containsKey(positionToCheck)) break

            // We reached another point in the maze
            if (path.contains(positionToCheck)) {
                tilesInside.addAll(tilesPotentiallyInside)
                break
            }
            tilesPotentiallyInside.add(positionToCheck)
        }

        val operation = map[position]!!
        val possibleNext = listOf(
            Pair(position.first + pipes[operation]!!.first.first, position.second + pipes[operation]!!.first.second),
            Pair(
                position.first + pipes[operation]!!.second.first,
                position.second + pipes[operation]!!.second.second
            ),
        )
        val next = possibleNext.filter { !newPath.contains(it) }
        if (next.isEmpty()) break

        if(operation != '-' && operation != '|') {
            // The pipes map is made so the 1st element means a turn to the left and the 2nd means a turn to the right
            vector = if (next.first() == possibleNext.first()) {
                rotateVector(vector, -Math.PI / 2)
            } else {
                rotateVector(vector, Math.PI / 2)
            }

            tilesPotentiallyInside = mutableListOf<Pair<Int, Int>>()
            positionToCheck = position
            while (true) {
                positionToCheck =
                    Pair(positionToCheck.first + vector.first, positionToCheck.second + vector.second)

                // We reached the edge of the map
                if (!map.containsKey(positionToCheck)) break

                // We reached another point in the maze
                if (path.contains(positionToCheck)) {
                    tilesInside.addAll(tilesPotentiallyInside)
                    break
                }
                tilesPotentiallyInside.add(positionToCheck)
            }
        }

        position = next.first()
    }

    return tilesInside.size
}