fun main() {
    // Initializam doua multimi cu cate 20 de elemente (unele comune, altele diferite)
    val A = (1..20).toSet()
    val B = (10..29).toSet()

    // 1. Calculam Reuniunea (A reunit cu B)
    val reuniune = A.union(B)

    // 2. Calculam Intersectia (B intersectat cu A)
    val intersectie = B.intersect(A)

    // 3. Calculam Produsul Cartesian intre Reuniune si Intersectie
    // Folosim flatMap si map cu functii lambda pentru a genera perechile (x, y)
    val produsCartesian = reuniune.flatMap { x ->
        intersectie.map { y -> Pair(x, y) }
    }

    // 4. Depunem rezultatul intr-un dictionar (Map)
    // Cheia va fi indexul perechii, iar valoarea va fi perechea respectiva
    val dictionarRezultat = produsCartesian.mapIndexed { index, pereche ->
        index to "(${pereche.first}, ${pereche.second})"
    }.toMap()

    // Afisam rezultatele obtinute
    println("Dimensiune Reuniune: ${reuniune.size} elemente")
    println("Dimensiune Intersectie: ${intersectie.size} elemente")
    println("Numar total elemente in Produsul Cartesian: ${dictionarRezultat.size}")

    // Afisam primele 5 intrari din dictionar ca exemplu
    println("\nPrimele 5 elemente din dictionar:")
    dictionarRezultat.entries.take(5).forEach { entry ->
        println("Cheie [${entry.key}] -> Valoare: ${entry.value}")
    }
}