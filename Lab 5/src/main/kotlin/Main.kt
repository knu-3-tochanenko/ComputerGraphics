fun main() {
   val chart = Chart()
    chart.add(Line(doubleArrayOf(1.0, 2.0, 3.0, 2.0, 1.0), doubleArrayOf(3.0, 1.0, 4.0, 5.0, 3.0)))
    chart.add(Line(doubleArrayOf(5.0, 4.0, 1.0, 3.0, 5.0), doubleArrayOf(1.0, 4.0, 2.0, 6.0, 1.0)))
    chart.draw()
}