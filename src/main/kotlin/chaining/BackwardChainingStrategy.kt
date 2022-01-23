package chaining

import Symbol
import common.KnowledgeBaseGraph

class BackwardChainingStrategy(
    private val graph: KnowledgeBaseGraph,
    private val requestSymbol: (BackwardChainingStrategy, Symbol) -> Boolean
) {

    val resolved: MutableSet<Symbol> = HashSet()

    fun canReach(goal: Symbol): Boolean =
        backward(goal, HashSet(), resolved)

    private fun backward(
        goal: Symbol,
        visited: MutableSet<Symbol>,
        resolved: MutableSet<Symbol>,
    ): Boolean {
        if (goal in resolved) return true

        if (goal in visited) return false
        visited += goal

        val premises = graph.vertices.filter { goal in it.value }.keys
        if (premises.isEmpty()) {
            val response = requestSymbol(this, goal)
            if (response) resolved += goal
            return response
        }

        val premisesResolved = premises.filter { backward(it, visited, resolved) }

        graph.conjunctions[goal]?.forEach { conjunction ->
            if (premisesResolved.containsAll(conjunction)) {
                resolved += goal
                return true
            }
        }

        return false
    }

}
