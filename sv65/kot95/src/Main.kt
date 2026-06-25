// 1. Clasa de tip ENUM pentru tipul salii
enum class TipSala {
    AMFITEATRU, LABORATOR, CURS_NORMAL
}

// 2. Clasa care modeleaza o Sala de Curs
class SalaDeCurs(
    val nume: String,
    val capacitateMaxima: Int,
    val tip: TipSala
) {
    private val studentiPrezenti = mutableListOf<String>()

    // Operatie pentru adaugarea unui student in sala
    fun adaugaStudent(numeStudent: String): Boolean {
        if (studentiPrezenti.size < capacitateMaxima) {
            studentiPrezenti.add(numeStudent)
            println("Studentul $numeStudent a intrat in sala $nume.")
            return true
        }
        println("Sala $nume este plina! $numeStudent nu poate intra.")
        return false
    }

    // Operatie pentru afisarea starii salii
    fun afiseazaDetalii() {
        println("Sala: $nume | Tip: $tip | Ocupare: ${studentiPrezenti.size}/$capacitateMaxima")
    }
}

fun main() {
    // Cream un obiect de tip SalaDeCurs folosind enum-ul definit
    val salaA1 = SalaDeCurs("Amfiteatrul A1", 3, TipSala.AMFITEATRU)

    salaA1.afiseazaDetalii()

    // Adaugam studenti pana se umple sala
    salaA1.adaugaStudent("Ionut")
    salaA1.adaugaStudent("Maria")
    salaA1.adaugaStudent("Andrei")
    salaA1.adaugaStudent("Elena") // Aceasta va fi refuzata deoarece capacitatea este 3

    salaA1.afiseazaDetalii()
}