import java.util.concurrent.ConcurrentHashMap
import kotlin.concurrent.thread

// Clasa de baza care stabileste structura comuna
abstract class OperatieActiva(val hartaPartajata: ConcurrentHashMap<String, Int>) {
    abstract fun pornesteExecutia()
}

// Subclasa activa pentru Adunare rulata pe fir separat
class SubclasaAdunare(harta: ConcurrentHashMap<String, Int>) : OperatieActiva(harta) {
    override fun pornesteExecutia() {
        thread {
            // Executa operatia in mod concurent
            hartaPartajata["Adunare_Rezultat"] = 50 + 20
            println("[Thread Adunare] Am procesat adunarea.")
        }
    }
}

// Subclasa activa pentru Inmultire rulata pe fir separat
class SubclasaInmultire(harta: ConcurrentHashMap<String, Int>) : OperatieActiva(harta) {
    override fun pornesteExecutia() {
        thread {
            hartaPartajata["Inmultire_Rezultat"] = 5 * 10
            println("[Thread Inmultire] Am procesat inmultirea.")
        }
    }
}

fun main() {
    // Folosim ConcurrentHashMap pentru siguranta firelor de executie (thread-safe)
    val hartaComuna = ConcurrentHashMap<String, Int>()

    val sarcina1 = SubclasaAdunare(hartaComuna)
    val sarcina2 = SubclasaInmultire(hartaComuna)

    // Pornim procesarile simultane
    sarcina1.pornesteExecutia()
    sarcina2.pornesteExecutia()

    Thread.sleep(500) // Asteptam scurt finalizarea thread-urilor
    println("\nHarta finala partajata: $hartaComuna")
}