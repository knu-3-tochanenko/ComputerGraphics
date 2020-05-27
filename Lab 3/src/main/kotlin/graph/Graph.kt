package graph


import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.util.*
import java.util.function.ToDoubleFunction
import kotlin.collections.LinkedHashSet

class Graph(points: Array<Point?>, matrix: Array<BooleanArray>) {
    private val vertexes //sorted by y coordinate
            : MutableList<GVertex>
    private val edges: MutableList<GEdge>
    private val N: Int
    private val trapezium: Trapezium
    var myRoot: Node
    fun buildTrapezium(V: List<GVertex>, E: MutableList<GEdge>, T: Trapezium?): Node {
        if (V.size == 0) {
            //System.out.println(T);
            return Node(T, 0) //leaf
        }
        val Edg: MutableList<MutableList<GEdge>> =
            ArrayList<MutableList<GEdge>>() //edges of trapezium
        val Vert: MutableList<MutableList<GVertex>> =
            ArrayList<MutableList<GVertex>>() //vertexes of trapezium sorted by y-coordinate
        val U: MutableList<MutableList<Node>> =
            ArrayList<MutableList<Node>>() //list of trapeziums
        val Tr: Array<Trapezium?> = arrayOfNulls<Trapezium>(2)
        var weight = 0
        for (i in 0..1) {
            Edg.add(ArrayList<GEdge>())
            Vert.add(ArrayList<GVertex>())
            U.add(ArrayList<Node>())
            Tr[i] = Trapezium()
        }
        /*Trapezium T1 = new Trapezium();
        Trapezium T2 = new Trapezium();*/
        val yMed: Double = V[(V.size - 1) / 2]!!.y //mediana
        Tr[0]!!.minY = T!!.minY
        Tr[0]!!.maxY = yMed
        Tr[1]!!.minY = yMed
        Tr[1]!!.maxY = T!!.maxY
        E.sortWith(Comparator.comparingDouble<GEdge>(ToDoubleFunction<GEdge> { obj: GEdge ->
            obj.middleXInInterval(
                Tr[0]!!.minY,
                Tr[0]!!.maxY
            )
        }))
        for (e in E) {
            for (i in 0..0) {
                val `in`: Int = Tr[i]!!.edgeBelongs(e)
                //e has end in Tr[i]
                if (`in` == 1) {
                    Edg[i].add(e)
                    if (Tr[i]!!.vertexBelongs(e.getA())) {
                        Vert[i].add(e.getA())
                        weight++
                    }
                    if (Tr[i]!!.vertexBelongs(e.getB())) {
                        Vert[i].add(e.getB())
                        weight++
                    }
                    //remove duplicates
                    val set: Set<GVertex> = LinkedHashSet<GVertex>(Vert[i])
                    Vert[i].clear()
                    Vert[i].addAll(set)
                    Vert[i].sortWith(Comparator.comparingDouble<GVertex>({ it.y }))
                } else if (`in` == 0 || e === Tr[i]!!.right) {
                    Edg[i].add(e)
                    if (Tr[i]!!.left == null) {
                        Tr[i]!!.left = e
                    } else {
                        Tr[i]!!.right = e
                        val n: Node = buildTrapezium(Vert[i], Edg[i], Tr[i])
                        U[i].add(n)
                        if (e !== T!!.right) {
                            U[i].add(Node(e))
                        }
                        Edg[i].clear()
                        Vert[i].clear()
                        Edg[i].add(e)
                        Tr[i] = Trapezium()
                        Tr[0]!!.minY = T!!.minY
                        Tr[0]!!.maxY = yMed
                        Tr[1]!!.minY = yMed
                        Tr[1]!!.maxY = T!!.maxY
                        Tr[i]!!.left = e
                        Tr[i]!!.right = null
                    }
                }
            }
        }
        E.sortWith(Comparator.comparingDouble<GEdge>(ToDoubleFunction<GEdge> { obj: GEdge ->
            obj.middleXInInterval(
                Tr[1]!!.minY,
                Tr[1]!!.maxY
            )
        }))
        for (e in E) {
            for (i in 1..1) {
                val `in`: Int = Tr[i]!!.edgeBelongs(e)
                //e has end in Tr[i]
                if (`in` == 1) {
                    Edg[i].add(e)
                    if (Tr[i]!!.vertexBelongs(e.getA())) {
                        Vert[i].add(e.getA())
                        weight++
                    }
                    if (Tr[i]!!.vertexBelongs(e.getB())) {
                        Vert[i].add(e.getB())
                        weight++
                    }
                    //remove duplicates
                    val set: Set<GVertex> = LinkedHashSet<GVertex>(Vert[i])
                    Vert[i].clear()
                    Vert[i].addAll(set)
                    Vert[i].sortWith(Comparator.comparingDouble<GVertex>({ it.y }))
                } else if (`in` == 0 || e === Tr[i]!!.right) {
                    Edg[i].add(e)
                    if (Tr[i]!!.left == null) {
                        Tr[i]!!.left = e
                    } else {
                        Tr[i]!!.right = e
                        val n: Node = buildTrapezium(Vert[i], Edg[i], Tr[i])
                        U[i].add(n)
                        if (e !== T!!.right) {
                            U[i].add(Node(e))
                        }
                        Edg[i].clear()
                        Vert[i].clear()
                        Edg[i].add(e)
                        Tr[i] = Trapezium()
                        Tr[0]!!.minY = T!!.minY
                        Tr[0]!!.maxY = yMed
                        Tr[1]!!.minY = yMed
                        Tr[1]!!.maxY = T!!.maxY
                        Tr[i]!!.left = e
                        Tr[i]!!.right = null
                    }
                }
            }
        }
        val root = Node(yMed, weight + 1)
        root.left = balance(U[0])
        root.right = balance(U[1])
        return root
    }

