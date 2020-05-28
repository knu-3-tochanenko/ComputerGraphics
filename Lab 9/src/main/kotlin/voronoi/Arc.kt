package voronoi

class Arc : ArcKey {
    private val v: Voronoi
    var leftBreakPoint: BreakPoint?
    var rightBreakPoint: BreakPoint?

    override val left: Point?
        get() = if (leftBreakPoint != null) leftBreakPoint!!.point else Point(
            Double.NEGATIVE_INFINITY,
            Double.POSITIVE_INFINITY
        )
    override val right: Point?
        get() = if (rightBreakPoint != null) rightBreakPoint!!.point else Point(
            Double.POSITIVE_INFINITY,
            Double.POSITIVE_INFINITY
        )

    var site: Point

    constructor(left: BreakPoint?, right: BreakPoint?, v: Voronoi) {
        this.v = v
        if (left == null && right == null) {
            throw RuntimeException("cannot make arc with null breakpoints")
        }
        this.leftBreakPoint = left
        this.rightBreakPoint = right
        site = left?.s2 ?: right!!.s1
    }

    constructor(site: Point, v: Voronoi) {
        this.v = v
        leftBreakPoint = null
        rightBreakPoint = null
        this.site = site
    }

    override fun toString(): String {
        val l: Point? = left
        val r: Point? = right
        return java.lang.String.format("{%.4f, %.4f}", l?.x, r?.x)
    }

    fun checkCircle(): Point? {
        if (leftBreakPoint == null || rightBreakPoint == null) return null
        return if (Point.ccw(leftBreakPoint!!.s1, site, rightBreakPoint!!.s2) != -1)
            null
        else
            leftBreakPoint!!.edge.intersection(rightBreakPoint!!.edge)
    }
}