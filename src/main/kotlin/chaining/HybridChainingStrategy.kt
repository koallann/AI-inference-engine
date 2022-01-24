package chaining

import Symbol
import common.KnowledgeBaseGraph

class HybridChainingStrategy(
    private val graph: KnowledgeBaseGraph,
    private val onRequestSymbol: (Symbol) -> Boolean
) {

    fun canReach(goal: Symbol): Boolean {
        val backward = BackwardChainingStrategy(graph) { backward, symbol ->
            val response = onRequestSymbol(symbol)
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
