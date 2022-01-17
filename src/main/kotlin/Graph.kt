class Graph {

    private val _vertices: HashMap<Symbol, MutableSet<Symbol>> = hashMapOf()
    val vertices: Map<Symbol, Set<Symbol>> = _vertices

    private val _inDegree: HashMap<Symbol, Int> = hashMapOf()
    val inDegree: Map<Symbol, Int> = _inDegree

    fun addEdge(from: Symbol, to: Symbol) {
        initVertexIfNeeded(from)
        initVertexIfNeeded(to)
        _vertices[from]!!.add(to)
        _inDegree[to] = _inDegree[to]!! + 1
    }

    private fun initVertexIfNeeded(symbol: Symbol) {
        if (_vertices[symbol] == null) {
            _vertices[symbol] = mutableSetOf()
        }
        if (_inDegree[symbol] == null) {
            _inDegree[symbol] = 0
        }
    }

}
