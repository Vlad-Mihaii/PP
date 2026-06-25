import java.util.concurrent.ConcurrentHashMap

class MemoizatorGeneralizat<In, Out>(private val functieCalcul: (In, MemoizatorGeneralizat<In, Out>) -> Out) {
    // Hasmap concurent si sigur la executia pe mai multe fire
    private val cache = ConcurrentHashMap<In, Out>()

    fun calculeaza(cheie: In): Out {
        // Daca valoarea exista in cache, o returneaza, altfel o calculeaza instant
        return cache.computeIfAbsent(cheie) { functieCalcul(it, this) }
    }
}

fun main() {
    // Definirea relatiei de recurenta folosind formula f(i) = f(i-1) + f(i-2)
    val memoizatorSir = MemoizatorGeneralizat<Long, Long> { i, mem ->
        if (i == 0L) 0L
        else if (i == 1L) 1L
        else mem.calculeaza(i - 1) + mem.calculeaza(i - 2)
    }

    val n = 40L
    println("Calcularea sirului pana la valoarea n = $n folosind ConcurrentHashMap:")
    for (i in 1..n) {
        println("f($i) = ${memoizatorSir.calculeaza(i)}")
    }
}