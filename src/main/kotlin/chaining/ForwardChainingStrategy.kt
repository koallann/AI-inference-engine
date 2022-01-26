package chaining

import Symbol
import common.KnowledgeBaseGraph
import java.util.*

class ForwardChainingStrategy(
    private val graph: KnowledgeBaseGraph,
    private val facts: Set<Symbol>,
) {

    val resolved: MutableSet<Symbol> = HashSet(facts)

    fun canReach(goal: Symbol): Boolean {
        val queue = LinkedList(facts)

        while (!queue.isEmpty()) {
            val p = queue.pop()

            graph.vertices[p]?.forEach { consequent ->
                graph.conjunctions[consequent]?.forEach { conjunction ->
                    if (resolved.containsAll(conjunction)) {
                        resolved += consequent

                        if (consequent == goal) return true
                        else queue.push(consequent)
                    }
                }
            }
        }
        return false
    }

}
