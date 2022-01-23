import chaining.BackwardChainingStrategy
import chaining.ForwardChainingStrategy
import common.KnowledgeBaseGraph
import java.io.BufferedReader
import java.io.File
import kotlin.system.exitProcess

class Main {
    companion object {

        private val PATTERN_KB_LINE = Regex("^(\\w+,)*\\w+->\\w+$")
        private val PATTERN_FACTS_LINE = Regex("^(\\w+,)*\\w+$")
        private val PATTERN_GOAL_LINE = Regex("^\\w+$")

        @JvmStatic
        fun main(args: Array<String>) {
            if (args.size != 1) {
                println("Invalid args, just pass the path of input file")
                exitProcess(1)
            }

            val input = File(args[0])
            if (!input.isFile) {
                println("Invalid path to input file")
                exitProcess(1)
            }

            var graph: KnowledgeBaseGraph? = null
            var facts: Set<Symbol>? = null
            var goal: Symbol? = null

            input.bufferedReader().use {
                try {
                    graph = readKnowledgeBase(it)
                    facts = readFacts(it)
                    goal = readGoal(it)
                } catch (e: Exception) {
                    println(e.localizedMessage)
                    exitProcess(1)
                }
            }

            runChaining(graph!!, facts!!, goal!!)
        }

        private fun runChaining(graph: KnowledgeBaseGraph, facts: Set<Symbol>, goal: Symbol) {
            val forward = ForwardChainingStrategy(graph, facts)
            val backward = BackwardChainingStrategy(graph, facts)

            println("Forward Chaining: ${forward.canReach(goal)}")
            println("Backward Chaining: ${backward.canReach(goal)}")
        }

        private fun readKnowledgeBase(input: BufferedReader): KnowledgeBaseGraph {
            if (input.readLine() != "KNOWLEDGE BASE")
                throw IllegalStateException("Invalid file, exiting...")

            val graph = KnowledgeBaseGraph()

            var line: String?
            do {
                line = input.readLine()

                if (line != null && line.matches(PATTERN_KB_LINE)) {
                    val split = line.split("->")
                    val premises = split[0].split(",")
                    val conclusion = split[1]

                    graph.addEdges(premises.toSet(), conclusion)
                }
            } while (line != null && line.isNotBlank())

            return graph
        }

        private fun readFacts(input: BufferedReader): Set<Symbol> {
            if (input.readLine() != "FACTS")
                throw IllegalStateException("Invalid file, exiting...")

            val facts = hashSetOf<Symbol>()
            val line = input.readLine()

            return if (line?.matches(PATTERN_FACTS_LINE) == true) {
                facts += line.split(",")
                input.readLine()
                facts
            } else {
                throw IllegalStateException("Invalid file, exiting...")
            }
        }

        private fun readGoal(input: BufferedReader): Symbol {
            if (input.readLine() != "GOAL")
                throw IllegalStateException("Invalid file, exiting...")

            val line = input.readLine()

            return if (line?.matches(PATTERN_GOAL_LINE) == true) line
            else throw IllegalStateException("Invalid file, exiting...")
        }

    }
}
