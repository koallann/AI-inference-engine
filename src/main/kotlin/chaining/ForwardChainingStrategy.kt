package chaining

import Symbol
import common.KnowledgeBaseGraph
import java.util.*

class ForwardChainingStrategy(
    private val graph: KnowledgeBaseGraph,
    private val facts: Set<Symbol>,
) {

    fun canReach(goal: Symbol): Boolean {
        val resolved = HashSet(facts)
        val queue = LinkedList(facts)

        while (!queue.isEmpty()) {
            val p = queue.pop()
            if (p == goal) return true

            graph.vertices[p]?.forEach { consequent ->
                graph.conjunctions[consequent]?.forEach { conjunction ->
                    if (resolved.containsAll(conjunction)) {
                        resolved += consequent
                        queue.push(consequent)
                    }
                }
            }
        }
        return false
    }

}
