data class Rectangle(
        var bottomLeft: Dot,
        var topRight: Dot
) {
    fun toLine(): List<Dot> {
        return listOf<Dot>(
                Dot(bottomLeft.x, bottomLeft.y),
                Dot(topRight.x, bottomLeft.y),
                Dot(topRight.x, topRight.y),
                Dot(bottomLeft.x, topRight.y),
                Dot(bottomLeft.x, bottomLeft.y)
        )
    }
}