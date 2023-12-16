package puzzles

import java.io.File
import kotlin.math.abs

private val lines = File("input/puzzle16/input.txt").readLines()

fun Pair<Int, Int>.sum(other: Pair<Int, Int>): Pair<Int, Int> =
    Pair(this.first + other.first, this.second + other.second)

fun puzzle16(): Int {
    val mirrors = mutableMapOf<Pair<Int, Int>, Char>()

    val maxX = lines[0].length
    val maxY = lines.size

    lines.forEachIndexed { y, s ->
        s.forEachIndexed { x, c ->
            if (c != '.') mirrors[Pair(x, y)] = c
        }
    }

    // store a list of light rays. Each light ray is composed by two pairs: Position and Direction.
    // The direction is a unit vector
    var lightInfo = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
    lightInfo.add(Pair(Pair(0, 0), Pair(1, 0)))

    val energizedSquares = mutableSetOf<Pair<Int, Int>>()
    val loopDetector3000 = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()

    while (lightInfo.isNotEmpty()) {
        val updatedLightInfo = lightInfo.toMutableList()
        lightInfo.forEach {
            updatedLightInfo.remove(it)
            loopDetector3000.add(it)
            energizedSquares.add(it.first)

            val newDirections = when (mirrors[it.first]) {
                '/' -> {
                    when (it.second) {
                        Pair(1, 0) -> {
                            listOf(Pair(0, -1))
                        }

                        Pair(-1, 0) -> {
                            listOf(Pair(0, 1))
                        }

                        Pair(0, 1) -> {
                            listOf(Pair(-1, 0))
                        }

                        Pair(0, -1) -> {
                            listOf(Pair(1, 0))
                        }

                        else -> {
                            listOf(it.second)
                        }
                    }
                }

                '\\' -> {
                    when (it.second) {
                        Pair(1, 0) -> {
                            listOf(Pair(0, 1))
                        }

                        Pair(-1, 0) -> {
                            listOf(Pair(0, -1))
                        }

                        Pair(0, 1) -> {
                            listOf(Pair(1, 0))
                        }

                        Pair(0, -1) -> {
                            listOf(Pair(-1, 0))
                        }

                        else -> {
                            listOf(it.second)
                        }
                    }
                }

                '|' -> {
                    when (it.second) {
                        Pair(1, 0), Pair(-1, 0) -> {
                            listOf(Pair(0, 1), Pair(0, -1))
                        }

                        else -> {
                            listOf(it.second)
                        }
                    }
                }

                '-' -> {
                    when (it.second) {
                        Pair(0, 1), Pair(0, -1) -> {
                            listOf(Pair(-1, 0), Pair(1, 0))
                        }

                        else -> {
                            listOf(it.second)
                        }
                    }
                }

                else -> {
                    listOf(it.second)
                }
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
    val mirrors = mutableMapOf<Pair<Int, Int>, Char>()

    val maxX = lines[0].length
    val maxY = lines.size

    lines.forEachIndexed { y, s ->
        s.forEachIndexed { x, c ->
            if (c != '.') mirrors[Pair(x, y)] = c
        }
    }

    var maxEnergy = 0
    for (x in 0 until maxX) {
        for (y in 0 until maxY) {
            val initialPosition = Pair(x, y)
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
                // store a list of light rays. Each light ray is composed by two pairs: Position and Direction.
                // The direction is a unit vector
                var lightInfo = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()

                lightInfo.add(Pair(initialPosition, it))

                val energizedSquares = mutableSetOf<Pair<Int, Int>>()
                val loopDetector3000 = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()

                while (lightInfo.isNotEmpty()) {
                    val updatedLightInfo = lightInfo.toMutableList()
                    lightInfo.forEach {
                        updatedLightInfo.remove(it)
                        loopDetector3000.add(it)
                        energizedSquares.add(it.first)

                        val newDirections = when (mirrors[it.first]) {
                            '/' -> {
                                when (it.second) {
                                    Pair(1, 0) -> {
                                        listOf(Pair(0, -1))
                                    }

                                    Pair(-1, 0) -> {
                                        listOf(Pair(0, 1))
                                    }

                                    Pair(0, 1) -> {
                                        listOf(Pair(-1, 0))
                                    }

                                    Pair(0, -1) -> {
                                        listOf(Pair(1, 0))
                                    }

                                    else -> {
                                        listOf(it.second)
                                    }
                                }
                            }

                            '\\' -> {
                                when (it.second) {
                                    Pair(1, 0) -> {
                                        listOf(Pair(0, 1))
                                    }

                                    Pair(-1, 0) -> {
                                        listOf(Pair(0, -1))
                                    }

                                    Pair(0, 1) -> {
                                        listOf(Pair(1, 0))
                                    }

                                    Pair(0, -1) -> {
                                        listOf(Pair(-1, 0))
                                    }

                                    else -> {
                                        listOf(it.second)
                                    }
                                }
                            }

                            '|' -> {
                                when (it.second) {
                                    Pair(1, 0), Pair(-1, 0) -> {
                                        listOf(Pair(0, 1), Pair(0, -1))
                                    }

                                    else -> {
                                        listOf(it.second)
                                    }
                                }
                            }

                            '-' -> {
                                when (it.second) {
                                    Pair(0, 1), Pair(0, -1) -> {
                                        listOf(Pair(-1, 0), Pair(1, 0))
                                    }

                                    else -> {
                                        listOf(it.second)
                                    }
                                }
                            }

                            else -> {
                                listOf(it.second)
                            }
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
                maxEnergy = maxOf(energizedSquares.size, maxEnergy)
            }
        }
    }

    return maxEnergy
}