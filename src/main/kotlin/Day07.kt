import java.io.File
import java.util.Stack

fun main() {
    val lines = File("inputs/inputDay07").readLines()
    val directories = assignFilesToDirectories(lines)
    val totalSizes = directories.map { Pair(it.key, it.value.sumOf { it.second }) }.sortedBy { it.second }
    val availableSpace = 70000000 - totalSizes.maxOf { it.second }
    val neededSpace = 30000000 - availableSpace

    val totalSizesOfDirectories = directories.map { it.value }
        .map { it.sumOf { file -> file.second } }

    val smallestDirectoryToDelete = totalSizesOfDirectories
        .sorted()
        .first { it > neededSpace }

    val sumOfDirectoriesLargerThan100000 = totalSizesOfDirectories
        .filter { it <= 100000 }
        .sum()

    println("Puzzle answer to part 1 is $sumOfDirectoriesLargerThan100000")
    println("Puzzle answer to part 2 is $smallestDirectoryToDelete")
}

private fun assignFilesToDirectories(lines: List<String>): Map<String, MutableSet<Pair<String, Int>>> {
    val currentDirectory = Stack<String>()
    val directories = mutableMapOf<String, MutableSet<Pair<String, Int>>>()
    var path = ""
    for (line in lines) {
        when {
            line == "$ cd .." -> {
                val directoryName = currentDirectory.pop()
                val oldPath = path
                path = path.substringBeforeLast("/$directoryName")
                if (directories[path] == null) {
                    directories[path] = directories[oldPath]!!
                } else {
                    directories[path]!!.addAll(directories[oldPath]!!)
                }
            }

            line.startsWith("$ cd") -> {
                val directoryName = line.split(" ").last()
                currentDirectory.push(directoryName)
                path += "/$directoryName"
            }

            line[0].isDigit() -> {
                val fileSize = line.split(" ").first().toInt()
                if (directories[path] == null) {
                    directories[path] = mutableSetOf(Pair(path, fileSize))
                } else {
                    directories[path]!!.add(Pair(path, fileSize))
                }
            }
        }
    }

    // empty stack (no return to root directory)
    while (currentDirectory.isNotEmpty()) {
        val directoryName = currentDirectory.pop()
        val oldPath = path
        path = path.substringBefore("/$directoryName")
        if (directories[path] == null) {
            directories[path] = directories[oldPath]!!
        } else {
            directories[path]!!.addAll(directories[oldPath]!!)
        }

    }
    return directories
}
