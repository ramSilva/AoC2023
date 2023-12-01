package puzzles

import java.io.File
import kotlin.math.abs

private val lines = File("input/puzzle15/input.txt").readLines()

fun Pair<Int, Int>.distance(other: Pair<Int, Int>): Int {
    return abs(this.first - other.first) + abs(this.second - other.second)
}

fun puzzle15(): Int {
    var minX = Int.MAX_VALUE
    var maxX = Int.MIN_VALUE
    var minY = Int.MAX_VALUE
    var maxY = Int.MIN_VALUE
    val beacons = mutableSetOf<Pair<Int, Int>>()
    val sensorsDistance = lines.map {
        val (sensor, beacon) = "x=(-?\\d+).*y=(-?\\d+).*x=(-?\\d+).*y=(-?\\d+)".toRegex()
            .find(it)!!.destructured.toList().chunked(2)
            .map { Pair(it.first().toInt(), it.last().toInt()) }

        beacons.add(beacon)
        Pair(sensor, sensor.distance(beacon)).also {
            if (sensor.first - it.second < minX) minX = sensor.first - it.second
            if (sensor.first + it.second > maxX) maxX = sensor.first + it.second
            if (sensor.second - it.second < minY) minY = sensor.second - it.second
            if (sensor.second + it.second > maxY) maxY = sensor.second + it.second
        }
    }

    val yToCheck = 2000000
    var impossibleBeacon = 0
    for (x in minX..maxX) {
        val currentPos = Pair(x, yToCheck)

        if (sensorsDistance.any {
                currentPos.distance(it.first) <= it.second && !beacons.contains(currentPos)
            }) {
            impossibleBeacon++
        }
    }

    return impossibleBeacon
}

fun puzzle15dot1(): Long {
    val yList = hashMapOf<Int, MutableList<Pair<Int, Int>>>()
    lines.forEach {
        val (sensor, beacon) = "x=(-?\\d+).*y=(-?\\d+).*x=(-?\\d+).*y=(-?\\d+)".toRegex()
            .find(it)!!.destructured.toList().chunked(2)
            .map { Pair(it.first().toInt(), it.last().toInt()) }

        val distance = sensor.distance(beacon)
        for (y in sensor.second - distance..sensor.second + distance) {
            val offset = abs(sensor.second - y)
            if (yList[y] == null) {
                yList[y] = mutableListOf()
            }
            yList[y]!!.add(
                Pair(
                    sensor.first - (distance - offset),
                    sensor.first + (distance - offset)
                )
            )
        }
    }

    yList.filter { (k, _) -> k in 0..4000000 }.forEach { (k, v) ->
        v.sortedBy {
            it.first
        }.reduce { acc, pair ->
            if (pair.first <= acc.second + 1 && pair.second >= acc.second) {
                return@reduce Pair(acc.first, pair.second)
            } else if(pair.first > acc.second && pair.first <= 4000000){
                println("${(pair.first - 1).toLong() * 4000000.toLong() + k.toLong()}")
                return@forEach
            }

            acc
        }
    }

    return -1
}