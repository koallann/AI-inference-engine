package common

import Symbol

class KnowledgeBaseGraph {

    private val _vertices: HashMap<Symbol, MutableSet<Symbol>> = hashMapOf()
    val vertices: Map<Symbol, Set<Symbol>> = _vertices

    private val _inDegree: HashMap<Symbol, Int> = hashMapOf()
    val inDegree: Map<Symbol, Int> = _inDegree

    private val _conjunctions: MutableMap<Symbol, MutableList<Set<Symbol>>> = hashMapOf()
    val conjunctions: Map<Symbol, List<Set<Symbol>>> = _conjunctions

    fun addEdges(premises: Set<Symbol>, conclusion: Symbol) {
        premises.forEach { initVertexIfNeeded(it) }
        initVertexIfNeeded(conclusion)

        premises.forEach {
            if (_vertices[it]!!.contains(conclusion)) return@forEach

            _vertices[it]!!.add(conclusion)
            _inDegree[conclusion] = _inDegree[conclusion]!! + 1
        }

        initConjunctionsIfNeeded(conclusion)
        _conjunctions[conclusion]!!.add(premises)
    }

    private fun initVertexIfNeeded(symbol: Symbol) {
        if (_vertices[symbol] == null) {
            _vertices[symbol] = mutableSetOf()
        }
        if (_inDegree[symbol] == null) {
            _inDegree[symbol] = 0
        }
    }

    private fun initConjunctionsIfNeeded(symbol: Symbol) {
        if (_conjunctions[symbol] == null) {
            _conjunctions[symbol] = arrayListOf()
        }
    }

}
