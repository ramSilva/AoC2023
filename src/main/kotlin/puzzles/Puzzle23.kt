package puzzles

import java.io.File
import java.util.Collections
import kotlin.math.pow
import kotlin.math.sqrt

private val lines = File("input/puzzle23/input.txt").readLines()

data class Elf(
    var position: Pair<Int, Int>,
    var destination: Pair<Int, Int>
)

data class CheckCondition(
    val coordinatesToCheck: List<Pair<Int, Int>>,
    val destination: Pair<Int, Int>
)

fun puzzle23(): Int {
    val elves = mutableListOf<Elf>()

    fun hasAdjacent(elf: Elf) = elves.any { other ->
        val distance = sqrt(
            (elf.position.first - other.position.first).toFloat().pow(2)
                + (elf.position.second - other.position.second).toFloat().pow(2)
        )
        distance <= sqrt(2.0f) && distance > 0
    }

    lines.forEachIndexed { y, s ->
        s.forEachIndexed { x, c ->
            if (c == '#') elves.add(Elf(Pair(x, y), Pair(x, y)))
        }
    }

    var minX = elves.minOf { it.position.first }
    var maxX = elves.maxOf { it.position.first }
    var minY = elves.minOf { it.position.second }
    var maxY = elves.maxOf { it.position.second }

    val N = Pair(0, -1)
    val NE = Pair(1, -1)
    val E = Pair(1, 0)
    val SE = Pair(1, 1)
    val S = Pair(0, 1)
    val SW = Pair(-1, 1)
    val W = Pair(-1, 0)
    val NW = Pair(-1, -1)

    val check1 = CheckCondition(listOf(N, NW, NE), Pair(0, -1))
    val check2 = CheckCondition(listOf(S, SW, SE), Pair(0, 1))
    val check3 = CheckCondition(listOf(W, NW, SW), Pair(-1, 0))
    val check4 = CheckCondition(listOf(E, NE, SE), Pair(1, 0))

    val checks = listOf(check1, check2, check3, check4)

    val print = false
    var i = 0
    while (true) {
        i++

        if (print) {
            for (y in minY..maxY) {
                var str = ""
                for (x in minX..maxX) {
                    str += if (elves.any { it.position == Pair(x, y) }) '#' else '.'
                }
                println(str)
            }
            println()
            println()
        }

        elves.forEach elvesFor@{ elf ->
            if (hasAdjacent(elf)) {
                checks.forEach { check ->
                    if (check.coordinatesToCheck.none { coordinate ->
                            elves.any { it.position == elf.position + coordinate }
                        }) {
                        elf.destination = elf.position + check.destination
                        return@elvesFor
                    }
                }
            }
        }

        var moved = false
        elves.forEach { elf ->
            if (elves.none { it.destination == elf.destination && it.position != elf.position }) {
                if(elf.position != elf.destination) moved = true
                elf.position = elf.destination
            }
        }

        elves.forEach { it.destination = it.position }
        minX = elves.minOf { it.position.first }
        maxX = elves.maxOf { it.position.first }
        minY = elves.minOf { it.position.second }
        maxY = elves.maxOf { it.position.second }

        Collections.rotate(checks, -1)
        if(!moved) break
    }

    return i
}

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) =
    Pair(this.first + other.first, this.second + other.second)
