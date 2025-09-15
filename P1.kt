import kotlin.math.*
import java.util.Random as JRandom
import kotlin.random.Random

open class Human(
    fio: String,
    age: Int,
    speed: Double,
    private val alpha: Double = 0.75,
    private val meanSpeed: Double = speed,
    private val stdAngle: Double = PI / 4
) {
    var fio: String = fio
        get() = field.uppercase()
        set(value) { field = value.trim() }

    var age: Int = age
        set(value) { field = value.coerceAtLeast(0) }

    var speed: Double = speed
        set(value) { field = value.coerceAtLeast(0.0) }

    var _x: Double = 0.0
    var _y: Double = 0.0
    private var direction: Double = JRandom().nextDouble(-PI, PI)
    var totalDistance: Double = 0.0
    private val rand = JRandom()

    val x: Double get() = _x
    val y: Double get() = _y
    val distance: Double get() = totalDistance

    open fun move(stepTime: Double = 1.0) {
        speed = alpha * speed + (1 - alpha) * meanSpeed +
                (1 - alpha) * rand.nextGaussian() * meanSpeed * 0.1
        speed = speed.coerceAtLeast(0.0)

        direction = alpha * direction + (1 - alpha) * 0.0 +
                (1 - alpha) * rand.nextGaussian() * stdAngle

        val dx = speed * stepTime * cos(direction)
        val dy = speed * stepTime * sin(direction)
        _x += dx
        _y += dy
        totalDistance += sqrt(dx * dx + dy * dy)

        println("${fio} двигается со скоростью ${"%.2f".format(speed)} м/с " + "направление ${"%.2f".format(Math.toDegrees(direction))}° " + "координаты (${"%.2f".format(x)}; ${"%.2f".format(y)})")
    }
}


class Driver(
    val number: String,
    age: Int
) : Human("Driver-$number", age, 10.0){
    private var direction: Double = JRandom().nextDouble(-PI, PI)
    private val rand = JRandom()
    val angleRad  = Math.toRadians(Random.nextDouble(0.0, 360.0))

    private fun calculateDistance(speed: Double, time: Double): Int =
        (speed * time)
            .coerceIn(100.0, 1500.0)
            .let { ((it + 50) / 100).toInt() * 100 }

    override fun move(stepTime: Double) {
        val speedKmH = Random.nextInt(2, 9) * 10 * 1000.0 / 3600
        val distance  = calculateDistance(speedKmH, stepTime)

        val dx = distance * cos(angleRad)
        val dy = distance * sin(angleRad)

        _x += dx
        _y += dy
        totalDistance += distance

        println("автомобиль $number проехал ${distance}м " +
                "направление ${"%.1f".format(Math.toDegrees(angleRad))}° " + "со скоростью ${speed}" +
                " координаты (${"%.2f".format(x)}; ${"%.2f".format(y)})")
    }

}

fun main() {
    val people = Array(3) { i -> Human("Human${i + 1}", 20 + i, 1.0 + i * 0.1) }
    val cars = Driver("H109TM", 30)
    val totalTime = 30
    val step = 1.0

    for (t in 0 until totalTime) {
        println("\n--- t = ${t + 1} с ---")
        people.forEach { it.move(step) }
        cars.move(step)
    }

    println("\nИтог:")
    people.forEach { println("${it.fio} прошёл ${"%.2f".format(it.distance)} м") }
    println("Автомобиль ${cars.number} проехал ${"%.2f".format(cars.distance)} м")
}