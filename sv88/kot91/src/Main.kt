import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread

abstract class OperatieCuLacat(
    val harta: HashMap<String, Int>,
    val lacat: ReentrantLock
) {
    abstract fun executaSimultan()
}

class ScadereActiva(harta: HashMap<String, Int>, lacat: ReentrantLock) : OperatieCuLacat(harta, lacat) {
    override fun executaSimultan() {
        thread {
            lacat.lock() // Blocam resursa (Lock)
            try {
                harta["Scadere_Rezultat"] = 100 - 40
                println("[Thread Scadere] Am efectuat operatia in siguranta.")
            } finally {
                lacat.unlock() // Eliberam resursa obligatoriu in blocul finally
            }
        }
    }
}

class ImpartireActiva(harta: HashMap<String, Int>, lacat: ReentrantLock) : OperatieCuLacat(harta, lacat) {
    override fun executaSimultan() {
        thread {
            lacat.lock()
            try {
                harta["Impartire_Rezultat"] = 80 / 2
                println("[Thread Impartire] Am efectuat operatia in siguranta.")
            } finally {
                lacat.unlock()
            }
        }
    }
}

fun main() {
    // Folosim un HashMap normal si adaugam protectie prin ReentrantLock (Mutex)
    val hartaPartajata = HashMap<String, Int>()
    val mutex = ReentrantLock()

    val task1 = ScadereActiva(hartaPartajata, mutex)
    val task2 = ImpartireActiva(hartaPartajata, mutex)

    task1.executaSimultan()
    task2.executaSimultan()

    Thread.sleep(500)
    println("\nHarta finala securizata: $hartaPartajata")
}