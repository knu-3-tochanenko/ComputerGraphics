open class Event(val p: Point?) : Comparable<Event?> {
    override operator fun compareTo(other: Event?): Int {
        if (other != null && p != null && other.p != null) {
            return Point.minYOrderedCompareTo(p, other.p)
        }
        return 0
    }
}