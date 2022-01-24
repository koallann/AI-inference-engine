import chaining.HybridChainingStrategy
import common.KnowledgeBaseGraph
import java.io.File
import java.util.*
import kotlin.system.exitProcess

class Main {
    companion object {

        private val PATTERN_INPUT_LINE = Regex("^(\\w+,)*\\w+->\\w+$")

        @JvmStatic
        fun main(args: Array<String>) {
            // checking execution args

            if (args.size != 1) {
                println("Invalid args, just pass the path of input file")
                exitProcess(1)
            }

            // checking the input file

            val inputFile = File(args[0])
            if (!inputFile.isFile) {
                println("Invalid path to input file")
                exitProcess(1)
            }

            // reading the input file and requesting the goal

            val graph = KnowledgeBaseGraph()

            inputFile.bufferedReader().use { input ->
                var line: String? = input.readLine()

                while (line != null && line.matches(PATTERN_INPUT_LINE)) {
                    val split = line.split("->")
                    val premises = split[0].split(",")
                    val conclusion = split[1]

                    graph.addEdges(premises.toSet(), conclusion)
                    line = input.readLine()
                }
            }

            println("Type the goal:")
            val scanner = Scanner(System.`in`)
            val goal = scanner.nextLine()

            // running Hybrid Chaining

            val hybridChaining = HybridChainingStrategy(graph) { symbol ->
                println("=> $symbol ? (type \"yes\" if true)")
                scanner.nextLine() == "yes"
            }

            if (hybridChaining.canReach(goal)) {
                println("The goal \"$goal\" is reachable.")
            } else {
                println("The goal \"$goal\" is not reachable.")
            }
        }

    }
}
