package puzzles

import java.io.File
import kotlin.math.abs

private val lines = File("input/puzzle11/input.txt").readLines()

fun puzzle11(expansionRate: Int): Long {
    val galaxies = mutableListOf<Pair<Long, Long>>()

    var maxX = 0
    var maxY = 0
    lines.forEachIndexed { y, s ->
        s.forEachIndexed { x, c ->
            if (c == '#') galaxies.add(Pair(x.toLong(), y.toLong()))

            if (x > maxX) maxX = x
            if (y > maxY) maxY = y
        }
    }

    for (y in (maxY - 1) downTo  0) {
        val expandedGalaxies = mutableListOf<Pair<Long, Long>>()
        if (galaxies.none { it.second == y.toLong() }) {
            val galaxiesBeyond = galaxies.filter { it.second > y }
            expandedGalaxies.addAll(galaxiesBeyond.map { Pair(it.first, it.second + expansionRate - 1) })
            galaxies.removeAll(galaxiesBeyond)
            galaxies.addAll(expandedGalaxies)
        }
    }

    for (x in (maxX - 1) downTo  0) {
        val expandedGalaxies = mutableListOf<Pair<Long, Long>>()
        if (galaxies.none { it.first == x.toLong() }) {
            val galaxiesBeyond = galaxies.filter { it.first > x }
            expandedGalaxies.addAll(galaxiesBeyond.map { Pair(it.first + expansionRate - 1, it.second) })
            galaxies.removeAll(galaxiesBeyond)
            galaxies.addAll(expandedGalaxies)
        }
    }

    return galaxies.sumOf { galaxy ->
        galaxies.filter { it != galaxy }.sumOf { abs(galaxy.first - it.first) + abs(galaxy.second - it.second) }
    } / 2L
}