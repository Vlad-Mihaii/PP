fun main() {
    // 1. Initializam doua colectii de tip Lista cu cate 20 de elemente fiecare
    val listaA = (1..20).toList()
    val listaB = (21..40).toList()

    // 2. Calculam produsul cartezian AxB folosind functiile flatMap si map
    // Pentru fiecare element din A, il mapam cu fiecare element din B sub forma de Pair
    val produsCartezianAXB = listaA.flatMap { a ->
        listaB.map { b -> Pair(a, b) }
    }

    // 3. Calculam uniunea BuA utilizand functia specifica .union()
    val uniuneBUA = listaB.union(listaA).toList()

    // 4. Calculam (AxB)x(BuA) si depunem rezultatele intr-un dictionar (Map)
    // Cheia va fi un index unic de tip String, iar valoarea va fi perechea finala
    val dictionarRezultat = mutableMapOf<String, Pair<Pair<Int, Int>, Int>>()
    var contorId = 1

    for (perecheAXB in produsCartezianAXB) {
        for (elementBUA in uniuneBUA) {
            val cheieUnica = "ID_$contorId"
            dictionarRezultat[cheieUnica] = Pair(perecheAXB, elementBUA)
            contorId++
        }
    }

    // 5. Afisam dimensiunea totala si primele 5 elemente din dictionar pentru exemplificare
    println("Dimensiunea totala a dictionarului rezultat: ${dictionarRezultat.size} elemente.")
    println("\nAfisam primele 5 inregistrari din dictionar:")

    dictionarRezultat.entries.take(5).forEach { entries ->
        println("${entries.key} -> Pereche AxB: ${entries.value.first}, Element din BuA: ${entries.value.second}")
    }
}