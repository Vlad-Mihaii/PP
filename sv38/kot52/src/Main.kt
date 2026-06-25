fun main() {
    // Generam multimea A de la 1 la 100 ca o lista (colectie)
    val A = (1..100).toList()

    // Problema cere submultimi de 4 elemente care neaparat contin numarul 1.
    // Asta inseamna ca numarul 1 este deja ales, iar noi trebuie sa mai alegem
    // inca 3 elemente din cele 99 ramase (toate elementele in afara de 1).

    // Filtram lista pentru a obtine elementele ramase (fara elementul 1)
    val elementeRamase = A.filter { it != 1 }

    // Calculam matematic combinari de 99 luate cate 3: (99 * 98 * 97) / (1 * 2 * 3)
    // Folosim o abordare functionala (fold/transformare) pentru a simula un calcul curat
    val n = elementeRamase.size.toLong() // 99

    // Formula: (n * (n-1) * (n-2)) / 6
    val numarSubmultimi = (n * (n - 1) * (n - 2)) / 6

    println("Multimea initiala are ${A.size} elemente.")
    println("Numarul de submultimi de 4 elemente care contin elementul 1 este: $numarSubmultimi")
}