package puzzles

import java.io.File

private val lines = File("input/puzzle6/input.txt").readLines()

fun puzzle6(): Int {
    val times = "\\d+".toRegex().findAll(lines[0]).map { it.value.toInt() }.toList()
    val distances = "\\d+".toRegex().findAll(lines[1]).map { it.value.toInt() }.toList()

    return times.foldIndexed(1) { index, acc, t ->
        var winning = 0
        for (hold in 0..t) {
            val traveledDistance = (t - hold) * hold
            if (traveledDistance > distances[index]) winning++
        }

        acc * winning
    }
}

fun puzzle6dot1(): Int {
    val time = "\\d+".toRegex().findAll(lines[0]).fold("") { acc, matchResult -> acc + matchResult.value }.toLong()
    val distance = "\\d+".toRegex().findAll(lines[1]).fold("") { acc, matchResult -> acc + matchResult.value }.toLong()

    var winning = 0
    for (hold in 0..time) {
        val traveledDistance = (time - hold) * hold
        if (traveledDistance > distance) winning++
    }

    return winning
}

