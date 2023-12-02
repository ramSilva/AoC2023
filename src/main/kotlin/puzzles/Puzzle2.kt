package puzzles

import java.io.File

private val lines = File("input/puzzle2/input.txt").readLines()

fun puzzle2(): Int {
    val limits = mutableMapOf("red" to 12, "green" to 13, "blue" to 14)
    var valid = 0
    lines.forEach line@{
        val splits = it.split(": ")
        val game = "\\d+".toRegex().find(splits[0])

        val pulls = splits[1].split("; ")

        pulls.forEach { pull ->
            val balls = pull.split(", ")
            balls.forEach {
                val ball = it.split(" ")
                if (ball[0].toInt() > limits[ball[1]]!!) {
                    return@line
                }
            }
        }
        valid += game!!.value.toInt()
    }

    return valid
}

fun puzzle2dot1(): Int {
    var powerSet = 0
    lines.forEach line@{
        val splits = it.split(": ")

        val pulls = splits[1].split("; ")

        var minimums = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)
        pulls.forEach { pull ->
            val balls = pull.split(", ")
            balls.forEach {
                val ball = it.split(" ")
                minimums[ball[1]] = maxOf(minimums[ball[1]]!!, ball[0].toInt())
            }
        }
        powerSet += (minimums["red"]!! * minimums["green"]!! * minimums["blue"]!!)
    }

    return powerSet
}