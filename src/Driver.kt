import kotlin.math.*
import kotlin.random.Random
import java.util.Random as JRandom
import Movable

class Driver(
    val number: String,
    age: Int
) : Human("Driver-$number", age, 10.0), Movable {
    private var direction: Double = JRandom().nextDouble(-PI, PI)
    private val rand = JRandom()
    var angleRad = Math.toRadians(Random.nextDouble(0.0, 360.0))

    private fun calculateDistance(speed: Double, time: Double): Int =
        (speed * time).coerceIn(100.0, 1500.0).let { ((it + 50) / 100).toInt() * 100 }

    override fun move(stepTime: Double) {
        for (t in 0 until totalSteps) {
            val speedKmH = Random.nextInt(2, 9) * 10 * 1000.0 / 3600
            val distance = calculateDistance(speedKmH, stepTime.toDouble()).toDouble()

            val dx = distance * cos(angleRad)
            val dy = distance * sin(angleRad)
            _x += dx
            _y += dy
            totalDistance += distance

            println("Автомобиль $number проехал %.2f м, направление %.1f°, со скоростью %.2f, координаты (%.2f; %.2f)"
                .format(distance, Math.toDegrees(angleRad), speedKmH, x, y))
        }
    }
}