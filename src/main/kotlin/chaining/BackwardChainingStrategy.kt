package chaining

import Symbol
import common.KnowledgeBaseGraph

class BackwardChainingStrategy(
    private val graph: KnowledgeBaseGraph,
    private val onRequestSymbol: (BackwardChainingStrategy, Symbol) -> Boolean
) {

    val resolved: MutableSet<Symbol> = HashSet()

    fun canReach(goal: Symbol): Boolean {
        resolved.clear()
        return backward(goal, HashSet(), resolved)
    }

    private fun backward(
        goal: Symbol,
        visited: MutableSet<Symbol>,
        resolved: MutableSet<Symbol>,
    ): Boolean {
        if (goal in resolved) return true

        if (goal in visited) return false
        visited += goal

        val conjunctions = graph.conjunctions[goal]
        if (conjunctions.isNullOrEmpty()) {
            val response = onRequestSymbol(this, goal)
            if (response) resolved += goal
            return response
        }

        conjunctions.forEach { conjunction ->
            val conjunctionResolved = conjunction.all { backward(it, visited, resolved) }
            if (conjunctionResolved) {
                resolved += goal
                return true
            }
        }

        return false
    }

}
