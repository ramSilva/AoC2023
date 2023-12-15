package puzzles

import java.io.File
import kotlin.math.abs

private val lines = File("input/puzzle15/input.txt").readLines()

fun String.hash() = this.fold(0) { acc, c -> ((acc + c.code) * 17) % 256 }

fun puzzle15() = lines[0].split(",").sumOf { it.hash() }

fun puzzle15dot1(): Int {
    val boxes = List<MutableList<Pair<String, Int>>>(256) { mutableListOf() }

    lines[0].split(",").forEach {
        val (label, lens) = it.split("[=-]".toRegex())
        val boxNumber = label.hash()

        if (lens.isNotEmpty()) {
            val duplicateIndex = boxes[boxNumber].indexOfFirst { it.first == label }
            if (duplicateIndex > -1)
                boxes[boxNumber][duplicateIndex] = Pair(label, lens.toInt())
            else
                boxes[boxNumber].add(Pair(label, lens.toInt()))
        } else {
            boxes[boxNumber].removeIf { it.first == label }
        }
    }

    return boxes.foldIndexed(0) { iBox, totalFocusingPower, box ->
        totalFocusingPower + box.foldIndexed(0) { iLens, boxFocusingPower, lens ->
            boxFocusingPower + (iBox + 1) * (iLens + 1) * lens.second
        }
    }
}