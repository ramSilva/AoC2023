package puzzles

import java.io.File

private val lines = File("input/puzzle14/input.txt").readLines()

fun puzzle14(): Int {
    val grid = mutableSetOf<Pair<Int, Int>>()
    var bottom = Int.MIN_VALUE

    lines.forEach {
        it.split(" -> ").zipWithNext { rock1, rock2 ->
            val rock1coords = rock1.split(",")
            val rock2coords = rock2.split(",")

            for (x in rock1coords.first().toInt()..rock2coords.first().toInt()) {
                val y = rock1coords.last().toInt()
                grid.add(Pair(x, y))
            }

            for (x in rock2coords.first().toInt()..rock1coords.first().toInt()) {
                val y = rock1coords.last().toInt()
                grid.add(Pair(x, y))
            }

            for (y in rock1coords.last().toInt()..rock2coords.last().toInt()) {
                val x = rock1coords.first().toInt()
                grid.add(Pair(x, y))
            }

            for (y in rock2coords.last().toInt()..rock1coords.last().toInt()) {
                val x = rock1coords.first().toInt()
                grid.add(Pair(x, y))
            }
        }
    }

    grid.forEach {
        if (it.second >= bottom) {
            bottom = it.second
        }
    }

    val origin = Pair(500, 0)

    var i = 0
    var isMoving = true
    var sandPosition = origin
    while (true) {
        if (sandPosition.second > bottom) break

        if (!isMoving) {
            i++
            sandPosition = origin
            isMoving = true
        }

        val down = Pair(sandPosition.first, sandPosition.second + 1)
        val downRight = Pair(sandPosition.first + 1, sandPosition.second + 1)
        val downLeft = Pair(sandPosition.first - 1, sandPosition.second + 1)

        when {
            !grid.contains(down) -> sandPosition = down
            !grid.contains(downLeft) -> sandPosition = downLeft
            !grid.contains(downRight) -> sandPosition = downRight
            else -> {
                grid.add(sandPosition)
                isMoving = false
            }
        }
    }

    return i
}

fun puzzle14dot1(): Int {
    val grid = mutableSetOf<Pair<Int, Int>>()
    var bottom = Int.MIN_VALUE

    lines.forEach {
        it.split(" -> ").zipWithNext { rock1, rock2 ->
            val rock1coords = rock1.split(",")
            val rock2coords = rock2.split(",")

            for (x in rock1coords.first().toInt()..rock2coords.first().toInt()) {
                val y = rock1coords.last().toInt()
                grid.add(Pair(x, y))
            }

            for (x in rock2coords.first().toInt()..rock1coords.first().toInt()) {
                val y = rock1coords.last().toInt()
                grid.add(Pair(x, y))
            }

            for (y in rock1coords.last().toInt()..rock2coords.last().toInt()) {
                val x = rock1coords.first().toInt()
                grid.add(Pair(x, y))
            }

            for (y in rock2coords.last().toInt()..rock1coords.last().toInt()) {
                val x = rock1coords.first().toInt()
                grid.add(Pair(x, y))
            }
        }
    }

    grid.forEach {
        if (it.second >= bottom) {
            bottom = it.second
        }
    }

    val origin = Pair(500, 0)

    var i = 1
    var isMoving = true
    var sandPosition = origin
    while (true) {
        if (sandPosition.second == bottom + 1){
            grid.add(sandPosition)
            isMoving = false
        }

        if (!isMoving) {
            i++
            sandPosition = origin
            isMoving = true
        }

        val down = Pair(sandPosition.first, sandPosition.second + 1)
        val downRight = Pair(sandPosition.first + 1, sandPosition.second + 1)
        val downLeft = Pair(sandPosition.first - 1, sandPosition.second + 1)

        when {
            !grid.contains(down) -> sandPosition = down
            !grid.contains(downLeft) -> sandPosition = downLeft
            !grid.contains(downRight) -> sandPosition = downRight
            else -> {
                if(sandPosition == origin) break

                grid.add(sandPosition)
                isMoving = false
            }
        }
    }

    return i
}

