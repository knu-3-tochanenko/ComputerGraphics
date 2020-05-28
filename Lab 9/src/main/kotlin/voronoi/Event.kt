package voronoi

open class Event(val p: Point?) : Comparable<Event?> {
    override operator fun compareTo(other: Event?) =
        if (other != null && p != null && other.p != null)
            Point.minYOrderedCompareTo(p, other.p)
        else 0
}