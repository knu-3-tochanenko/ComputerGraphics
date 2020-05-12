data class Dot(
    var x: Double,
    var y: Double,
    var angle: Double = 0.0
) {
    override fun equals(other: Any?): Boolean =
        if (other is Dot) {
            x == other.x && y == other.y
        } else false
}