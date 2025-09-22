import kotlin.concurrent.thread
import kotlin.math.*
import kotlin.random.Random
import java.util.Random as JRandom
import Human
import Driver

fun main() {
    val people = Array(3) { i -> Human("Human${i + 1}", 20 + i, 1.0 + i * 0.1) }
    val cars = Driver("H109TM", 30)

    people.forEach { human ->
        thread(start = true) { human.move(1.0) }
    }
    thread(start = true) { cars.move(1.0) }
}
