package puzzles

import java.io.File
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

private val lines = File("input/puzzle22/input.txt").readLines()

fun puzzle22(): Int {
    val map = mutableListOf<MutableList<Char>>()

    lines.dropLast(2).forEachIndexed { i, s ->
        map.add(mutableListOf())
        s.forEach {
            map[i].add(it)
        }
    }

    var position = Pair(map[0].indexOfFirst { it == '.' }, 0)
    var angle = 0.0
    var direction = Pair(1, 0)
    val moves = "(\\D?)(\\d+)".toRegex().findAll(lines.last())
    moves.forEach {
        val (directionString, amount) = it.destructured

        when (directionString) {
            "L" -> {
                angle = (angle - PI / 2.0) % (PI * 2)
                direction = angle.direction()
            }
            "R" -> {
                angle = (angle + PI / 2.0) % (PI * 2)
                direction = angle.direction()
            }
            else -> {}
        }

        for (i in 0 until amount.toInt()) {
            var newPosition = Pair(
                position.first + direction.first,
                position.second + direction.second
            )

            if (direction.first == 0) {
                // Moving vertically
                if (newPosition.second < 0
                    || newPosition.second >= map.size
                    || newPosition.first >= map[newPosition.second].size
                    || map[newPosition.second][newPosition.first] == ' '
                ) {
                    // Wrap. Change the newPosition
                    val wrapY = if (direction.second == 1) {
                        map.indexOfFirst {
                            position.first < it.size && (it[position.first] == '.' || it[position.first] == '#')
                        }
                    } else {
                        map.indexOfLast {
                            position.first < it.size && (it[position.first] == '.' || it[position.first] == '#')
                        }
                    }
                    newPosition = Pair(newPosition.first, wrapY)
                }
            } else {
                // Moving horizontally
                if (newPosition.first < 0
                    || newPosition.first >= map[newPosition.second].size
                    || map[newPosition.second][newPosition.first] == ' '
                ) {
                    // Wrap. Change the newPosition
                    val wrapX = if (direction.first == 1) {
                        map[newPosition.second].indexOfFirst { it == '.' || it == '#' }
                    } else {
                        map[newPosition.second].indexOfLast { it == '.' || it == '#' }
                    }
                    newPosition = Pair(wrapX, newPosition.second)
                }
            }
            if (map[newPosition.second][newPosition.first] != '#') {
                position = newPosition
            }
        }
    }

    return 1000 * (position.second + 1) + 4 * (position.first + 1) + direction.score()
}

fun Double.direction() = Pair(cos(this).roundToInt(), sin(this).roundToInt())
fun Pair<Int, Int>.score() =
    if (this.first == 1 && this.second == 0) 0
    else if (this.first == 0 && this.second == 1) 1
    else if (this.first == -1 && this.second == 0) 2
    else if (this.first == 0 && this.second == -1) 3
    else throw UnsupportedOperationException("Directions must be one of the 4 above")