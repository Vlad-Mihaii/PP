import itertools

def creeaza_generator_nume_temporare():
    # Utilizam un iterator infinit de numarare pus la dispozitie de itertools
    # Acesta porneste automat numaratoarea de la valoarea 1
    contor_itertools = itertools.count(start=1)

    # Aceasta este functia interna de tip Closure (inchidere)
    # Ea isi va aminti intotdeauna starea generatorului 'contor_itertools' intre apeluri
    def closure_generator(s1: str, s2: str) -> str:
        # Preluam valoarea urmatoare din generator folosind functia nativa next()
        index_curent = next(contor_itertools)

        # Returnam numele formatat conform sablonului cerut
        return f"{s1}-{index_curent}-{s2}.tmp"

    return closure_generator


# --- Zona de testare a functionalitatii ---
if __name__ == "__main__":
    # Initializam instanta functiei closure
    genereaza_nume = creeaza_generator_nume_temporare()

    print("--- Generare automata nume fisiere temporare ---")

    # Primul apel al metodei
    nume1 = genereaza_nume("log", "cache")
    print(f"Apel 1 -> {nume1}")

    # Al doilea apel al metodei (reține unde a rămas contorul)
    nume2 = genereaza_nume("log", "cache")
    print(f"Apel 2 -> {nume2}")

    # Apelul trei cu parametri textuali diferiti
    nume3 = genereaza_nume("date", "backup")
    print(f"Apel 3 -> {nume3}")

    # Apelul patru
    nume4 = genereaza_nume("temp", "test")
    print(f"Apel 4 -> {nume4}")