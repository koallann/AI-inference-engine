package chaining

import Symbol
import common.KnowledgeBaseGraph
import java.util.*

class HybridChainingStrategy(private val graph: KnowledgeBaseGraph) {

    private val scanner = Scanner(System.`in`)

    fun canReach(goal: Symbol): Boolean {
        val backward = BackwardChainingStrategy(graph) { backward, symbol ->
            println("=> $symbol ? Type yes/no")
            val response = scanner.nextLine() == "yes"

            if (response) {
                val facts = mutableSetOf(symbol)
                facts += backward.resolved

                val forward = ForwardChainingStrategy(graph, facts)
                forward.canReach(goal)

                backward.resolved += forward.resolved
            }
            response
        }
        return backward.canReach(goal)
    }

}
