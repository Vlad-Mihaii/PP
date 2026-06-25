// 1. Clasa care modeleaza un echipament din laborator
class Echipament(val nume: String, val inventarId: String) {
    fun afiseazaDetalii() {
        println("Echipament: $nume | ID: $inventarId")
    }
}

// 2. Clasa care modeleaza sala de laborator si contine o lista mutabila
class SalaLaborator(val numeSala: String) {
    // Utilizam o lista mutabila pentru a gestiona dinamic echipamentele
    private val listaEchipamente = mutableListOf<Echipament>()

    fun adaugaEchipament(echipament: Echipament) {
        listaEchipamente.add(echipament)
        println("[Laborator] A fost adaugat: ${echipament.nume}")
    }

    fun eliminaEchipamentDupaId(id: String): Boolean {
        val sters = listaEchipamente.removeIf { it.inventarId == id }
        if (sters) {
            println("[Laborator] Echipamentul cu ID-ul $id a fost eliminat.")
        } else {
            println("[Laborator] Echipamentul cu ID-ul $id nu a fost gasit.")
        }
        return sters
    }

    fun afiseazaInventarComplet() {
        println("\n--- Inventar Sala: $numeSala ---")
        if (listaEchipamente.isEmpty()) {
            println("Sala este goala.")
        } else {
            listaEchipamente.forEach { it.afiseazaDetalii() }
        }
        println("--------------------------------")
    }
}

fun main() {
    // Cream instanta salii de laborator
    val laboratorInformatica = SalaLaborator("Sali de Calculatoare 302")

    // Cream cateva obiecte de tip echipament
    val pc1 = Echipament("Calculator HP", "PC_01")
    val pc2 = Echipament("Calculator Dell", "PC_02")
    val proiector = Echipament("Proiector BenQ", "PROJ_01")

    // Executam operatii pe obiecte
    laboratorInformatica.adaugaEchipament(pc1)
    laboratorInformatica.adaugaEchipament(pc2)
    laboratorInformatica.adaugaEchipament(proiector)

    laboratorInformatica.afiseazaInventarComplet()

    // Eliminam un obiect din lista mutabila
    laboratorInformatica.eliminaEchipamentDupaId("PC_02")

    laboratorInformatica.afiseazaInventarComplet()
}