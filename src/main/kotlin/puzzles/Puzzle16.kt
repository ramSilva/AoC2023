package puzzles

import java.io.File
import kotlin.math.abs

private val lines = File("input/puzzle16/input.txt").readLines()

fun Pair<Int, Int>.sum(other: Pair<Int, Int>): Pair<Int, Int> =
    Pair(this.first + other.first, this.second + other.second)

fun puzzle16(initialPosition: Pair<Int, Int> = Pair(0, 0), initialDirection: Pair<Int, Int> = Pair(1, 0)): Int {
    val mirrors = mutableMapOf<Pair<Int, Int>, Char>()

    val maxX = lines[0].length
    val maxY = lines.size

    lines.forEachIndexed { y, s ->
        s.forEachIndexed { x, c ->
            if (c != '.') mirrors[Pair(x, y)] = c
        }
    }

    var lightInfo = mutableListOf(Pair(initialPosition, initialDirection))

    val energizedSquares = mutableSetOf<Pair<Int, Int>>()
    val loopDetector3000 = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()

    while (lightInfo.isNotEmpty()) {
        val updatedLightInfo = lightInfo.toMutableList()
        lightInfo.forEach {
            updatedLightInfo.remove(it)
            loopDetector3000.add(it)
            energizedSquares.add(it.first)

            val newDirections = when (mirrors[it.first]) {
                '/' -> listOf(Pair(it.second.second * -1, it.second.first * -1))

                '\\' -> listOf(Pair(it.second.second, it.second.first))

                '|' -> when (it.second) {
                    Pair(1, 0), Pair(-1, 0) -> listOf(Pair(0, 1), Pair(0, -1))
                    else -> listOf(it.second)
                }

                '-' -> when (it.second) {
                    Pair(0, 1), Pair(0, -1) -> listOf(Pair(-1, 0), Pair(1, 0))
                    else -> listOf(it.second)
                }

                else -> listOf(it.second)
            }

            newDirections.forEach { dir ->
                val newPosition = it.first.sum(dir)
                val newLightInfo = Pair(newPosition, dir)
                if (newPosition.first < maxX && newPosition.second < maxY && newPosition.first >= 0 && newPosition.second >= 0
                    && !loopDetector3000.contains(newLightInfo)
                ) {
                    updatedLightInfo.add(newLightInfo)
                }

            }
        }
        lightInfo = updatedLightInfo.toMutableList()
    }

    return energizedSquares.size
}

fun puzzle16dot1(): Int {
    val maxX = lines[0].length
    val maxY = lines.size

    var maxEnergy = 0
    for (x in 0 until maxX) {
        for (y in 0 until maxY) {
            val initialDirections = mutableListOf<Pair<Int, Int>>()
            if (x == 0)
                initialDirections.add(Pair(1, 0))
            if (x == maxX - 1)
                initialDirections.add(Pair(-1, 0))
            if (y == 0)
                initialDirections.add(Pair(0, 1))
            if (y == maxY - 1)
                initialDirections.add(Pair(0, -1))

            initialDirections.forEach {
                maxEnergy = maxOf(puzzle16(Pair(x, y), it), maxEnergy)
            }
        }
    }

    return maxEnergy
}