// Clasa care actioneaza ca un Functor explicit impachetat peste o colectie Map
class DictionarFunctor<K, V>(val dateMape: Map<K, V>) {

    // Functia fmap specifica modelului matematic de Functor
    fun <R> fmap(transformare: (V) -> R): DictionarFunctor<K, R> {
        val nouaHarta = dateMape.mapValues { entry -> transformare(entry.value) }
        return DictionarFunctor(nouaHarta)
    }
}

fun main() {
    // 1. Initializam hasmap-ul initial cu valori numerice intregi
    val hasmapInitial = mapOf(
        "A" to 5,
        "B" to 10,
        "C" to 15
    )
    println("Hasmap-ul initial: $hasmapInitial")

    // 2. Cream instanta functorului nostru
    val functorSursa = DictionarFunctor(hasmapInitial)

    // 3. Aplicam transformarea lambda f(x) = 3x - 1 urmat de conversia la String
    val functorRezultat = functorSursa.fmap { x ->
        val calculMatematic = 3 * x - 1
        val stringRezultat = "Rezultat calcul: $calculMatematic"
        stringRezultat // Lambda returneaza valoarea convertita in string
    }

    // 4. Afisam rezultatul final stocat in noul hasmap generat de Functor
    println("\nHasmap-ul final dupa aplicarea functorului:")
    functorRezultat.dateMape.forEach { (cheie, valoare) ->
        println("Cheie: $cheie -> Valoare: \"$valoare\" (Tip: ${valoare.javaClass.simpleName})")
    }
}