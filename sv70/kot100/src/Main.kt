// 1. Definim structura unei Liste Algebrice folosind un Sealed Class
// Aceasta reprezinta o structura functionala de tip lista inlantuita imutabila
sealed class ListaAlgebrica {
    object Vid : ListaAlgebrica() // Capat de lista (lista goala)
    class Nod(val numeObiect: String, val urmatorul: ListaAlgebrica) : ListaAlgebrica() // Element curent + legatura
}

// 2. Clasa care modeleaza Sala de Laborator
class SalaLaborator(val numeSala: String) {
    // Initial, lista de echipamente din laborator este vida
    private var echipamente: ListaAlgebrica = ListaAlgebrica.Vid

    // Operatie pentru adaugarea unui obiect/echipament in sala
    fun adaugaEchipament(nume: String) {
        // Adaugam un nod nou in capul listei algebrice
        echipamente = ListaAlgebrica.Nod(nume, echipamente)
        println("In $numeSala s-a adaugat: $nume")
    }

    // Operatie publica pentru listarea tuturor obiectelor
    fun afiseazaLaborator() {
        print("Continut $numeSala: ")
        parcurgeRecursiv(echipamente)
        println()
    }

    // Functie privata auxiliara pentru parcurgerea structurii algebrice prin pattern matching
    private fun parcurgeRecursiv(lista: ListaAlgebrica) {
        when (lista) {
            is ListaAlgebrica.Vid -> print("[Gata]")
            is ListaAlgebrica.Nod -> {
                print("${lista.numeObiect} -> ")
                parcurgeRecursiv(lista.urmatorul) // Apel recursiv
            }
        }
    }
}

fun main() {
    val laboratorSisteme = SalaLaborator("Laboratorul de Sisteme de Operare")

    // Adaugam obiecte in sala prin operatiile clasei
    laboratorSisteme.adaugaEchipament("Osciloscop Digital")
    laboratorSisteme.adaugaEchipament("Placa de dezvoltare Raspberry Pi")
    laboratorSisteme.adaugaEchipament("PC Statie de lucru")

    // Afisam rezultatul stocat in structura algebrica
    println()
    laboratorSisteme.afiseazaLaborator()
}