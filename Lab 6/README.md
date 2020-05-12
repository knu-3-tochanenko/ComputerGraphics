# Лабораторна робота 6 : Алгоритм Джарвіса (Упаковка подарунків)

**Виконав студент групи ІПС-31**

**Точаненко Владислав Володимирович**

# Опис алгоритму & огляд коду

## Допоміжні класи

`Dot` - клас для зберігання координат точки та її куту відносно деякої точки, який буде вираховуватись далі.

```kotlin
data class Dot(
    var x: Double,
    var y: Double,
    var angle: Double = 0.0
)
```

`Line` - клас для зберігання відрізків.

```kotlin
data class Line(
    val start: Dot,
    val end: Dot
)
```

## Основний алгоритм

### Короткий опис

Метод Джарвіса (Упаковка подарунків) використовується для обчислення опуклої оболонки скінченної множини точок.

### Словесний опис алгоритму

Обираємо найлівішу точку із усієї множини точок. Вона буде стартовою. Далі знаходиться точка з найменшим полярним кутом відносно поточної точки. Повторюємо цей крок, поки наступна точка не буде першою.

### Програмна реалізація

#### Допоміжні методи

Метод `mostLeft` знаходить найлівішу точку із множини точок.

```kotlin
private fun mostLeft(dots: List<Dot>): Int {
    var index = 0;
    for (dot in dots.withIndex()) {
              if (dot.value.x < dots[index].x)
                  index = dot.index
    }
    return index
}
```

Метод `mostRight` знаходить найправішу точку для поточної.

```kotlin
private fun mostRight(dot: Dot, dots: List<Dot>): Dot {
    var next = dots[0]
    var index = 0
    for (i in dots.indices) {
        if (getSide(next, dot, dots[i]) == -1) {
            next = dots[i]
            index = i
        }
    }
    return dots[index]
}
```

Метод `getSide` повертає сторону повороту. 1 - лівий, -1 - правий і 0, якщо він прямий.

```kotlin
private fun getSide(a: Dot, b: Dot, c: Dot): Int {
    val rotation = getRotation(a, b, c)
    if (rotation > 0)
        return 1
    if (rotation < 0)
        return -1
    return 0
}
```

Метод `getRotation` вираховує полярний кут.

```kotlin
private fun getRotation(a: Dot, b: Dot, c: Dot): Double {
    return (c.y - a.y) * (b.x - a.x) - (b.y - a.y) * (c.x - a.x)
}
```

#### Основний алгоритм

Побудувати опуклу оболонку можливо для трьох та більше точок. Тому спочатку треба перевірити чи кількість даних точок більша за 2.

```kotlin
assert(dots.size > 2)
```

Створюємо список `list`, у котрому будемо зберігати ще не розглянуті точки. Знаходимо найлівішу точку і додаємо її до результату.

```kotlin
val list = dots.toMutableList()
val first = mostLeft(dots)
list.remove(dots[first])
list.add(dots[first])
result.add(dots[first])
```

Будемо записувати поточну знайдену точку у змінну `cur`. Далі знаходимо наступну точку, видаляємо її з тих, що розглядаються, та додаємо до результату. Повторюємо поки поточна не стане рівна початковій.

```kotlin
var cur = dots[first]

do {
    cur = mostRight(cur, list)
    list.remove(cur)
    result.add(cur)
} while (cur != dots[first])
```

Повертаємо результат обчислення.

```kotlin
return result
```

### Побудова графіку

