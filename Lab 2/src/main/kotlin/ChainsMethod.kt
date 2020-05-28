class ChainsMethod(
    private val graph: Graph,
    private val node: GraphNode
) {
    val chains by lazy {
        val arrChains = mutableListOf<MutableList<GraphNode>>()
        val edgesOut = edgeWeight()

        for (i in edgesOut.last().indices) {
            createChain(arrChains, 0, i)
        }

        createChainsRecursive(arrChains, edgesOut, graph.nodes.size - 1, 0)

        arrChains
    }

    val chainsBetween by lazy {
        var result = emptyList<GraphNode>() to emptyList<GraphNode>()
        if (isTopBottom(node)) {
            result
        } else {
            val searched = searchStripes(node)
            if (searched != null)
                result = searched
            result
        }
    }

    private fun edgeWeight(): Array<MutableList<Int>> {
        val edgesIn = Array<MutableList<Int>>(graph.nodes.size) { mutableListOf() }
        val edgesOut = Array<MutableList<Int>>(graph.nodes.size) { mutableListOf() }

        edgesInOut(edgesIn, edgesOut)

        upIteration(edgesIn, edgesOut)
        downIteration(edgesIn, edgesOut)

        return edgesOut
    }

    private fun edgesInOut(edgesIn: Array<MutableList<Int>>, edgesOut: Array<MutableList<Int>>) {
        for (i in graph.edges.indices) {
            if (graph.edges[i].from.dot.y < graph.edges[i].to.dot.y
                || (graph.edges[i].from.dot.y == graph.edges[i].to.dot.y
                        && graph.edges[i].from.dot.x > graph.edges[i].to.dot.x)
            ) {
                edgesIn[getIndex(graph.edges[i].from)].add(i)
                edgesOut[getIndex(graph.edges[i].to)].add(i)
            } else {
                edgesIn[getIndex(graph.edges[i].to)].add(i)
                edgesOut[getIndex(graph.edges[i].from)].add(i)

                graph.edges[i].swap()
            }
            graph.edges[i].weight = 1
        }
    }

    private fun upIteration(edgesIn: Array<MutableList<Int>>, edgesOut: Array<MutableList<Int>>) {
        for (i in 1 until graph.nodes.size - 1) {
            val weightSumIn = weightSum(edgesIn[i])
            val weightSumOut = edgesOut[i].size

            val leftest = leftestOut(edgesOut[i])
            if (leftest == -1) {
                continue
            }

            if (weightSumIn > weightSumOut) {
                graph.edges[leftest].weight = weightSumIn - weightSumOut + 1
            }
        }
    }

    private fun downIteration(edgesIn: Array<MutableList<Int>>, edgesOut: Array<MutableList<Int>>) {
        for (i in graph.nodes.size - 2 downTo 1) {
            val weightSumOut = weightSum(edgesOut[i])
            val weightSumIn = weightSum(edgesIn[i])

            val leftest = leftestIn(edgesIn[i])
            if (leftest == -1) {
                continue
            }

            if (weightSumOut > weightSumIn) {
                val weight = graph.edges[leftest].weight
                graph.edges[leftest].weight = weight + weightSumOut - weightSumIn
            }
        }
    }

    private fun weightSum(weight: List<Int>): Int {
        var sum = 0
        for (i in weight) {
            sum += graph.edges[i].weight
        }
        return sum
    }

    private fun leftestOut(list: List<Int>): Int {
        var leftest = -1

        for (i in list) {
            if (leftest == -1 || graph.edges[i].to.dot.x < graph.edges[leftest].to.dot.x) {
                leftest = i
            }
        }

        return leftest
    }

    private fun leftestIn(list: List<Int>): Int {
        var leftest = -1
        for (i in list) {
            if (leftest == -1 || graph.edges[i].from.dot.x < graph.edges[leftest].from.dot.x) {
                leftest = i
            }
        }

        return leftest
    }

    private fun createChain(arrChains: MutableList<MutableList<GraphNode>>, id: Int, shift: Int) {
        arrChains.add(id + shift, mutableListOf())
        for (i in arrChains[id]) {
            if (!arrChains[id + shift].contains(i)) {
                arrChains[id + shift].add(i)
            }
        }
    }

    private fun createChainsRecursive(
        arrChains: MutableList<MutableList<GraphNode>>,
        edgesOut: Array<MutableList<Int>>,
        node: Int,
        id: Int
    ): Int {
        var chainId = id
        val list = edgesLeftToRight(edgesOut[node])
        val nodes = graph.nodes
        nodes.sortBy { it.dot.y }

        if (list.isEmpty()) {
            arrChains[chainId].add(nodes[node])
        }

        for (i in list.indices) {
            if (i > 0) {
                chainId++
            }

            arrChains[chainId].add(nodes[node])
            val nextNode =
                if (getIndex(graph.edges[list[i]].from) == node) {
                    getIndex(graph.edges[list[i]].to)
                } else {
                    getIndex(graph.edges[list[i]].from)
                }

            if (edgesOut[nextNode].size > 1) {
                for (j in 1 until edgesOut[nextNode].size) {
                    createChain(arrChains, chainId, j)
                }
            }
            chainId = createChainsRecursive(arrChains, edgesOut, nextNode, chainId)
        }

        return chainId
    }

    private fun edgesLeftToRight(edgesId: MutableList<Int>): MutableList<Int> {
        for (i in edgesId) {
            for (j in i + 1 until edgesId.size) {
                if (getSide(
                        graph.edges[edgesId[i]].from,
                        graph.edges[edgesId[i]].to,
                        graph.edges[edgesId[j]].to
                    ) < 0
                ) {
                    val tmp = edgesId[j]
                    edgesId[j] = edgesId[i]
                    edgesId[i] = tmp
                }
            }
        }

        return edgesId
    }

    private fun getIndex(node: GraphNode): Int {
        val nodes = graph.nodes
        nodes.sortBy { it.dot.y }
        for (i in nodes.indices) {
            if (nodes[i] == node) {
                return i
            }
        }
        return -1
    }

    private fun isTopBottom(node: GraphNode): Boolean {
        val nodes = graph.nodes
        nodes.sortBy { it.dot.y }
        if (node.dot.y > nodes.last().dot.y) {
            println("Point is out of graph on the top.")
            return true
        }
        if (node.dot.y < nodes[0].dot.y) {
            println("Point is out of graph on the bottom.")
            return true
        }
        return false
    }

    private fun searchStripes(point: GraphNode): Pair<List<GraphNode>, List<GraphNode>>? {
        val leftEdge = searchEdge(point, chains[0])
        var side = getSide(leftEdge.from, leftEdge.to, point)
        if (side < 0) {
            println("Point is out of graph on the left.")
            return null
        } else if (side == 0) {
            if (point == leftEdge.from || point == leftEdge.to) {
                println("Point is graph's point.")
                return null
            }
            println("Point is on graph's edge ${leftEdge.from} ${leftEdge.to}.")
            return null
        }

        val rightEdge = searchEdge(point, chains.last())
        side = getSide(rightEdge.from, rightEdge.to, point)
        if (side > 0) {
            println("Point is out of graph on the right.")
            return null
        } else if (side == 0) {
            if (point == rightEdge.from || point == rightEdge.to) {
                println("Point is graph's point.")
                return null
            }
            println("Point is on graph's edge ${leftEdge.from} ${leftEdge.to}.")
            return null
        }

        var l = 0
        var r = chains.size - 1
        var mid: Int
        var edge: Edge

        while (r - l > 1) {
            mid = l + (r - l) / 2
            edge = searchEdge(point, chains[mid])
            side = getSide(edge.from, edge.to, point)
            println("Edge ${leftEdge.from} ${leftEdge.to}.")
            when {
                side < 0 -> {
                    r = mid
                }
                side > 0 -> {
                    l = mid
                }
                else -> {
                    if (point == rightEdge.from || point == rightEdge.to) {
                        println("Point is graph's point.")
                        return null
                    }
                    println("Point is on graph's edge ${leftEdge.from} ${leftEdge.to}.")
                    return null
                }
            }
        }

        return chains[l] to chains[r]
    }

    private fun searchEdge(point: GraphNode, chain: List<GraphNode>): Edge {
        var l = 0
        var r = chain.size - 1
        var mid: Int

        while (r - l > 1) {
            mid = (l + r) / 2
            if (point.dot.y > chain[mid].dot.y) {
                r = mid
            } else {
                l = mid
            }
        }

        return Edge(chain[l], chain[r])
    }


    private fun getSide(a: GraphNode, b: GraphNode, c: GraphNode): Int {
        val distance = getRotation(a, b, c)

        if (distance > 0)
            return 1
        if (distance < 0)
            return -1
        return 0
    }


    private fun getRotation(a: GraphNode, b: GraphNode, c: GraphNode) =
        (c.dot.y - a.dot.y) * (b.dot.x - a.dot.x) - (b.dot.y - a.dot.y) * (c.dot.x - a.dot.x)
}