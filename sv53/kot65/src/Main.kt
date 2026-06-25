fun main() {
    val A = mutableSetOf<Int>()
    val B = mutableSetOf<Int>()

    // 1. Generam elementele pentru multimea A
    var nA = 0
    while (A.size < 100 && nA < 10000) {
        val numitor = 2 * nA - 9
        if (numitor != 0) {
            val numarator = 8 * nA - 18
            if (numarator % numitor == 0) {
                val x = numarator / numitor
                if (x >= 0) { // x trebuie sa fie in N (numar natural)
                    A.add(x)
                }
            }
        }
        nA++
    }

    // 2. Generam elementele pentru multimea B
    var nB = 1
    while (B.size < 100 && nB < 10000) {
        val numitor = 3 * nB - 8
        if (numitor != 0) {
            val numarator = 9 * nB * nB - 48 * nB + 16
            if (numarator % numitor == 0) {
                val x = numarator / numitor // x este in Z (numar intreg)
                B.add(x)
            }
        }
        nB++
    }

    // 3. Calculam Intersectia (B intersectat cu A)
    val intersectia = B.intersect(A)

    // 4. Calculam Produsul Cartesian (A x B) sub forma de lista de perechi
    val produsCartesian = mutableListOf<Pair<Int, Int>>()
    for (a in A) {
        for (b in B) {
            produsCartesian.add(Pair(a, b))
        }
    }

    // 5. Combinam rezultatele intr-un HashMap (Reuniunea ceruta)
    // Deoarece produsul cartesian contine perechi, iar intersectia contine numere simple,
    // pentru a le reuni intr-o structura comuna tip HashMap, vom folosi un index ca cheie.
    val hashMapRezultat = HashMap<Int, String>()
    var indexCheie = 1

    // Adaugam elementele din produsul cartesian
    for (pereche in produsCartesian) {
        hashMapRezultat[indexCheie] = "Pereche(${pereche.first}, ${pereche.second})"
        indexCheie++
    }

    // Adaugam elementele din intersectie
    for (numar in intersectia) {
        hashMapRezultat[indexCheie] = "NumarInIntersectie($numar)"
        indexCheie++
    }

    // Afisam dimensiunea finala si cateva exemple
    println("Multimea A are ${A.size} elemente.")
    println("Multimea B are ${B.size} elemente.")
    println("HashMap-ul final contine ${hashMapRezultat.size} elemente in total.")

    println("\nExemplu de elemente din HashMap:")
    hashMapRezultat.entries.take(5).forEach { entry ->
        println("Cheie: ${entry.key} -> Valoare: ${entry.value}")
    }
}