fun main() {
    val dots = listOf(
        Dot(1.0, 3.0),
        Dot(2.0, 4.0),
        Dot(5.0, 3.0),
        Dot(1.0, 3.0),
        Dot(7.0, 6.5),
        Dot(4.3, 6.1),
        Dot(1.0, 2.5),
        Dot(0.3, 0.0),
        Dot(7.0, 1.5),
        Dot(7.0, 1.5),
        Dot(11.0, 6.0),
        Dot(3.0, 9.0),
        Dot(0.5, 7.0),
        Dot(-0.5, 3.5)
    )

    val chart = Chart()
    chart.addDots(dots)
    chart.addLine(GiftWrapper.calculate(dots))
    chart.draw()

//    val dots = listOf(
//        Dot(10.0, 10.0),
//        Dot(0.0, 0.0),
//        Dot(1.0, 4.0)
//    )
//
//    val chart = Chart()
//    chart.addDots(dots)
//    chart.addLine(dots)
//    chart.draw()
//
//    println(GiftWrapper.angle(
//        Dot(dots[0].x - dots[1].x, dots[0].y - dots[1].y),
//        Dot(dots[2].x - dots[1].x, dots[2].y - dots[1].y)
//    ))
}