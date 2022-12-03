import java.io.File

fun main() {
    val lines = File("inputs/inputDay03").readLines()
    val compartments = lines.map { Pair(it.substring(0, it.length / 2), it.substring(it.length / 2, it.length)) }
    val priorities = compartments.map { it.first.toSet().intersect(it.second.toSet()).joinToString("") }
        .map { toPriority(it[0]) }

    val groupedByBadges = lines
        .chunked(3)
        .map { chunk -> chunk.map { it.toSet() } }
        .map { it[0].intersect(it[1]).intersect(it[2]) }
        .map { toPriority(it.toList()[0]) }

    println("Puzzle answer to part 1 is ${priorities.sum()}")
    println("Puzzle answer to part 2 is ${groupedByBadges.sum()}")
}

fun toPriority(c: Char): Int {
    val lowerCaseToPriority = ('a'..'z').mapIndexed { index, lc -> lc to (1..26).toList()[index] }.toMap()
    val upperCaseToPriority = ('A'..'Z').mapIndexed { index, uc -> uc to (27..52).toList()[index] }.toMap()
    return lowerCaseToPriority.plus(upperCaseToPriority)[c]!!
}