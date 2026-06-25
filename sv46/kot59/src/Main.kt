fun main() {
    // 1. Initializam doua colectii cu cate 20 de elemente care se suprapun partial
    val colectiaA = (1..20).toList()
    val colectiaB = (10..29).toList()

    // 2. Calculam produsul cartezian A x B folosind flatMap si map cu expresii lambda
    val produsAXB = colectiaA.flatMap { a ->
        colectiaB.map { b -> Pair(a, b) }
    }

    // 3. Calculam produsul cartezian B x A
    val produsBXA = colectiaB.flatMap { b ->
        colectiaA.map { a -> Pair(b, a) }
    }

    // 4. Gasim intersectia celor doua produse carteziene utilizand functia .intersect()
    // Aceasta functie analizeaza elementele comune din ambele liste
    val intersectieRezultat = produsAXB.intersect(produsBXA.toSet())

    // 5. Depunem rezultatul obtinut intr-un dictionar (Map) cu chei incrementale
    val dictionarRezultat = mutableMapOf<String, Pair<Int, Int>>()
    var contor = 1

    for (pereche in intersectieRezultat) {
        dictionarRezultat["Pereche_$contor"] = pereche
        contor++
    }

    // 6. Afisam dictionarul generat in consola
    println("Numarul total de elemente in intersectie: ${dictionarRezultat.size}")
    println("\nContinutul dictionarului rezultat:")
    dictionarRezultat.forEach { (cheie, valoare) ->
        println("$cheie -> $valoare")
    }
}