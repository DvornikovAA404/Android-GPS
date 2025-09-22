import kotlin.math.*
import kotlin.random.Random
import java.util.Random as JRandom
import Movable

open class Human(
    fio: String,
    age: Int,
    speed: Double,
    private val alpha: Double = 0.75,
    private val meanSpeed: Double = speed,
    private val stdAngle: Double = PI / 4
) : Movable {
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

    open val totalSteps = 30

    override fun move(stepTime: Double) {
        for (t in 0 until totalSteps) {
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

            println("${fio} двигается со скоростью %.2f м/с, направление %.2f°, координаты (%.2f; %.2f)"
                .format(speed, Math.toDegrees(direction), x, y))
        }
    }
}