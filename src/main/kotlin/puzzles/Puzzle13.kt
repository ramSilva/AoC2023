package puzzles

import com.google.gson.Gson
import java.io.File

private val lines = File("input/puzzle13/input.txt").readLines()

fun puzzle13(): Int {
    val ordered = mutableListOf<Int>()
    var index = 0
    lines.filter { it.isNotEmpty() }.map {
        Gson().fromJson(it, List::class.java)
    }.chunked(2).forEach {
        index++
        if (isOrdered(it.first(), it.last()) == Result.ORDERED) {
            ordered.add(index)
        }
    }

    return ordered.sum()
}

fun puzzle13dot1(): Int {
    val ordered = mutableListOf<Any>()
    val unordered = lines.filter { it.isNotEmpty() }.map {
        Gson().fromJson(it, List::class.java)
    }.toMutableList()

    val dividers = Pair(arrayListOf(arrayListOf(2.0)), arrayListOf(arrayListOf(6.0)))
    unordered.add(dividers.first)
    unordered.add(dividers.second)

    while (true) {
        if (unordered.size == 0) break

        var currentMin = unordered[0]
        unordered.forEach {
            if (isOrdered(it, currentMin) == Result.ORDERED) {
                currentMin = it
            }
        }
        unordered.remove(currentMin)
        ordered.add(currentMin)
    }

    return (ordered.indexOf(dividers.first) + 1) * (ordered.indexOf(dividers.second) + 1)
}

enum class Result {
    ORDERED,
    NON_ORDERED,
    INCONCLUSIVE
}

fun isOrdered(list1: List<*>, list2: List<*>): Result {
    var i = 0
    while (true) {
        if (i >= list1.size && i >= list2.size) return Result.INCONCLUSIVE

        if (i >= list1.size) return Result.ORDERED
        if (i >= list2.size) return Result.NON_ORDERED

        val element1 = list1[i]
        val element2 = list2[i]

        if (element1 is Double && element2 is Double) {
            if (element1 < element2) return Result.ORDERED
            if (element2 < element1) return Result.NON_ORDERED
        }
        if (element1 is List<*> && element2 is List<*>) {
            val result = isOrdered(element1, element2)
            if (result != Result.INCONCLUSIVE) return result
        }
        if (element1 is List<*> && element2 is Double) {
            val result = isOrdered(element1, listOf(element2))
            if (result != Result.INCONCLUSIVE) return result
        }
        if (element1 is Double && element2 is List<*>) {
            val result = isOrdered(listOf(element1), element2)
            if (result != Result.INCONCLUSIVE) return result
        }

        i++
    }
}

