package puzzles

import java.io.File

private val lines = File("input/puzzle13/input.txt").readLines()

fun puzzle13(): Int {
    val map = mutableListOf<Pair<Int, Int>>()
    var maxX = 0
    var maxY = 0
    var result = 0
    var yOffset = 0
    lines.forEachIndexed { y, s ->
        if (s.isEmpty()) {
            var mirror = -1
            for (x in 0 until maxX) {
                for (y in 0..maxY) {
                    for (i in 0..x) {
                        val pairsToCheck = listOf(Pair(x - i, y), Pair(x + i + 1, y))
                        if (map.containsAll(pairsToCheck) || !map.any { pairsToCheck.contains(it) }) {
                            if (mirror == -1) mirror = pairsToCheck.first().first
                        } else if (
                            pairsToCheck.first().first < 0 ||
                            pairsToCheck.last().first > maxX &&
                            mirror != -1
                        ) {
                            break
                        } else {
                            mirror = -1
                            break
                        }
                    }
                    if (mirror == -1) break
                }
                if (mirror != -1) break
            }

            if (mirror != -1) {
                result += mirror + 1
            } else {
                for (y in 0 until maxY) {
                    for (x in 0..maxX) {
                        for (i in 0..y) {
                            val pairsToCheck = listOf(Pair(x, y - i), Pair(x, y + i + 1))
                            if (map.containsAll(pairsToCheck) || !map.any { pairsToCheck.contains(it) }) {
                                if (mirror == -1) mirror = pairsToCheck.first().second
                            } else if (
                                pairsToCheck.first().second < 0 ||
                                pairsToCheck.last().second > maxY &&
                                mirror != -1
                            ) {
                                break
                            } else {
                                mirror = -1
                                break
                            }
                        }
                        if (mirror == -1) break
                    }
                    if (mirror != -1) break
                }
                result += (mirror + 1) * 100
            }

            map.clear()
            maxX = 0
            maxY = 0
            yOffset = y + 1
            return@forEachIndexed
        }

        s.forEachIndexed { x, c ->
            if (c == '#') {
                map.add(Pair(x, y - yOffset))
                maxX = maxOf(maxX, x)
                maxY = maxOf(maxY, y - yOffset)
            }
        }
    }

    return result
}

fun puzzle13dot1(): Int {
    val map = mutableListOf<Pair<Int, Int>>()
    var maxX = 0
    var maxY = 0
    var result = 0
    var yOffset = 0
    lines.forEachIndexed { y, s ->
        if (s.isEmpty()) {
            var mirror = -1
            var numberOfErrors = 0
            for (x in 0 until maxX) {
                numberOfErrors = 0
                for (y in 0..maxY) {
                    for (i in 0..x) {
                        val pairsToCheck = listOf(Pair(x - i, y), Pair(x + i + 1, y))
                        if (map.containsAll(pairsToCheck) || !map.any { pairsToCheck.contains(it) }) {
                            if (mirror == -1) mirror = pairsToCheck.first().first
                        } else if (
                            pairsToCheck.first().first < 0 ||
                            pairsToCheck.last().first > maxX &&
                            mirror != -1
                        ) {
                            break
                        } else if (numberOfErrors < 1) {
                            if (mirror == -1) mirror = pairsToCheck.first().first
                            numberOfErrors++
                        } else {
                            mirror = -1
                            break
                        }
                    }
                    if (mirror == -1) break
                }
                if (mirror != -1 && numberOfErrors > 0) break
            }

            if (mirror != -1 && numberOfErrors > 0) {
                result += mirror + 1
            } else {
                for (y in 0 until maxY) {
                    numberOfErrors = 0
                    for (x in 0..maxX) {
                        for (i in 0..y) {
                            val pairsToCheck = listOf(Pair(x, y - i), Pair(x, y + i + 1))
                            if (map.containsAll(pairsToCheck) || !map.any { pairsToCheck.contains(it) }) {
                                if (mirror == -1) mirror = pairsToCheck.first().second
                            } else if (
                                pairsToCheck.first().second < 0 ||
                                pairsToCheck.last().second > maxY &&
                                mirror != -1
                            ) {
                                break
                            } else if (numberOfErrors < 1) {
                                if (mirror == -1) mirror = pairsToCheck.first().second
                                numberOfErrors++
                            } else {
                                mirror = -1
                                break
                            }
                        }
                        if (mirror == -1) break
                    }
                    if (mirror != -1 && numberOfErrors > 0) break
                }
                result += (mirror + 1) * 100
            }

            map.clear()
            maxX = 0
            maxY = 0
            yOffset = y + 1
            return@forEachIndexed
        }

        s.forEachIndexed { x, c ->
            if (c == '#') {
                map.add(Pair(x, y - yOffset))
                maxX = maxOf(maxX, x)
                maxY = maxOf(maxY, y - yOffset)
            }
        }
    }

    return result
}
