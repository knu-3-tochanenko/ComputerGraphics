package graph

class Trapezium {
    var left: Edge? = null
    var right: Edge? = null
    var minY = 0.0
    var maxY = 0.0

    constructor() {}
    constructor(left: Edge?, right: Edge?, minY: Double, maxY: Double) {
        this.left = left
        this.right = right
        this.minY = minY
        this.maxY = maxY
    }

    /*
    * 1 - belongs
    * -1 - no
    * 0 - intersection
    */
    fun edgeBelongs(e: Edge): Int {
        if (java.lang.Double.compare(e.ay, minY) > 0 && java.lang.Double.compare(e.ay, maxY) < 0 ||
            java.lang.Double.compare(e.by, minY) > 0 && java.lang.Double.compare(e.by, maxY) < 0
        ) {
            return 1 //(1), (2), (7), (8)
        }
        if (java.lang.Double.compare(e.ay, minY) == 0 && java.lang.Double.compare(e.by, maxY) == 0 ||
            java.lang.Double.compare(e.by, minY) == 0 && java.lang.Double.compare(e.ay, maxY) == 0
        ) {
            return 0 //(4)
        }
        if (java.lang.Double.compare(e.ay, minY) == 0 && java.lang.Double.compare(e.by, maxY) > 0 ||
            java.lang.Double.compare(e.by, minY) == 0 && java.lang.Double.compare(e.ay, maxY) > 0
        ) {
            return 0 //(6)
        }
        if (java.lang.Double.compare(e.ay, maxY) == 0 && java.lang.Double.compare(e.by, minY) < 0 ||
            java.lang.Double.compare(e.by, maxY) == 0 && java.lang.Double.compare(e.ay, minY) < 0
        ) {
            return 0 //(10)
        }
        return if (java.lang.Double.compare(e.ay, maxY) > 0 && java.lang.Double.compare(e.by, minY) < 0 ||
            java.lang.Double.compare(e.by, maxY) > 0 && java.lang.Double.compare(e.ay, minY) < 0
        ) {
            0 //(9)
        } else -1
    }

    fun vertexBelongs(v: Vertex): Boolean {
        return java.lang.Double.compare(v.y, minY) > 0 && java.lang.Double.compare(v.y, maxY) < 0
    }

    override fun toString(): String {
        return "{" +
                "left=" + left +
                "right=" + right +
                "minY=" + minY +
                ", maxY=" + maxY +
                "}"
    }
}
