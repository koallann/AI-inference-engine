class BackwardChainingStrategy(
    private val graph: KnowledgeBaseGraph,
    private val facts: Set<Symbol>,
) {

    fun reach(goal: Symbol): Boolean {
        val visited = HashMap<Symbol, Boolean>()
        val resolved = HashMap<Symbol, Boolean>().also { map ->
            facts.forEach { fact -> map[fact] = true }
        }
        return backward(goal, visited, resolved)
    }

    private fun backward(
        goal: Symbol,
        visited: HashMap<Symbol, Boolean>,
        resolved: HashMap<Symbol, Boolean>,
    ): Boolean {
        if (resolved[goal] == true) return true

        if (visited[goal] == true) return false
        visited[goal] = true

        var goalResolved = false

        for ((vertex, adj) in graph.vertices) {
            if (goal !in adj) continue

            goalResolved = true

            if (!backward(vertex, visited, resolved)) {
                goalResolved = false
                break
            }
        }

        return goalResolved.also { resolved[goal] = it }
    }

}
