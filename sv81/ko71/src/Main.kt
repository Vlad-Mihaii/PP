import java.io.File

// 1. Interfata tinta (Target) pe care o asteapta clientul
interface UniversalWriter {
    fun salveaza(obiect: Any, numeFisier: String)
}

// 2. Clasa existenta (Adaptee) care are o interfata incompatibila (stie doar text)
class LegacyFileWriter {
    fun scrieTextInFisier(numeFisier: String, text: String) {
        val fisier = File(numeFisier)
        fisier.writeText(text)
        println("[Sistem] Datele au fost scrise cu succes in: $numeFisier")
    }
}

// 3. Adaptorul (Adapter) care face legatura intre cele doua componente
class DataAdapter(private val legacyWriter: LegacyFileWriter) : UniversalWriter {

    override fun salveaza(obiect: Any, numeFisier: String) {
        val textFormatat: String = when (obiect) {
            // Daca obiectul este o colectie (Lista, Set etc.), unim elementele prin virgula
            is Collection<*> -> "Colectie: " + obiect.joinToString(", ")
            // Daca este un tip de date simplu (String, Int, Boolean etc.)
            else -> "Tip Simplu: " + obiect.toString()
        }

        // Apelam metoda clasei adaptate folosind textul gata formatat
        legacyWriter.scrieTextInFisier(numeFisier, textFormatat)
    }
}

fun main() {
    val scriitorBaza = LegacyFileWriter()
    val adaptor = DataAdapter(scriitorBaza)

    // Exemplu 1: Trimitem un tip de date simplu (un numar intreg)
    val numarSimplu = 2026
    adaptor.salveaza(numarSimplu, "numar.txt")

    // Exemplu 2: Trimitem o colectie (o lista de siruri de caractere)
    val listaStudenti = listOf("Alin", "Bogdan", "Catalin")
    adaptor.salveaza(listaStudenti, "studenti.txt")
}