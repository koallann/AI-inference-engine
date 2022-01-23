package chaining

import Symbol
import common.KnowledgeBaseGraph

class BackwardChainingStrategy(
    private val graph: KnowledgeBaseGraph,
    private val facts: Set<Symbol>,
) {

    fun canReach(goal: Symbol): Boolean {
        val visited = HashMap<Symbol, Boolean>()
        val resolved = HashMap<Symbol, Boolean>().also { map ->
            facts.forEach { fact -> map[fact] = true }
        }
        return backward(goal, visited, resolved)
    }

    private fun backward(
        goal: Symbol,
        visited: MutableMap<Symbol, Boolean>,
        resolved: MutableMap<Symbol, Boolean>,
    ): Boolean {
        if (resolved[goal] == true) return true

        if (visited[goal] == true) return false
        visited[goal] = true

        val premises = mutableSetOf<Symbol>()
        graph.vertices.forEach { (v, adj) -> if (goal in adj) premises.add(v) }
        if (premises.isEmpty()) return false

        val premisesResolved = mutableMapOf<Symbol, Boolean>()
        premises.forEach { premisesResolved[it] = backward(it, visited, resolved) }

        graph.conjunctions[goal]?.forEach { conjunction ->
            val allPremisesResolved = conjunction.all { premise -> premisesResolved[premise] == true }
            if (allPremisesResolved) {
                resolved[goal] = true
                return true
            }
        }

        return false
    }

}
