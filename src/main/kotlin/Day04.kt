import java.io.File

fun main() {
    val lines = File("inputs/inputDay04").readLines()
    val ranges = lines.map {
        val splits = it.split(",")
        Pair(
            splits[0],
            splits[1]
        )
    }.map { rangeString ->
        val firstSplits = rangeString.first.split("-").map { it.toInt() }
        val secondSplits = rangeString.second.split("-").map { it.toInt() }
        Pair(
            (firstSplits[0]..firstSplits[1]).toSet(),
            (secondSplits[0]..secondSplits[1]).toSet()
        )
    }
    val sectionsContained = ranges.count { it.first.containsAll(it.second) || it.second.containsAll(it.first) }
    val sectionsOverlapping = ranges.count { it.first.intersect(it.second).isNotEmpty() }
    println("Puzzle answer to part 1 is $sectionsContained")
    println("Puzzle answer to part 2 is $sectionsOverlapping")
}
