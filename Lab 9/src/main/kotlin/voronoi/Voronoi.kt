package voronoi

import java.util.*


class Voronoi constructor(sites: ArrayList<Point>) {
    var sweepLoc: Double
    private val edgeList: ArrayList<VoronoiEdge?> = ArrayList<VoronoiEdge?>(sites.size)
    fun getEdgeList(): ArrayList<VoronoiEdge?> {
        return edgeList
    }

    private val breakPoints: HashSet<BreakPoint?> = HashSet()
    private val arcs: TreeMap<ArcKey, CircleEvent?> = TreeMap()
    private val events: TreeSet<Event> = TreeSet()

    private fun handleSiteEvent(cur: Event) {
        // Deal with first point case
        if (arcs.size == 0) {
            arcs[Arc(cur.p!!, this)] = null
            return
        }

        // Find the arc above the site
        val arcEntryAbove = arcs.floorEntry(ArcQuery(cur.p))
        val arcAbove = arcEntryAbove.key as Arc

        // Deal with the degenerate case where the first two points are at the same y value
        if (arcs.size == 0 && arcAbove.site.y == cur.p!!.y) {
            val newEdge = VoronoiEdge(arcAbove.site, cur.p)
            newEdge.p1 = Point((cur.p.x + arcAbove.site.x) / 2, Double.POSITIVE_INFINITY)
            val newBreak = BreakPoint(arcAbove.site, cur.p, newEdge, false, this)
            breakPoints.add(newBreak)
            edgeList.add(newEdge)
            val arcLeft = Arc(null, newBreak, this)
            val arcRight = Arc(newBreak, null, this)
            arcs.remove(arcAbove)
            arcs[arcLeft] = null
            arcs[arcRight] = null
            return
        }

        // Remove the circle event associated with this arc if there is one
        val falseCE = arcEntryAbove?.value
        if (falseCE != null) {
            events.remove(falseCE)
        }
        val breakL = arcAbove.leftBreakPoint
        val breakR = arcAbove.rightBreakPoint
        val newEdge = VoronoiEdge(arcAbove.site, cur.p!!)
        edgeList.add(newEdge)
        val newBreakL =
            BreakPoint(arcAbove.site, cur.p, newEdge, true, this)

        val newBreakR =
            BreakPoint(cur.p, arcAbove.site, newEdge, false, this)

        breakPoints.add(newBreakL)
        breakPoints.add(newBreakR)
        val arcLeft = Arc(breakL, newBreakL, this)
        val center = Arc(newBreakL, newBreakR, this)
        val arcRight = Arc(newBreakR, breakR, this)
        arcs.remove(arcAbove)
        arcs[arcLeft] = null
        arcs[center] = null
        arcs[arcRight] = null
        checkForCircleEvent(arcLeft)
        checkForCircleEvent(arcRight)
    }

    private fun handleCircleEvent(ce: CircleEvent) {
        arcs.remove(ce.arc)
        ce.arc.leftBreakPoint!!.finish(ce.vert)
        ce.arc.rightBreakPoint!!.finish(ce.vert)
        breakPoints.remove(ce.arc.leftBreakPoint)
        breakPoints.remove(ce.arc.rightBreakPoint)
        var entryRight = arcs.higherEntry(ce.arc)
        var entryLeft = arcs.lowerEntry(ce.arc)
        var arcRight = Arc(Point(0.0, 0.0), this)
        var arcLeft = Arc(Point(0.0, 0.0), this)
        val ceArcLeft = ce.arc.left
        val cocircularJunction = ce.arc.right == ceArcLeft
        if (entryRight != null) {
            arcRight = entryRight.key as Arc
            while (cocircularJunction && arcRight.right == ceArcLeft) {
                arcs.remove(arcRight)
                arcRight.leftBreakPoint!!.finish(ce.vert)
                arcRight.rightBreakPoint!!.finish(ce.vert)
                breakPoints.remove(arcRight.leftBreakPoint)
                breakPoints.remove(arcRight.rightBreakPoint)
                val falseCe = entryRight!!.value
                if (falseCe != null) {
                    events.remove(falseCe)
                }
                entryRight = arcs.higherEntry(arcRight)
                arcRight = entryRight.key as Arc
            }
            val falseCe = entryRight!!.value
            if (falseCe != null) {
                events.remove(falseCe)
                arcs[arcRight] = null
            }
        }
        if (entryLeft != null) {
            arcLeft = entryLeft.key as Arc
            while (cocircularJunction && arcLeft.left?.equals(ceArcLeft)!!) {
                arcs.remove(arcLeft)
                arcLeft.leftBreakPoint!!.finish(ce.vert)
                arcLeft.rightBreakPoint!!.finish(ce.vert)
                breakPoints.remove(arcLeft.leftBreakPoint)
                breakPoints.remove(arcLeft.rightBreakPoint)
                val falseCe = entryLeft!!.value
                if (falseCe != null) {
                    events.remove(falseCe)
                }
                entryLeft = arcs.lowerEntry(arcLeft)
                arcLeft = entryLeft.key as Arc
            }
            val falseCe = entryLeft!!.value
            if (falseCe != null) {
                events.remove(falseCe)
                arcs[arcLeft] = null
            }
        }
        val e = VoronoiEdge(arcLeft.rightBreakPoint!!.s1, arcRight.leftBreakPoint!!.s2)
        edgeList.add(e)

        val turnsLeft = ce.p?.let {
            Point.ccw(
                arcLeft.rightBreakPoint!!.edgeBegin!!,
                it, arcRight.leftBreakPoint!!.edgeBegin!!
            )
        } == 1

        val isLeftPoint: Boolean = if (turnsLeft) e.m < 0 else e.m > 0
        if (isLeftPoint) {
            e.p1 = ce.vert
        } else {
            e.p2 = ce.vert
        }
        val newBP = BreakPoint(arcLeft.rightBreakPoint!!.s1, arcRight.leftBreakPoint!!.s2, e, !isLeftPoint, this)
        breakPoints.add(newBP)
        arcRight.leftBreakPoint = newBP
        arcLeft.rightBreakPoint = newBP
        checkForCircleEvent(arcLeft)
        checkForCircleEvent(arcRight)
    }

    private fun checkForCircleEvent(a: Arc?) {
        val circleCenter = a!!.checkCircle()
        if (circleCenter != null) {
            val radius = a.site.distanceTo(circleCenter)
            val circleEventPoint = Point(circleCenter.x, circleCenter.y - radius)
            val ce = CircleEvent(a, circleEventPoint, circleCenter)
            arcs[a] = ce
            events.add(ce)
        }
    }

    companion object {
        private const val MAX_DIM = 600.0
        private const val MIN_DIM = -600.0
    }

    init {
        for (site in sites) {
            if (site.x > MAX_DIM || site.x < MIN_DIM || site.y > MAX_DIM || site.y < MIN_DIM) throw RuntimeException(
                String.format(
                    "Invalid site in input, sites must be between %f and %f",
                    MIN_DIM,
                    MAX_DIM
                )
            )
            events.add(Event(site))
        }
        sweepLoc = MAX_DIM
        do {
            val cur = events.pollFirst()
            sweepLoc = cur.p!!.y
            if (cur.javaClass == Event::class.java) {
                handleSiteEvent(cur)
            } else {
                val ce = cur as CircleEvent
                handleCircleEvent(ce)
            }
        } while (events.size > 0)
        sweepLoc = MIN_DIM
        for (bp in breakPoints) {
            bp!!.finish()
        }
    }
}