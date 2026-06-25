import java.io.File

fun main() {
    // 1. Cream un fisier text de test pentru a avea date de intrare
    val numeFisier = "date_intrare.txt"
    val fisier = File(numeFisier)
    fisier.writeText("Acesta este un fisier text de test format din cateva propozitii simple.")

    // 2. Citim intregul continut al fisierului text
    val continut = fisier.readText()
    println("Textul initial din fisier:\n $continut \n")

    // 3. Impartim textul in cuvinte folosind spatiul ca delimitator
    val cuvinte = continut.split(" ")

    // 4. Procesam colectia folosind functii dedicate si o expresie lambda
    val cuvinteProcesate = cuvinte.map { cuvant ->
        // Daca cuvantul are minim 4 caractere, ii stergem primele doua caractere
        if (cuvant.length >= 4) {
            cuvant.drop(2)
        } else {
            cuvant // Daca are sub 4 caractere, ramane neschimbat
        }
    }

    // 5. Unim cuvintele inapoi intr-un singur text si il afisam
    val textFinal = cuvinteProcesate.joinToString(" ")
    println("Textul dupa procesare:\n$textFinal")
}