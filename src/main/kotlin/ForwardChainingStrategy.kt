import java.util.*

class ForwardChainingStrategy(
    private val graph: KnowledgeBaseGraph,
    private val facts: Set<Symbol>,
) {

    fun reach(goal: Symbol): Boolean {
        val solveCount = graph.inDegree.toMutableMap()
        val inferred = HashMap<Symbol, Boolean>()
        val queue = LinkedList(facts)

        while (!queue.isEmpty()) {
            val p = queue.pop()
            if (p == goal) return true

            if (inferred[p] == true) continue
            inferred[p] = true

            for ((vertex, adj) in graph.vertices) {
                if (p != vertex) continue

                adj.forEach { conclusion ->
                    solveCount[conclusion] = solveCount[conclusion]!! - 1
                    if (solveCount[conclusion] == 0) queue.push(conclusion)
                }
            }
        }
        return false
    }

}
