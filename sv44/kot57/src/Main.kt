import kotlin.random.Random

fun main() {
    // 1. Initializam doua multimi (Set) cu cate 15 numere aleatorii unice
    val A = mutableSetOf<Int>()
    val B = mutableSetOf<Int>()

    while (A.size < 15) A.add(Random.nextInt(1, 50))
    while (B.size < 15) B.add(Random.nextInt(51, 100))

    println("Multimea A: $A")
    println("Multimea B: $B")

    // 2. Aplicam calculul lambda (flatMap si map) pentru Produsul Cartesian A x B
    val produsCartesian: List<Pair<Int, Int>> = A.flatMap { a ->
        B.map { b -> Pair(a, b) }
    }

    // 3. Afisam rezultatul din noua colectie
    println("\nProdusul Cartesian contine ${produsCartesian.size} perechi (15 * 15).")
    println("Primele 10 perechi din colectie sunt:")
    produsCartesian.take(10).forEach { pereche ->
        print("(${pereche.first}, ${pereche.second}) ")
    }
    println()
}