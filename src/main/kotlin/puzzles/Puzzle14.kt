package puzzles

import java.io.File

private val lines = File("input/puzzle14/input.txt").readLines()

fun puzzle14(): Int {
    val originalMap = mutableMapOf<Pair<Int, Int>, Char>()
    var maxX = lines[0].length - 1
    var maxY = lines.size - 1
    lines.forEachIndexed { y, s ->
        s.forEachIndexed { x, c ->
            if (c != '.') originalMap[Pair(x, y)] = c
        }
    }

    val movedMap = originalMap.filter { it.value == '#' }.toMutableMap()
    var result = 0
    originalMap.forEach {
        if (it.value == 'O') {
            for (y in it.key.second downTo 0) {
                val currentPos = Pair(it.key.first, y)
                if (movedMap.containsKey(currentPos) || originalMap[currentPos] == '#') {
                    movedMap[Pair(it.key.first, y + 1)] = 'O'
                    result += maxY - y
                    break
                }

                if (y == 0) {
                    movedMap[Pair(it.key.first, 0)] = 'O'
                    result += maxY + 1
                }
            }
        }
    }

    //val sorted = movedMap.toList().sortedBy { it.first.second }.sortedBy { it.first.first }.toMap()

    return result
}

fun puzzle14dot1(): Int {
    var originalMap = mutableMapOf<Pair<Int, Int>, Char>()
    val maxX = lines[0].length - 1
    val maxY = lines.size - 1
    lines.forEachIndexed { y, s ->
        s.forEachIndexed { x, c ->
            if (c != '.') originalMap[Pair(x, y)] = c
        }
    }

    val cycleDetector3000 = mutableListOf<List<Pair<Int, Int>>>()
    val iterations = 1000000000
    val finalRockPositions = mutableListOf<Pair<Int, Int>>()
    for (i in 0..iterations) {
        val rockPositions = originalMap.filter { it.value == 'O' }.keys.toList()
        if (cycleDetector3000.contains(rockPositions)) {
            val cycleSize = cycleDetector3000.size - cycleDetector3000.indexOf(rockPositions)
            var cycleOffset = i
            while (cycleOffset < iterations)
                cycleOffset += cycleSize

            cycleOffset -= iterations
            finalRockPositions.addAll(cycleDetector3000[cycleDetector3000.size - cycleOffset])
            break
        }

        cycleDetector3000.add(rockPositions)
        // North
        var movedMap = originalMap.filter { it.value == '#' }.toMutableMap()
        originalMap
            .toList()
            .sortedBy { it.first.second }
            .toMap()
            .forEach {
                if (it.value == 'O') {
                    for (y in it.key.second downTo 0) {
                        val currentPos = Pair(it.key.first, y)
                        if (movedMap.containsKey(currentPos) || originalMap[currentPos] == '#') {
                            movedMap[Pair(it.key.first, y + 1)] = 'O'
                            break
                        }

                        if (y == 0) {
                            movedMap[Pair(it.key.first, 0)] = 'O'
                        }
                    }
                }
            }
        originalMap = movedMap.toMutableMap()

        // West
        movedMap = originalMap.filter { it.value == '#' }.toMutableMap()
        originalMap
            .toList()
            .sortedBy { it.first.first }
            .toMap()
            .forEach {
                if (it.value == 'O') {
                    for (x in it.key.first downTo 0) {
                        val currentPos = Pair(x, it.key.second)
                        if (movedMap.containsKey(currentPos) || originalMap[currentPos] == '#') {
                            movedMap[Pair(x + 1, it.key.second)] = 'O'
                            break
                        }

                        if (x == 0) {
                            movedMap[Pair(0, it.key.second)] = 'O'
                        }
                    }
                }
            }
        originalMap = movedMap.toMutableMap()

        // South
        movedMap = originalMap.filter { it.value == '#' }.toMutableMap()
        originalMap
            .toList()
            .sortedByDescending { it.first.second }
            .toMap()
            .forEach {
                if (it.value == 'O') {
                    for (y in it.key.second..maxY) {
                        val currentPos = Pair(it.key.first, y)
                        if (movedMap.containsKey(currentPos) || originalMap[currentPos] == '#') {
                            movedMap[Pair(it.key.first, y - 1)] = 'O'
                            break
                        }

                        if (y == maxY) {
                            movedMap[Pair(it.key.first, maxY)] = 'O'
                        }
                    }
                }
            }
        originalMap = movedMap.toMutableMap()

        // East
        movedMap = originalMap.filter { it.value == '#' }.toMutableMap()
        originalMap
            .toList()
            .sortedByDescending { it.first.first }
            .toMap()
            .forEach {
                if (it.value == 'O') {
                    for (x in it.key.first..maxX) {
                        val currentPos = Pair(x, it.key.second)
                        if (movedMap.containsKey(currentPos) || originalMap[currentPos] == '#') {
                            movedMap[Pair(x - 1, it.key.second)] = 'O'
                            break
                        }

                        if (x == maxX) {
                            movedMap[Pair(maxX, it.key.second)] = 'O'
                        }
                    }
                }
            }
        originalMap = movedMap.toMutableMap()
    }

    return finalRockPositions.sumOf {
        maxY + 1 - it.second
    }
}

