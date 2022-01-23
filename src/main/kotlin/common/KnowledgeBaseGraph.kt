package common

import Symbol

class KnowledgeBaseGraph {

    private val _vertices: HashMap<Symbol, MutableSet<Symbol>> = hashMapOf()
    val vertices: Map<Symbol, Set<Symbol>> = _vertices

    private val _conjunctions: MutableMap<Symbol, MutableList<Set<Symbol>>> = hashMapOf()
    val conjunctions: Map<Symbol, List<Set<Symbol>>> = _conjunctions

    fun addEdges(premises: Set<Symbol>, conclusion: Symbol) {
        premises.forEach { initVertexIfNeeded(it) }
        initVertexIfNeeded(conclusion)
        initConjunctionsIfNeeded(conclusion)

        premises.forEach { _vertices[it]!!.add(conclusion) }
        _conjunctions[conclusion]!!.add(premises)
    }

    private fun initVertexIfNeeded(symbol: Symbol) {
        if (_vertices[symbol] == null) {
            _vertices[symbol] = mutableSetOf()
        }
    }

    private fun initConjunctionsIfNeeded(symbol: Symbol) {
        if (_conjunctions[symbol] == null) {
            _conjunctions[symbol] = arrayListOf()
        }
    }

}
