package puzzles
import java.io.File

private val lines = File("input/puzzle2/input.txt").readLines()

fun puzzle2() = lines.foldIndexed(0) { index, acc, v ->
    val limits = mutableMapOf("red" to 12, "green" to 13, "blue" to 14)
    v.split(": ")[1].split("; ").forEach { pull ->
        pull.split(", ").forEach {
            if (it.split(" ")[0].toInt() > limits[it.split(" ")[1]]!!) {
                return@foldIndexed acc
            }
        }
    }
    acc + index + 1
}

fun puzzle2dot1() = lines.fold(0) { acc, v ->
    val minimums = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)
    v.split(": ")[1].split("; ").forEach { pull ->
        pull.split(", ").forEach {
            minimums[it.split(" ")[1]] =
                maxOf(minimums[it.split(" ")[1]]!!, it.split(" ")[0].toInt())
        }
    }
    acc + (minimums["red"]!! * minimums["green"]!! * minimums["blue"]!!)
}