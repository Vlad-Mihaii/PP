// Definim structura unui Nod din arborele binar
class Nod(val cuvant: String) {
    var stanga: Nod? = null
    var dreapta: Nod? = null
}

class ArboreBinarCuvinte {
    var radacina: Nod? = null

    // Functie publica pentru a introduce un cuvant in arbore
    fun adauga(cuvant: String) {
        radacina = adaugaRecursiv(radacina, cuvant.lowercase())
    }

    // Logica recursiva de adaugare pe baza ordinii alfabetice (constringere)
    private fun adaugaRecursiv(curent: Nod?, cuvant: String): Nod {
        if (curent == null) {
            return Nod(cuvant)
        }

        // Comparam cuvintele alfabetic
        if (cuvant < curent.cuvant) {
            curent.stanga = adaugaRecursiv(curent.stanga, cuvant)
        } else if (cuvant > curent.cuvant) {
            curent.dreapta = adaugaRecursiv(curent.dreapta, cuvant)
        }
        // Daca cuvantul este egal, nu il mai adaugam (evitam duplicatele)
        return curent
    }

    // Afisarea arborelui in ordine alfabetica (In-Order Traversal)
    fun afiseazaInOrdine(nod: Nod?) {
        if (nod != null) {
            afiseazaInOrdine(nod.stanga)
            print("${nod.cuvant} ")
            afiseazaInOrdine(nod.dreapta)
        }
    }
}

fun main() {
    // Simulam continutul unui fisier text citit ca sir de caractere
    val continutFisier = "invatam kotlin si structuri de date pentru examen"

    // Spargem textul in cuvinte separate prin spatiu
    val cuvinte = continutFisier.split(" ")

    val arbore = ArboreBinarCuvinte()

    // Introducem fiecare cuvant in arborele binar
    for (cuv in cuvinte) {
        arbore.adauga(cuv)
    }

    println("Cuvintele au fost organizate in arborele binar.")
    println("Afisarea portiunii din arbore (sortat alfabetic):")
    arbore.afiseazaInOrdine(arbore.radacina)
    println()
}