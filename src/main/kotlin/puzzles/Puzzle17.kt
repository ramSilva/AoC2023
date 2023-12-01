package puzzles

import java.io.File
import kotlin.math.max

private val lines = File("input/puzzle17/input.txt").readLines()

data class Shape(
    val occupied: List<Pair<Int, Int>>,
    val width: Int,
    val height: Int
)

fun puzzle17(): Long {
    val shapes = listOf(
        Shape(
            occupied = listOf(Pair(0, 0), Pair(1, 0), Pair(2, 0), Pair(3, 0)),
            width = 4,
            height = 1
        ),
        Shape(
            occupied = listOf(Pair(0, 1), Pair(1, 0), Pair(1, 1), Pair(1, 2), Pair(2, 1)),
            width = 3,
            height = 3
        ),
        Shape(
            occupied = listOf(Pair(0, 0), Pair(1, 0), Pair(2, 0), Pair(2, 1), Pair(2, 2)),
            width = 3,
            height = 3
        ),
        Shape(
            occupied = listOf(Pair(0, 0), Pair(0, 1), Pair(0, 2), Pair(0, 3)),
            width = 1,
            height = 4
        ),
        Shape(
            occupied = listOf(Pair(0, 0), Pair(0, 1), Pair(1, 0), Pair(1, 1)),
            width = 2,
            height = 2
        )
    )

    var shapeIndex = 0
    var pushIndex = 0
    var height = 0L
    val grid = mutableListOf<Pair<Int, Long>>()

    for (i in 0 until 1000000000000) {
        var pos = Pair(2, height + 3)
        val shape = shapes[shapeIndex]
        shapeIndex = (shapeIndex + 1) % shapes.size

        while (true) {
            val pushDirection = lines[0][pushIndex]
            pushIndex = (pushIndex + 1) % lines[0].length

            if (pushDirection == '<' && shape.occupied.none {
                    grid.contains(
                        Pair(
                            pos.first + it.first - 1,
                            pos.second + it.second
                        )
                    )
                } && pos.first - 1 >= 0)
                pos = Pair(pos.first - 1, pos.second)

            if (pushDirection == '>' && shape.occupied.none {
                    grid.contains(
                        Pair(
                            pos.first + it.first + 1,
                            pos.second + it.second
                        )
                    )
                } && pos.first + shape.width < 7)
                pos = Pair(pos.first + 1, pos.second)

            if (shape.occupied.none {
                    grid.contains(
                        Pair(
                            pos.first + it.first,
                            pos.second + it.second - 1
                        )
                    )
                } && pos.second > 0)
                pos = Pair(pos.first, pos.second - 1)
            else {
                shape.occupied.forEach {
                    grid.add(Pair(pos.first + it.first, pos.second + it.second))
                }
                height = max(height, pos.second + shape.height)

                for (y in i downTo i - shape.height) {
                    val toCheck = mutableListOf<Pair<Int, Long>>()
                    for (x in 0 until 7) {
                        toCheck.add(Pair(x, y.toLong()))
                    }
                    if (grid.containsAll(toCheck)) {
                        grid.removeIf { it.second <= y }
                    }
                }

                break
            }
        }
    }

    /*for (i in height downTo 0) {
        var str = ""
        for (j in 0 until 7)
            str += if (grid.contains(Pair(j, i))) "#" else "."
        println(str)
    }*/

    return height
}
