import java.io.File

fun main() {
    val lines = File("inputs/inputDay01").readLines()
    val subLists = createSubLists(lines)
    val sumOfLargestSubList = subLists.maxOf { it.sum() }
    val sumOf3LargestSubLists = subLists.map { it.sum() }
        .sorted()
        .takeLast(3)
        .sum()
    println("Puzzle answer to part 1 is $sumOfLargestSubList")
    println("Puzzle answer to part 2 is $sumOf3LargestSubLists")
}

private fun createSubLists(lines: List<String>): MutableList<MutableList<Int>> {
    val subLists = mutableListOf<MutableList<Int>>()
    subLists.add(mutableListOf())
    for (line in lines) {
        if (line.isEmpty()) {
            subLists.add(mutableListOf())
        } else {
            subLists.last().add(line.toInt())
        }
    }
    return subLists
}
