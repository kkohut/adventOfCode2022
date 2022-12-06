import java.io.File

fun main() {
    fun firstStartIndexOfDistinctPacket(input: String, packetLength: Int): Int {
        return input.windowed(packetLength)
            .map { it.toSet() }
            .indexOfFirst { it.size == packetLength }
            .let { it + packetLength}
    }
    
    val dataStream = File("inputs/inputDay06").readLines()[0]
    println("Puzzle answer to part 1 is ${firstStartIndexOfDistinctPacket(input = dataStream, packetLength = 4)}")
    println("Puzzle answer to part 2 is ${firstStartIndexOfDistinctPacket(input = dataStream, packetLength = 14)}")
}

