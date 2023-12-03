package puzzles

import java.io.File
import kotlin.math.abs

private val lines = File("input/puzzle3/input.txt").readLines()

data class EngineNumber(
    val number: Int,
    val y: Int,
    val xStart: Int,
    val xEnd: Int,
    var counted: Boolean = false
)

fun puzzle3(): Int {
    val numbers = mutableListOf<EngineNumber>()

    lines.forEachIndexed { index, s ->
        val matches = "\\d+".toRegex().findAll(s)
        numbers.addAll(matches.map { match ->
            EngineNumber(match.value.toInt(), index, match.range.first, match.range.last)
        }.toList())
    }

    var sum = 0
    lines.forEachIndexed { y, s ->
        s.forEachIndexed { x, c ->
            if (!c.isDigit() && c != '.') {
                numbers.forEach { n ->
                    if (!n.counted && abs(y - n.y) <= 1 && x >= n.xStart - 1 && x <= n.xEnd + 1) {
                        n.counted = true
                        sum += n.number
                    }
                }
            }
        }
    }

    return sum
}

fun puzzle3dot1(): Int {
    val numbers = mutableListOf<EngineNumber>()

    lines.forEachIndexed { index, s ->
        val matches = "\\d+".toRegex().findAll(s)
        numbers.addAll(matches.map { match ->
            EngineNumber(match.value.toInt(), index, match.range.first, match.range.last)
        }.toList())
    }

    var sum = 0
    lines.forEachIndexed { y, s ->
        s.forEachIndexed { x, c ->
            if (!c.isDigit() && c != '.') {
                val adjacent = numbers.filter { n ->
                    abs(y - n.y) <= 1 && x >= n.xStart - 1 && x <= n.xEnd + 1
                }
                if (adjacent.count() == 2) {
                    sum += adjacent[0].number * adjacent[1].number
                }
            }
        }
    }

    return sum
}
