package puzzles

import java.io.File
import kotlin.math.max

private val lines = File("input/puzzle17/testInput.txt").readLines()

fun puzzle17(): Int {
    val maxX = lines[0].length
    val maxY = lines.size

    val map = mutableMapOf<Pair<Int, Int>, Int>()

    lines.forEachIndexed { y, s ->
        s.forEachIndexed { x, c ->
            map[Pair(x, y)] = c.digitToInt()
        }
    }
    var currentPos = Pair(0, 0)
    val target = Pair(maxX - 1, maxY - 1)

    val visited = mutableSetOf<Pair<Int, Int>>()
    val distances = mutableMapOf(currentPos to Pair(0, listOf(currentPos)))

    fun Pair<Int, Int>.neighbours(): List<Pair<Int, Int>> {
        val neighbours = mutableListOf<Pair<Int, Int>>()

        val left = Pair(this.first - 1, this.second)
        val right = Pair(this.first + 1, this.second)
        val up = Pair(this.first, this.second - 1)
        val down = Pair(this.first, this.second + 1)

        if (left.first >= 0) neighbours.add(left)
        if (right.first < maxX) neighbours.add(right)
        if (up.second >= 0) neighbours.add(up)
        if (down.second < maxY) neighbours.add(down)

        return neighbours
    }

    var distance = 0

    while (currentPos != target) {
        visited.add(currentPos)
        val neighbours = currentPos.neighbours()
        if (neighbours.isEmpty()) continue

        neighbours.forEach {
            if (!distances.contains(it) || distances[it]!!.first > distance + map[it]!!) {
                val path = distances[currentPos]!!.second.toMutableList()
                path.add(it)
                if (
                    path.size < 4 || (
                            path.takeLast(4).map { it.first }.toSet().size != 1
                            && path.takeLast(4).map { it.second }.toSet().size != 1
                        )
                ) {
                    distances[it] = Pair(distance + map[it]!!, path)
                } else {
                    val i = 0
                }
            }
        }
        val next = distances.filter { !visited.contains(it.key) }.minByOrNull { it.value.first }!!
        currentPos = next.key
        distance = next.value.first
    }

    return distance
}
