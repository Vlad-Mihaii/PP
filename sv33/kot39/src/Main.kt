import java.io.File

fun main() {
    // 1. Cream un fisier de test cu propozitii simple
    val fisier = File("text_laborator.txt")
    fisier.writeText("Studentii invata limbaje noi de programare precum Kotlin.")

    // 2. Preluam si citim continutul fisierului text
    val textBrut = fisier.readText()
    println("Text initial din fisier: $textBrut")

    // 3. Impartim textul intr-o lista de cuvinte, curatand semnele de punctuatie
    val cuvinte = textBrut.split(Regex("[\\s,.]+")).filter { it.isNotEmpty() }

    // 4. Utilizam functii de colectie si lambda pentru a extrage caracterele din mijloc
    val rezultateMijloc = cuvinte
        .filter { cuvant -> cuvant.length >= 4 } // Conditia de minim 4 caractere
        .map { cuvant ->
            val lungime = cuvant.length
            val mijloc = lungime / 2

            // Extragem cele doua caractere centrale din mijlocul cuvantului
            val caractereMijloc = cuvant.substring(mijloc - 1, mijloc + 1)
            "$cuvant -> [$caractereMijloc]"
        }

    // 5. Afisam rezultatele procesarii colectiei
    println("\nCaracterele extrase din mijlocul cuvintelor eligibile:")
    rezultateMijloc.forEach { println(it) }
}