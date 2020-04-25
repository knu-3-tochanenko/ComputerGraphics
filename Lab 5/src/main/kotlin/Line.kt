data class Line(
    val xData: DoubleArray,
    val yData: DoubleArray,
    var lineName: String = "#"
) {
    init {
        if (lineName == "#") {
            lineName += id++
        }
    }
}

private var id = 1