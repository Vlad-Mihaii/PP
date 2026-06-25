fun main() {
    // 1. Initializam doua liste cu cate 20 de elemente fiecare
    val listaA = (1..20).toList()
    val listaB = (21..40).toList()

    // 2. Calculam produsul cartezian A x B folosind flatMap si map cu expresie lambda
    val produsAXB = listaA.flatMap { a ->
        listaB.map { b -> Pair(a, b) }
    }

    // 3. Calculam produsul cartezian B x B
    val produsBXB = listaB.flatMap { b1 ->
        listaB.map { b2 -> Pair(b1, b2) }
    }

    // 4. Realizam uniunea celor doua produse carteziene folosind functia .union()
    // Aceasta functie asigura combinarea elementelor si elimina eventualele duplicate
    val uniuneRezultat = produsAXB.union(produsBXB)

    // 5. Salvam rezultatul intr-un dictionar (Map) cu chei textuale incrementale
    val dictionarFinal = mutableMapOf<String, Pair<Int, Int>>()
    var contor = 1

    for (pereche in uniuneRezultat) {
        dictionarFinal["Cheie_$contor"] = pereche
        contor++
    }

    // 6. Afisam dimensiunea totala si primele 10 elemente din dictionar pentru exemplificare
    println("Numarul total de perechi in dictionar: ${dictionarFinal.size}")
    println("\nAfisam primele 10 inregistrari din dictionarul rezultat:")

    dictionarFinal.entries.take(10).forEach { entry ->
        println("${entry.key} -> ${entry.value}")
    }
}