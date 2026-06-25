import java.util.concurrent.ConcurrentHashMap

// Clasa ce implementeaza functionalitatea unei bariere bazata pe wait si notifyAll
class BarieraSincronizare(private val numarTotalFire: Int) {
    private var fireAsteptate = numarTotalFire

    @Synchronized
    fun asteapta() {
        fireAsteptate--
        if (fireAsteptate == 0) {
            // Corectura: Castam 'this' la java.lang.Object pentru a putea apela notifyAll()
            (this as java.lang.Object).notifyAll()
        } else {
            // Firele sosite premature asteapta eliberarea barierii
            while (fireAsteptate > 0) {
                try {
                    (this as java.lang.Object).wait()
                } catch (e: InterruptedException) {
                    Thread.currentThread().interrupt()
                }
            }
        }
    }
}

fun main() {
    val datePartajate = ConcurrentHashMap<String, Int>()
    datePartajate["A"] = 100
    datePartajate["B"] = 200
    datePartajate["C"] = 300

    val numarFire = 3
    val bariera = BarieraSincronizare(numarFire)
    var capsulaSuma = 0

    // Cream si lansam firele de executie
    val fireExecutie = listOf(
        Thread {
            val valoare = datePartajate["A"] ?: 0
            synchronized(bariera) { capsulaSuma += valoare }
            println("Fir 1 terminat de procesat valoarea A. Asteapta la bariera...")
            bariera.asteapta()
        },
        Thread {
            val valoare = datePartajate["B"] ?: 0
            synchronized(bariera) { capsulaSuma += valoare }
            println("Fir 2 terminat de procesat valoarea B. Asteapta la bariera...")
            bariera.asteapta()
        },
        Thread {
            val valoare = datePartajate["C"] ?: 0
            synchronized(bariera) { capsulaSuma += valoare }
            println("Fir 3 terminat de procesat valoarea C. Asteapta la bariera...")
            bariera.asteapta()
        }
    )

    fireExecutie.forEach { it.start() }
    fireExecutie.forEach { it.join() }

    println("Toate firele au trecut de bariera. Suma finala din hasmap este: $capsulaSuma")
}