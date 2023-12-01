package puzzles

import java.io.File
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

private val lines = File("input/puzzle18/input.txt").readLines()

fun Triple<Int, Int, Int>.distance(other: Triple<Int, Int, Int>) =
    sqrt(
        (this.first - other.first).toFloat().pow(2)
            + (this.second - other.second).toFloat().pow(2)
            + (this.third - other.third).toFloat().pow(2)
    )

fun puzzle18(): Int {
    val fallenDroplets = mutableListOf<Triple<Int, Int, Int>>()

    return lines.sumOf {
        val (x, y, z) = "(\\d+),(\\d+),(\\d+)".toRegex().find(it)!!.destructured
        val droplet = Triple(x.toInt(), y.toInt(), z.toInt())

        val faces = 6 - fallenDroplets.count { it.distance(droplet) == 1.0f } * 2

        fallenDroplets.add(droplet)

        faces
    }
}

fun puzzle18dot1(): Int {
    val fallenDroplets = mutableListOf<Triple<Int, Int, Int>>()
    var minX = Int.MAX_VALUE
    var maxX = Int.MIN_VALUE

    var minY = Int.MAX_VALUE
    var maxY = Int.MIN_VALUE

    var minZ = Int.MAX_VALUE
    var maxZ = Int.MIN_VALUE

    lines.forEach {
        val (x, y, z) = "(\\d+),(\\d+),(\\d+)".toRegex().find(it)!!.destructured
        val droplet = Triple(x.toInt(), y.toInt(), z.toInt())

        minX = min(droplet.first, minX)
        maxX = max(droplet.first, maxX)

        minY = min(droplet.second, minY)
        maxY = max(droplet.second, maxY)

        minZ = min(droplet.third, minZ)
        maxZ = max(droplet.third, maxZ)

        fallenDroplets.add(droplet)
    }

    minX--
    minY--
    minZ--
    maxX++
    maxY++
    maxZ++

    val visitQueue = mutableSetOf<Triple<Int, Int, Int>>()
    val visitedPositions = mutableSetOf<Triple<Int, Int, Int>>()

    visitQueue.add(Triple(minX, minY, minZ))

    while (true) {
        val currentPos = visitQueue.first()
        visitQueue.remove(currentPos)

        visitedPositions.add(currentPos)

        val adjacent = mutableListOf(
            Triple(currentPos.first - 1, currentPos.second, currentPos.third),
            Triple(currentPos.first + 1, currentPos.second, currentPos.third),
            Triple(currentPos.first, currentPos.second - 1, currentPos.third),
            Triple(currentPos.first, currentPos.second + 1, currentPos.third),
            Triple(currentPos.first, currentPos.second, currentPos.third - 1),
            Triple(currentPos.first, currentPos.second, currentPos.third + 1),
        )
        adjacent.removeIf {
            visitedPositions.contains(it) || fallenDroplets.contains(it) ||
                it.first < minX || it.first > maxX ||
                it.second < minY || it.second > maxY ||
                it.third < minZ || it.third > maxZ
        }

        visitQueue.addAll(adjacent)

        if (visitQueue.isEmpty()) {
            break
        }
    }

    var faces = 0
    fallenDroplets.forEach outer@{ droplet ->
        visitedPositions.forEach { visited ->
            if (droplet.distance(visited) == 1.0f) {
                faces++
            }
        }
    }

    return faces
}