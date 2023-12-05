package puzzles

import java.io.File

private val lines = File("input/puzzle5/input.txt").readLines()

fun puzzle5(): Long {
    val mapping = mutableListOf<Pair<Long, Long>>()

    lines[0].split(": ")[1].split(" ").map { it.toLong() }.forEach {
        mapping.add(Pair(it, it))
    }

    val newPairs = mutableListOf<Pair<Long, Long>>()
    val pairsToRemove = mutableListOf<Pair<Long, Long>>()
    lines.subList(2, lines.size).forEach { line ->
        if (line.contains("\\d".toRegex())) {
            val (destinationStart, sourceStart, length) = line.split(" ").map { it.toLong() }

            mapping.forEach {
                if (it.first <= (sourceStart + length - 1) && it.second >= sourceStart) {
                    val transposition = destinationStart - sourceStart
                    val newFirst = maxOf(it.first, sourceStart)
                    val newSecond = minOf(it.second, sourceStart + length - 1)

                    pairsToRemove.add(it)
                    newPairs.add(Pair(newFirst + transposition, newSecond + transposition))

                    if (newFirst > it.first) {
                        newPairs.add(Pair(it.first, newFirst - 1))
                    }
                    if (newSecond < it.second) {
                        newPairs.add(Pair(newSecond + 1, it.second))
                    }
                }
            }
        }
        if (line.isEmpty()) {
            mapping.removeAll(pairsToRemove)
            mapping.addAll(newPairs)

            pairsToRemove.clear()
            newPairs.clear()
        }
    }

    return mapping.minByOrNull { it.first }!!.first
}

fun puzzle5dot1(): Long {
    var mapping = mutableListOf<Pair<Long, Long>>()

    lines[0].split(": ")[1].split(" ").map { it.toLong() }.chunked(2).forEach {
        mapping.add(Pair(it[0], it[0] + it[1] - 1))
    }

    val processed = mutableListOf<Pair<Long, Long>>()
    var unprocessed = mapping.toMutableList()
    lines.subList(2, lines.size).forEach { line ->
        if (line.contains("\\d".toRegex())) {
            val (destinationStart, sourceStart, length) = line.split(" ").map { it.toLong() }

            mapping.forEach {
                if (it.first <= (sourceStart + length - 1) && it.second >= sourceStart) {
                    val transposition = destinationStart - sourceStart
                    val newFirst = maxOf(it.first, sourceStart)
                    val newSecond = minOf(it.second, sourceStart + length - 1)

                    processed.add(Pair(newFirst + transposition, newSecond + transposition))
                    unprocessed.remove(it)
                    if (newFirst > it.first) {
                        unprocessed.add(Pair(it.first, newFirst - 1))
                    }
                    if (newSecond < it.second) {
                        unprocessed.add(Pair(newSecond + 1, it.second))
                    }
                }
            }
            mapping = unprocessed.toMutableList()
        }

        if (line.isEmpty()) {
            mapping = processed.toMutableList()
            mapping.addAll(unprocessed)

            processed.clear()
            unprocessed = mapping.toMutableList()
        }
    }

    return mapping.minByOrNull { it.first }!!.first
}