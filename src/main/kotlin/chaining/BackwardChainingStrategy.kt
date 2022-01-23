package chaining

import Symbol
import common.KnowledgeBaseGraph

class BackwardChainingStrategy(
    private val graph: KnowledgeBaseGraph,
    private val facts: Set<Symbol>,
) {

    fun canReach(goal: Symbol): Boolean {
        val visited = HashSet<Symbol>(facts)
        val resolved = HashSet<Symbol>(facts)
        return backward(goal, visited, resolved)
    }

    private fun backward(
        goal: Symbol,
        visited: MutableSet<Symbol>,
        resolved: MutableSet<Symbol>,
    ): Boolean {
        if (goal in resolved) return true

        if (goal in visited) return false
        visited += goal

        val premises = graph.vertices.filter { goal in it.value }.keys
        if (premises.isEmpty()) return false

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
