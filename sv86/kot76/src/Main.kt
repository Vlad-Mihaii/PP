import java.io.File
import java.util.Date

// --- Componentele Modelului Observator ---
interface Observator {
    fun laModificarePret(user: String, rataVeche: Double, rataNoua: Double)
}

class LoggerModificari : Observator {
    override fun laModificarePret(user: String, rataVeche: Double, rataNoua: Double) {
        val fisierLog = File("jurnal_operatii.txt")
        val dataCurenta = Date()
        val mesaj = "Data: $dataCurenta | User: $user | Schimbare rata reducere: de la $rataVeche la $rataNoua\n"

        // Scrierea automata in fisier text separat
        fisierLog.appendText(mesaj)
        println("[Logger] Modificarea a fost inregistrata in jurnal.")
    }
}

// Interfata comuna pentru subiectul real si cel proxy
interface InterfataRecalculare {
    fun seteazaRataReducere(user: String, rataNoua: Double)
}

// Subiectul Real
class RecalculatorPret : InterfataRecalculare {
    private var rataReducere: Double = 0.0
    private val observatori = mutableListOf<Observator>()

    fun adaugaObservator(obs: Observator) {
        observatori.add(obs)
    }

    override fun seteazaRataReducere(user: String, rataNoua: Double) {
        val rataVeche = rataReducere
        rataReducere = rataNoua
        println("[Sistem] Pretul a fost recalculat cu noua rata de $rataNoua")

        // Notificam observatorii (Loggerul)
        observatori.forEach { it.laModificarePret(user, rataVeche, rataNoua) }
    }
}

// --- Componenta Modelului Proxy ---
class SecuritateProxy(private val calculatorReal: RecalculatorPret) : InterfataRecalculare {

    // Verificarea simpla a credențialelor de acces
    private fun valideazaUtilizator(user: String, parola: String): Boolean {
        return user == "admin" && parola == "secret123"
    }

    override fun seteazaRataReducere(user: String, rataNoua: Double) {
        // Simulam introducerea parolei in proxy
        val parolaIntroduca = "secret123"

        if (valideazaUtilizator(user, parolaIntroduca)) {
            calculatorReal.seteazaRataReducere(user, rataNoua)
        } else {
            println("[EROARE PROXY] Autentificare esuata pentru utilizatorul $user!")
        }
    }
}

fun main() {
    val calculatorReal = RecalculatorPret()
    val logger = LoggerModificari()
    calculatorReal.adaugaObservator(logger)

    val proxySecurizat = SecuritateProxy(calculatorReal)

    // Exemplu de apel valid de la tastatura/utilizator
    println("--- Incercare modificare pret cu date corecte ---")
    proxySecurizat.seteazaRataReducere("admin", 0.15)

    println("\n--- Incercare modificare pret cu date gresite ---")
    proxySecurizat.seteazaRataReducere("utilizator_anonim", 0.25)
}