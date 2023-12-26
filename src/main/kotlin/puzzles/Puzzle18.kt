package puzzles

import java.io.File
import kotlin.math.abs

private val lines = File("input/puzzle18/input.txt").readLines()

fun puzzle18(): Int {
    val trench = mutableListOf<Pair<Int, Int>>()

    var maxX = 0
    var minX = 0
    var maxY = 0
    var minY = 0
    var currentPosition = Pair(0, 0)
    var direction: Pair<Int, Int>
    lines.forEach {
        val (directionString, amount) = it.takeWhile { it != '(' }.split(" ")

        direction = when (directionString) {
            "U" -> Pair(0, -1)
            "D" -> Pair(0, 1)
            "L" -> Pair(-1, 0)
            "R" -> Pair(1, 0)
            else -> Pair(0, 0)
        }

        for (i in 0 until amount.toInt()) {
            trench.add(currentPosition)
            maxX = maxOf(maxX, currentPosition.first)
            minX = minOf(minX, currentPosition.first)
            maxY = maxOf(maxY, currentPosition.second)
            minY = minOf(minY, currentPosition.second)
            currentPosition += direction
        }
    }

    var insideVector = Pair(1, 0)
    val insideTrench = mutableListOf<Pair<Int, Int>>()
    for (i in trench.indices) {
        val before = if (i == 0) trench.last() else trench[i - 1]
        val current = trench[i]
        val after = trench[(i + 1) % trench.size]

        var position = current
        var attempt = mutableListOf<Pair<Int, Int>>()
        while (true) {
            position += insideVector
            if (trench.contains(position)) {
                insideTrench.addAll(attempt)
                break
            }
            if (position.first < minX || position.first > maxX || position.second < minY || position.second > maxY)
                break

            attempt.add(position)
        }

        // L
        if (before.first > current.first && after.second < current.second) {
            // CW
            insideVector = rotateVector(insideVector, Math.PI / 2)
        }
        if (before.second < current.second && after.first > current.first) {
            // CCW
            insideVector = rotateVector(insideVector, -Math.PI / 2)
        }

        // F
        if (before.second > current.second && after.first > current.first) {
            // CW
            insideVector = rotateVector(insideVector, Math.PI / 2)
        }
        if (before.first > current.first && after.second > current.second) {
            // CCW
            insideVector = rotateVector(insideVector, -Math.PI / 2)
        }

        // 7
        if (before.first < current.first && after.second > current.second) {
            // CW
            insideVector = rotateVector(insideVector, Math.PI / 2)
        }
        if (before.second > current.second && after.first < current.first) {
            // CCW
            insideVector = rotateVector(insideVector, -Math.PI / 2)
        }

        // J
        if (before.second < current.second && after.first < current.first) {
            // CW
            insideVector = rotateVector(insideVector, Math.PI / 2)
        }
        if (before.first < current.first && after.second < current.second) {
            // CCW
            insideVector = rotateVector(insideVector, -Math.PI / 2)
        }

        position = current
        attempt = mutableListOf<Pair<Int, Int>>()
        while (true) {
            position += insideVector
            if (trench.contains(position)) {
                insideTrench.addAll(attempt)
                break
            }
            if (position.first < minX || position.first > maxX || position.second < minY || position.second > maxY)
                break

            attempt.add(position)
        }
    }

    trench.addAll(insideTrench)

    val set = trench.toSet()
    for (x in minX..maxX) {
        var line = ""
        for (y in minY..maxY) {
            line += if (set.contains(Pair(x, y))) '#' else '.'
        }
        //println(line)
    }
    return trench.toSet().size
}

fun Pair<Long, Long>.sum(other: Pair<Long, Long>): Pair<Long, Long> =
    Pair(this.first + other.first, this.second + other.second)

fun puzzle18dot1(): Long {
    val vertices = mutableListOf<Pair<Long, Long>>(Pair(0L, 0L))

    var direction: Pair<Long, Long>
    lines.forEach {
        val hexa = it.takeLastWhile { it != '#' }.split(")").first()

        direction = when (hexa.last()) {
            '3' -> Pair(0, -1)
            '1' -> Pair(0, 1)
            '2' -> Pair(-1, 0)
            '0' -> Pair(1, 0)
            else -> Pair(0, 0)
        }

        var offset = Pair(0L, 0L)
        val amount = hexa.dropLast(1).toLong(16)
        offset = offset.sum(Pair(direction.first * amount, direction.second * amount))

        val newPosition = vertices.last().sum(offset)

        vertices.add(newPosition)
    }

    var area = 0L
    var edge = 0L

    for (i in 0 until vertices.size - 1) {
        val xi = vertices[i].first
        val yi = vertices[i].second

        val xj = vertices[i + 1].first
        val yj = vertices[i + 1].second

        area += (xi * yj) - (xj * yi)

        edge += abs((xi - xj) + (yi - yj))
    }

    area = abs(area) / 2
    edge = (edge/ 2) + 1

    return area + edge
}