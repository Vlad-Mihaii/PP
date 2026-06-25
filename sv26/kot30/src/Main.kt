// 1. Definim o functie simpla care primeste trei parametri intregi si returneaza suma lor
fun sumaTreiVariabile(a: Int, b: Int, c: Int): Int {
    return a + b + c
}

fun main() {
    // 2. Definim un set de valori (variabile) pe care le vom trimite ca argumente
    val valoare1 = 15
    val valoare2 = 25
    val valoare3 = 40

    // 3. Apelam direct functia creata mai sus transmitem setul de valori
    val rezultat = sumaTreiVariabile(valoare1, valoare2, valoare3)

    // 4. Afisam rezultatul obtinut in consola aplicatiei
    println("Setul de valori folosit: $valoare1, $valoare2, $valoare3")
    println("Rezultatul apelului direct al functiei este: $rezultat")
}