fun main() {
    val points = arrayListOf(
        Dot(1.0, 3.0),
        Dot(2.0, 3.5),
        Dot(0.0, 1.0),
        Dot(1.5, 2.5),
        Dot(2.5, 1.1),
        Dot(1.0, 7.4),
        Dot(5.0, 3.4),
        Dot(2.0, 1.0),
        Dot(1.3, 0.5),
        Dot(2.6, 0.0),
        Dot(3.9, 1.3),
        Dot(0.9, 1.9),
        Dot(1.6, 5.4),
        Dot(1.1, 7.0)
    )

    val visualisation = Visualisation()
    visualisation.drawPoints(points)
    visualisation.drawHull(Hull(points).getResult().toMutableList())
}