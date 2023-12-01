package puzzles

import java.io.File

private val lines = File("input/puzzle7/input.txt").readLines()

fun puzzle7() = Pair(mutableMapOf<String, Int>(), mutableListOf("/")).also { (sizes, openFolders) -> lines.forEach { if (it.contains("$ cd")) { Regex("\\$ cd (.+)").find(it)!!.groupValues[1].also { dest -> if (dest == "..") openFolders.removeAt(openFolders.size - 1) else if (dest == "/") { openFolders.removeIf { it != "/" } } else openFolders.add("${openFolders.last()}/$dest") } } else if (it[0].isDigit()) { Regex("([0-9]+) .*").find(it)!!.groupValues[1].let { size -> openFolders.forEach { folder -> sizes[folder] = size.toInt() + (sizes[folder] ?: 0) }}}}}.let{(sizes, _) -> "pt1: ${ sizes.values.filter { it <= 100000 }.sum()}, pt2: ${sizes.values.filter { it >= (sizes["/"]!! - 40000000) }.minOrNull()}" }

fun puzzle7ButLegible() {
    val sizes = mutableMapOf<String, Int>()
    val openFolders = mutableListOf("/")
    lines.forEach {
        if (it.contains("$ cd")) {
            when (val dest = Regex("\\$ cd (.+)").find(it)!!.groupValues[1]) {
                ".." -> openFolders.removeLast()
                "/" -> openFolders.removeIf { it != "/" }
                else -> openFolders.add("${openFolders.last()}/$dest")
            }

        }
        if (it[0].isDigit()) {
            val size = Regex("([0-9]+) .*").find(it)!!.groupValues[1]
            openFolders.forEach { folder -> sizes[folder] = size.toInt() + (sizes[folder] ?: 0) }
        }
    }

    println(sizes.values.filter { it <= 100000 }.sum())

    val target = 40000000
    val current = sizes["/"]!!

    val diff = current - target
    println(sizes.values.filter { it >= diff }.minOrNull())
}