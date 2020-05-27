package graph

class Node {
    var left: Node? = null

    var right: Node? = null

    private var trapezium: Trapezium? = null

    var edge: GEdge? = null

    var median: Double? = null
        private set

    var weight = 0
        private set
    private var id: Int

    fun getTrapezium(): Trapezium? {
        return trapezium
    }

    constructor(trapezium: Trapezium?, weight: Int) {
        this.trapezium = trapezium
        this.weight = weight
        i++
        id = i
    }

    constructor(edge: GEdge?) {
        this.edge = edge
        i++
        id = i
    }

    constructor(median: Double?, weight: Int) {
        this.median = median
        this.weight = weight
        i++
        id = i
    }

    override fun toString(): String {
        var s = ""
        if (edge != null) {
            s += "Node " + id + ": " + edge.toString()
        } else if (trapezium != null) {
            s += trapezium.toString()
        } else {
            s += median.toString()
        }
        return s
    }

    fun shape(): String {
        val shape: Int
        var s = ""
        if (edge != null) {
            s += "\"Node " + id + ": " + edge.toString()
            shape = 1
        } else if (trapezium != null) {
            s += "\"" + trapezium.toString()
            shape = 2
        } else {
            s += "\"" + median.toString()
            shape = 3
        }
        s += "\""
        if (shape == 2) {
            s += "[shape=\"rectangle\"];\n"
        } else if (shape == 3) {
            s += "[shape=\"triangle\"];\n"
        }
        return s
    }

    companion object {
        private var i = 0
    }
}