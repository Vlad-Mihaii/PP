//  Interfata (Bridge)
interface LogicEngine {
    fun calculate(inputs: List<Boolean>): Boolean
}

// FSM
class FsmLogicEngine : LogicEngine {

    // Starile Automatului Finit
    private enum class State {
        POTENTIAL_TRUE, // Starea inițială: totul e true până acum
        DEFINITIVE_FALSE // S-a întâlnit un 'false', rezultatul final va fi false
    }

    override fun calculate(inputs: List<Boolean>): Boolean {
        var currentState = State.POTENTIAL_TRUE

        for (input in inputs) {
            currentState = when (currentState) {
                State.POTENTIAL_TRUE -> if (input) State.POTENTIAL_TRUE else State.DEFINITIVE_FALSE
                State.DEFINITIVE_FALSE -> State.DEFINITIVE_FALSE
            }
            // Optimizare: dacă am ajuns în starea FALSE, nu mai are sens să verificăm restul
            if (currentState == State.DEFINITIVE_FALSE) break
        }

        return currentState == State.POTENTIAL_TRUE
    }
}

// Abstractizare
abstract class AndGate(protected val engine: LogicEngine) {
    protected val inputs = mutableListOf<Boolean>()

    fun addInput(value: Boolean) {
        inputs.add(value)
    }

    fun getResult(): Boolean = engine.calculate(inputs)

    abstract fun isValid(): Boolean
}


class AndGate2(engine: LogicEngine) : AndGate(engine) {
    override fun isValid() = inputs.size == 2
}

class AndGate3(engine: LogicEngine) : AndGate(engine) {
    override fun isValid() = inputs.size == 3
}

class AndGate4(engine: LogicEngine) : AndGate(engine) {
    override fun isValid() = inputs.size == 4
}

class AndGate8(engine: LogicEngine) : AndGate(engine) {
    override fun isValid() = inputs.size == 8
}

class AndGateBuilder(private val engine: LogicEngine) {
    private val inputs = mutableListOf<Boolean>()

    fun addInput(value: Boolean): AndGateBuilder {
        inputs.add(value)
        return this
    }

    fun build(): AndGate {
        val gate = when (inputs.size) {
            2 -> AndGate2(engine)
            3 -> AndGate3(engine)
            4 -> AndGate4(engine)
            8 -> AndGate8(engine)
            else -> throw IllegalArgumentException("Doar porti cu 2, 3, 4 sau 8 intrari sunt suportate. Primite: ${inputs.size}")
        }

        inputs.forEach { gate.addInput(it) }
        return gate
    }
}

fun main() {
    // Alegem implementarea motorului de calcul (Bridge)
    val fsmEngine = FsmLogicEngine()

    // AND 3 intrari
    val gate3Inputs = AndGateBuilder(fsmEngine)
        .addInput(true)
        .addInput(true)
        .addInput(false)
        .build()

    println("Rezultat Poartă AND (3 intrari): ${gate3Inputs.getResult()}")

    // AND 2 intrari
    val gate2Inputs = AndGateBuilder(fsmEngine)
        .addInput(true)
        .addInput(true)
        .build()

    println("Rezultat Poartă AND (2 intrari): ${gate2Inputs.getResult()}")

    //  AND 8 intrari
    val builder8 = AndGateBuilder(fsmEngine)
    repeat(7) { builder8.addInput(true) }
    builder8.addInput(true) // Al 8-lea element

    val gate8Inputs = builder8.build()
    println("Rezultat Poartă AND (8 intrari): ${gate8Inputs.getResult()}")
}