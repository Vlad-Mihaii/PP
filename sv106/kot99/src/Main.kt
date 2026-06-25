import java.io.File
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

fun main() {
    // 1. Pregatim fisierul cu numere intregi pe disc
    val fisierDate = File("numere.txt")
    fisierDate.writeText("10,20,30,40,50,60,70,80")

    // 2. Memoria comuna declarata partajat si protejata la concurenta
    val dictionarPartajat = ConcurrentHashMap<Int, Int>()

    // 3. Serverul implementat ca fir de tip Daemon
    val serverDaemon = Thread {
        println("[Server Daemon] Pornit. Incarc datele din fisier...")
        val linie = fisierDate.readText()
        val numere = linie.split(",").map { it.trim().toInt() }

        // Populam dictionarul din memoria comuna
        numere.forEachIndexed { index, valoare ->
            dictionarPartajat[index] = valoare
        }
        println("[Server Daemon] Date incarcate complet in dictionarul comun.")
    }

    // Setam firul ca daemon inainte de a-l porni
    serverDaemon.isDaemon = true
    serverDaemon.start()
    serverDaemon.join() // Asteptam sa termine incarcarea initiala

    // 4. Distribuim subintervale egale la fire procese normale
    val dimensiuneDictionar = dictionarPartajat.size
    val numarFireMuncitoare = 2
    val dimensiuneSubinterval = dimensiuneDictionar / numarFireMuncitoare
    val constantaMultiplicare = 5

    val fireMuncitoare = mutableListOf<Thread>()

    for (idFir in 0 until numarFireMuncitoare) {
        val startInceput = idFir * dimensiuneSubinterval
        val sfarsitFinal = startInceput + dimensiuneSubinterval

        val firProces = Thread {
            println("[Fir Worker-$idFir] Procesez subintervalul de indecsi: $startInceput -> ${sfarsitFinal - 1}")
            for (index in startInceput until sfarsitFinal) {
                val valoareOriginala = dictionarPartajat[index]
                if (valoareOriginala != null) {
                    // Modificam simultan valoarea in memoria comuna
                    dictionarPartajat[index] = valoareOriginala * constantaMultiplicare
                }
            }
        }
        fireMuncitoare.add(firProces)
    }

    // Pornim toate firele normale de lucru
    fireMuncitoare.forEach { it.start() }
    // Ne asiguram ca toate firele isi finalizeaza activitatea
    fireMuncitoare.forEach { it.join() }

    // 5. Afisam rezultatul stocat in memoria comuna securizata
    println("\nDictionarul final din memoria comuna dupa inmultirea simultana:")
    println(dictionarPartajat)
}