    fun balance(U: List<Node>): Node? {
        val edgs: MutableList<Node> = ArrayList<Node>()
        //List<Node> trapeziums = new ArrayList<>();
        val leaves: MutableList<Node> = ArrayList<Node>()
        //int weight = 0;
        for (i in U.indices) {
            if (i % 2 == 0) {
                //trapeziums.add(U.get(i));
                leaves.add(U[i])
                //weight += U.get(i).getWeight();
            } else {
                edgs.add(U[i])
            }
        }

        val root: Node? = balancedGEdgeTree(edgs, 0, edgs.size - 1)
        balancedTree(root, leaves)
        return root
    }

    private fun balancedGEdgeTree(edgs: List<Node>, i: Int, j: Int): Node? {
        if (i == j) {
            return edgs[i]
        }
        if (i > j) {
            return null
        }
        val k = (i + j) / 2
        val n: Node = edgs[k]
        n.left = balancedGEdgeTree(edgs, i, k - 1)
        n.right = balancedGEdgeTree(edgs, k + 1, j)
        return n
    }

    private fun balancedTree(root: Node?, leaves: MutableList<Node>) {
        if (root == null) {
            return
        }
        if (root.left != null && root.right != null) {
            balancedTree(root.left, leaves)
        }
        if (root.left == null) {
            root.left = leaves.removeAt(0)
        }
        if (root.right == null) {
            root.right = leaves.removeAt(0)
            return
        }
        balancedTree(root.right, leaves)
    }

    fun printNodeShapeToFile(root: Node?, file: File?) {
        if (root != null) {
            printNodeShapeToFile(root.left, file)
            try {
                FileWriter(file, true).use { writer ->
                    writer.write(root.shape())
                    writer.flush()
                }
            } catch (ex: IOException) {
                println(ex.message)
            }
            printNodeShapeToFile(root.right, file)
        }
    }

    fun localization(point: Point) {
        localization(myRoot, point)
    }

    fun localization(root: Node?, point: Point) {
        if (root!!.left == null && root.right == null) {
            val t: Trapezium? = root.getTrapezium()
            if (java.lang.Double.compare(t!!.minY, point.y) > 0) {
                println("Point is out of the graph;")
            } else if (java.lang.Double.compare(t.maxY, point.y) < 0) {
                println("Point is out of the graph;")
            } else if (t.left!!.getSide(point) == -1 || t.right!!.getSide(point) == 1) {
                println("Point is out of the graph;")
            } else {
                System.out.println(t)
            }
            return
        } else if (root.edge != null) {
            val edge: GEdge? = root.edge
            if (edge!!.getSide(point) == 0) {
                println("point is on the edge $edge")
                return
            } else if (edge.getSide(point) == -1) {
                localization(root.left, point)
            } else {
                localization(root.right, point)
            }
        } else {
            val vertex: Double = root.median!!
            if (java.lang.Double.compare(vertex, point.y) > 0) {
                localization(root.left, point)
            } else {
                localization(root.right, point)
            }
        }
    }

    init {
        N = points.size
        vertexes = ArrayList<GVertex>(N)
        edges = ArrayList<GEdge>()
        for (p in points) {
            vertexes.add(GVertex(p!!))
        }
        for (i in 0 until N) {
            for (j in i until N) {
                if (matrix[i][j]) {
                    edges.add(GEdge(vertexes[i], vertexes[j]))
                }
            }
        }
        vertexes.sortWith(Comparator.comparingDouble<GVertex>({ it.y }))
        val xSortedVetrtexes: MutableList<GVertex> = ArrayList<GVertex>(N)
        xSortedVetrtexes.addAll(vertexes)
        xSortedVetrtexes.sortWith(Comparator.comparingDouble<GVertex>({ it.x }))
        val left = GEdge(
            GVertex(xSortedVetrtexes[0].x, vertexes[0].y),
            GVertex(xSortedVetrtexes[0].x, vertexes[N - 1].y)
        )
        val right = GEdge(
            GVertex(xSortedVetrtexes[N - 1].x, vertexes[0].y),
            GVertex(xSortedVetrtexes[N - 1].x, vertexes[N - 1].y)
        )
        trapezium = Trapezium(left, right, vertexes[0].y, vertexes[N - 1].y)
        edges.add(left)
        edges.add(right)
        edges.sortWith(Comparator.comparingDouble<GEdge>({ obj: GEdge ->
            obj.middleXInInterval(
                vertexes[0].y,
                vertexes[N - 1].y
            )
        }))
        for (i in edges.indices) {
            edges[i].setName("e$i")
        }
        myRoot = buildTrapezium(vertexes, edges, trapezium)
    }

    fun root() = myRoot
}