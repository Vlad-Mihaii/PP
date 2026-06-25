// 1. Clasa de baza abstracta care defineste comportamentul general
abstract class Om(val nume: String, val esteBarbat: Boolean) {
    abstract fun mananca()
    abstract fun bea()
    abstract fun danseaza()

    // Metode ajutatoare pentru a raspunde polimorfic la intrebari
    abstract fun ceMananca(): String
    abstract fun cuCineDanseaza(): String
}

// 2. Subclasa pentru Ion
class Ion : Om("Ion", true) {
    override fun mananca() = println("$nume mananca mere.")
    override fun bea() = println("$nume bea bere.")
    override fun danseaza() = println("$nume danseaza cu femei.")

    override fun ceMananca() = "mere"
    override fun cuCineDanseaza() = "femenii"
}

// 3. Subclasa pentru Vasile
class Vasile : Om("Vasile", true) {
    override fun mananca() = println("$nume mananca pere.")
    override fun bea() = println("$nume bea vodca.")
    override fun danseaza() = println("$nume nu danseaza cu barbati.")

    override fun ceMananca() = "pere"
    override fun cuCineDanseaza() = " barbati "
}

// 4. Subclasa pentru Alex
class Alex : Om("Alex", true) { // Considerat barbat conform contextului numelui
    override fun mananca() = println("$nume mananca prajituri.")
    override fun bea() = println("$nume bea vin.")
    override fun danseaza() = println("$nume danseaza cu sefi.")

    override fun ceMananca() = "prajituri"
    override fun cuCineDanseaza() = "sefi"
}

fun main() {
    // Cream grupul de oameni (o lista polimorfica)
    val grupOameni: List<Om> = listOf(Ion(), Vasile(), Alex())

    println("--- Executia activitatilor pentru fiecare om ---")
    for (om in grupOameni) {
        om.mananca()
        om.bea()
        om.danseaza()
        println()
    }

    println("--- Raspunsuri la intrebarile din bilet ---")

    // Intrebarea 1: Cu cine poate dansa Vasile?
    val vasile = grupOameni.find { it.nume == "Vasile" }
    if (vasile != null) {
        println("1. Vasile poate dansa cu: ${vasile.cuCineDanseaza()}.")
    }

    // Intrebarea 2: Ce pot manca barbatii?
    val alimenteBarbati = grupOameni.filter { it.esteBarbat }.map { it.ceMananca() }
    println("2. Barbatii din grup (Ion, Vasile, Alex) pot manca: ${alimenteBarbati.joinToString(", ")}.")
}