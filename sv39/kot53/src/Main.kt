import kotlin.random.Random

fun main() {
    // 1. Generam o lista cu 100 de numere pare alese aleator
    val A = mutableListOf<Int>()
    while (A.size < 100) {
        val numar = Random.nextInt(1, 1000) // Generam un numar intre 1 si 999
        if (numar % 2 == 0) {
            A.add(numar) // Adaugam in colectie doar daca este par
        }
    }

    println("Colectia A a fost generata cu succes (100 de numere pare).")

    // 2. Calculam b_n pentru toate valorile lui n de la 1 la 100
    // Formula: b_n = a_1^2 + a_2^2 + ... + a_n^2
    val b = LongArray(100) // Folosim Long pentru a evita depasirea de memorie (overflow)
    var sumaCurenta: Long = 0

    for (i in 0 until 100) {
        val element = A[i].toLong()
        sumaCurenta += element * element // Adaugam patratul elementului curent
        b[i] = sumaCurenta // Salvam rezultatul b_n
    }

    // 3. Afisam cateva rezultate ca exemplu pentru verificare
    println("\nPrimele 5 valori ale lui b_n:")
    for (n in 1..5) {
        println("b_$n = ${b[n - 1]}")
    }

    println("...")
    println("Ultima valoare b_100 = ${b[99]}")
}