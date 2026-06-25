// Interfata tinta (Target) ceruta de sistem
interface AfisajStandard {
    fun afiseaza()
}

// Adaptor pentru date simple (ex: String, Int)
class AdaptorDateSimple(private val date: Any) : AfisajStandard {
    override fun afiseaza() {
        println("[DATE SIMPLE] Valoarea este: $date")
    }
}

// Adaptor pentru colectii (ex: List)
class AdaptorColectie(private val colectie: Collection<*>) : AfisajStandard {
    override fun afiseaza() {
        println("[COLECTIE] Elemente: ${colectie.joinToString(", ")}")
    }
}

fun main() {
    // Clientul utilizeaza aceeasi operatie (.afiseaza()) indiferent de structura interna
    val listaAfisaje: List<AfisajStandard> = listOf(
        AdaptorDateSimple(2026),
        AdaptorDateSimple("Salut Kotlin"),
        AdaptorColectie(listOf("Mere", "Pere", "Banane"))
    )

    // Afisare uniforma prin polimorfism
    for (element in listaAfisaje) {
        element.afiseaza()
    }
}