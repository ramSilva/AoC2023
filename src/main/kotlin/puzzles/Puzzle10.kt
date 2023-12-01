package puzzles

import java.io.File
import kotlin.math.abs

private val lines = File("input/puzzle10/input.txt").readLines()

fun puzzle10(): Int {
    var counter = 0
    var register = 1
    val keyIntensities = mutableListOf<Int>()

    lines.forEach {
        counter++
        if ((counter - 20) % 40 == 0) {
            val intensity = counter * register
            keyIntensities.add(intensity)
        }
        if (it.contains("addx")) {
            counter++
            if ((counter - 20) % 40 == 0) {
                val intensity = counter * register
                keyIntensities.add(intensity)
            }

            val (sign, stringAmount) = Regex("\\w+ (-?)(\\d+)").find(it)!!.destructured

            val intAmount =
                if (sign.isEmpty()) stringAmount.toInt()
                else -stringAmount.toInt()

            register += intAmount
        }
    }
    return keyIntensities.sum()
}

fun puzzle10dot1() {
    var counter = 0
    var register = 1

    lines.forEach {
        if (abs(register - counter) < 2) {
            print("#")
        } else {
            print("-")
        }
        counter++
        if (counter % 40 == 0) {
            println()
            counter = 0
        }
        if (it.contains("addx")) {
            if (abs(register - counter) < 2) {
                print("#")
            } else {
                print("-")
            }
            counter++
            if (counter % 40 == 0) {
                println()
                counter = 0
            }

            val (sign, stringAmount) = Regex("\\w+ (-?)(\\d+)").find(it)!!.destructured

            val intAmount =
                if (sign.isEmpty()) stringAmount.toInt()
                else -stringAmount.toInt()

            register += intAmount
        }
    }
}