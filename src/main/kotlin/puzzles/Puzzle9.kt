package puzzles

import java.io.File
import kotlin.math.pow
import kotlin.math.sign
import kotlin.math.sqrt

private val lines = File("input/puzzle9/input.txt").readLines()

fun puzzle9(): Int {
    val visitedTailPositions = mutableSetOf(Pair(0f, 0f))
    var headPosition = Pair(0f, 0f)
    var tailPosition = Pair(0f, 0f)

    lines.forEach {
        val (direction, amount) = Regex("(\\w) (\\d+)").find(it)!!.destructured
        val moveDirection = when (direction) {
            "L" -> Pair(-1, 0)
            "R" -> Pair(1, 0)
            "U" -> Pair(0, -1)
            "D" -> Pair(0, 1)
            else -> Pair(0, 0)
        }

        for (i in 0 until amount.toInt()) {
            headPosition = Pair(
                headPosition.first + moveDirection.first,
                headPosition.second + moveDirection.second
            )
            if (headPosition.distance(tailPosition) > sqrt(2.0f)) {
                val delta = Pair(
                    sign(headPosition.first - tailPosition.first),
                    sign(headPosition.second - tailPosition.second)
                )

                tailPosition = Pair(
                    tailPosition.first + delta.first,
                    tailPosition.second + delta.second
                )
                visitedTailPositions.add(tailPosition)
            }
        }
    }

    return visitedTailPositions.size
}

fun puzzle9dot1(): Int {
    val visitedTailPositions = mutableSetOf(Pair(0f, 0f))
    val knots = MutableList(10) { Pair(0f, 0f) }

    lines.forEach {
        val (direction, amount) = Regex("(\\w) (\\d+)").find(it)!!.destructured
        val moveDirection = when (direction) {
            "L" -> Pair(-1, 0)
            "R" -> Pair(1, 0)
            "U" -> Pair(0, -1)
            "D" -> Pair(0, 1)
            else -> Pair(0, 0)
        }

        for (i in 0 until amount.toInt()) {
            knots[0] = Pair(
                knots[0].first + moveDirection.first,
                knots[0].second + moveDirection.second
            )

            for (i in 0 until knots.size - 1) {
                val knotA = knots[i]
                val knotB = knots[i + 1]

                if (knotA.distance(knotB) > sqrt(2.0f)) {
                    val delta = Pair(
                        sign(knotA.first - knotB.first),
                        sign(knotA.second - knotB.second)
                    )

                    knots[i + 1] = Pair(
                        knotB.first + delta.first,
                        knotB.second + delta.second
                    )
                }
            }
            visitedTailPositions.add(knots.last())
        }
    }

    return visitedTailPositions.size
}

fun Pair<Float, Float>.distance(other: Pair<Float, Float>) =
    sqrt(
        (this.first - other.first).pow(2) + (this.second - other.second).pow(2)
    )