Для побудови графіку була використана бібліотека [xChart](https://github.com/knowm/XChart).

Клас `ChartLine` є допоміжним. Він зберігає список точок як послідовність точок ламаної, а також автоматично надає кожній ламаній унікальну назву, щоб на графіку не було конфліктів.

Клас `Chart` використовується для зберігання ліній та точок, які потім можуть бути відображені на графіку.

* `addLine(List<Dot>)` - як аргумент приймає список точок та додає їх як ламану, що буде намальована на графіку
* `addDots(List<Dot>)` - як аргумент приймає список точок та додає їх у список точок, що будуть намальовані на графіку
* `draw()` - малює графік

### Функція `main`

Спочатку задаємо `dots` як список точок, по яким буде будуватись опукла оболонка. Далі створюється об'єкт графіку, додаються початкові точки і ламана, що є результатом роботи методу `GiftWrapper.calculate(dots)`, а далі цей графік малюється.

```kotlin
fun main() {
    val dots = listOf(
        ...
    )

    val chart = Chart()
    chart.addDots(dots)
    chart.addLine(GiftWrapper.calculate(dots))
    chart.draw()
}
```

### Приклад виконання

```kotlin
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
```

![result](./images/lab5.png)

## Повний код

### `Main.kt`

```kotlin
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
}
```

### `GiftWrapper.kt`

```kotlin
class GiftWrapper {
    companion object {
        private var result = mutableListOf<Dot>()

        fun calculate(dots: List<Dot>): List<Dot> {
            assert(dots.size > 2)

            val list = dots.toMutableList()
            val first = mostLeft(dots)
            list.remove(dots[first])
            list.add(dots[first])
            result.add(dots[first])

            var cur = dots[first]

            do {
                cur = mostRight(cur, list)
                list.remove(cur)
                result.add(cur)
            } while (cur != dots[first])

            return result
        }

        private fun mostLeft(dots: List<Dot>): Int {
            var index = 0;
            for (dot in dots.withIndex()) {
                if (dot.value.x < dots[index].x)
                    index = dot.index
            }
            return index
        }

        private fun mostRight(dot: Dot, dots: List<Dot>): Dot {
            var next = dots[0]
            var index = 0
            for (i in dots.indices) {
                if (getSide(next, dot, dots[i]) == 1) {
                    next = dots[i]
                    index = i
                }
            }
            return dots[index]
        }

        private fun getSide(a: Dot, b: Dot, c: Dot): Int {
            val rotation = getRotation(a, b, c)
            if (rotation > 0)
                return 1
            if (rotation < 0)
                return -1
            return 0
        }

        private fun getRotation(a: Dot, b: Dot, c: Dot): Double {
            return (c.y - a.y) * (b.x - a.x) - (b.y - a.y) * (c.x - a.x)
        }
    }
}
```

### `Line.kt`

```kotlin
data class Line(
    val start: Dot,
    val end: Dot
)
```

### `Dot.kt`

```kotlin
data class Dot(
    var x: Double,
    var y: Double,
    var angle: Double = 0.0
)
```

### `Chart.kt`

```kotlin
import org.knowm.xchart.QuickChart
import org.knowm.xchart.SwingWrapper
import org.knowm.xchart.XYChart
import org.knowm.xchart.style.lines.SeriesLines
import org.knowm.xchart.style.markers.SeriesMarkers
import java.awt.Color

class Chart(
    private val chartName: String = "Chart",
    private val xName: String = "x",
    private val yName: String = "y"
) {

    private val chart: XYChart =
        QuickChart.getChart(
            chartName, xName, yName, "0, 0",
            doubleArrayOf(0.0), doubleArrayOf(0.0)
        )

    private var lines = mutableListOf<ChartLine>()
    private var dots = mutableListOf<Dot>()

    fun addLine(dots: List<Dot>) {
        lines.add(ChartLine(dots))
    }

    fun addDots(dots: List<Dot>) {
        for (i in dots.indices) {
            this.dots.add(dots[i])
        }
    }

    fun draw() {
        for (line in lines)
            chart.addSeries(line.lineName, line.xData, line.yData)
                .setMarker(SeriesMarkers.CIRCLE).setLineColor(Color.BLACK).setLineStyle(SeriesLines.DASH_DASH);


        for (i in dots.indices)
            chart.addSeries("dot#$i", doubleArrayOf(dots[i].x), doubleArrayOf(dots[i].y))
                .setMarkerColor(Color.BLACK).setMarker(SeriesMarkers.CIRCLE)

        SwingWrapper(chart).displayChart()
    }
}
```

### `ChartLine.kt`

```kotlin
class ChartLine(
    var xData: DoubleArray,
    var yData: DoubleArray,
    var lineName: String = "#"
) {
    constructor(dots: List<Dot>) : this(DoubleArray(dots.size), DoubleArray(dots.size)) {
        for (i: Int in dots.indices) {
            xData[i] = dots[i].x
            yData[i] = dots[i].y
        }
    }

    init {
        if (lineName == "#") {
            lineName += id++
        }
    }
}

private var id = 1
```