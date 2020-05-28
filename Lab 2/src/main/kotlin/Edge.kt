class Edge(var from: GraphNode, var to: GraphNode) {
    var weight = 0

    @Override
    override fun equals(other: Any?): Boolean {
        if (other is Edge)
            if (this.from == other.from && this.to == other.to) {
                return true
            }
        return false
    }

    fun swap() {
        val tmp = from
        from = to
        to = tmp
    }
}