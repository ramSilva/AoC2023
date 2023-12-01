package puzzles

import java.io.File
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

private val lines = File("input/puzzle21/input.txt").readLines()

fun puzzle21(): Long {
    val monkes = lines.fold(mutableMapOf<String, String>()) { acc, s ->
        val (key, value) = "(.*): (.*)".toRegex().find(s)!!.destructured

        acc[key] = value
        acc
    }

    fun monkeValue(key: String): Long {
        val monke = monkes[key]!!
        return if (monke.toIntOrNull() != null) monke.toLong()
        else {
            val (op1, operator, op2) = "(.*) ([+\\-*/]) (.*)".toRegex().find(monke)!!.destructured
            when (operator) {
                "+" -> monkeValue(op1) + monkeValue(op2)
                "-" -> monkeValue(op1) - monkeValue(op2)
                "*" -> monkeValue(op1) * monkeValue(op2)
                "/" -> monkeValue(op1) / monkeValue(op2)
                else -> -1
            }
        }
    }

    return monkeValue("root")
}
