package voronoi

abstract class ArcKey : Comparable<ArcKey?> {
    abstract val left: Point?
    abstract val right: Point?

    override operator fun compareTo(other: ArcKey?): Int {
        val myLeft: Point? = left
        val myRight: Point? = right
        val yourLeft: Point? = other?.left
        val yourRight: Point? = other?.right

        if (yourLeft != null && yourRight != null && myLeft != null && myRight != null) {
            if ((other.javaClass == ArcQuery::class.java || this.javaClass == ArcQuery::class.java)
                && (myLeft.x <= yourLeft.x && myRight.x >= yourRight.x
                        || yourLeft.x <= myLeft.x && yourRight.x >= myRight.x)
            ) {
                return 0
            }
        }

        if (myLeft != null && myRight != null) {
            if (myLeft.x == yourLeft?.x && myRight.x == yourRight?.x) return 0
        }

        if (yourRight != null && myLeft != null) {
            if (myLeft.x >= yourRight.x) return 1
        }

        if (yourLeft != null && myRight != null && myLeft != null) {
            return if (myRight.x <= yourLeft.x) -1 else Point.midpoint(myLeft, myRight)
                .compareTo(yourRight?.let { Point.midpoint(yourLeft, it) })
        }
        return 0
    }
}