from abc import ABC, abstractmethod

# --- 1. Interfata Mediator ---
class FurnicarMediator(ABC):
    @abstractmethod
    def trimite_mesaj(self, mesaj: str, expeditor: 'Furnica'):
        pass


# --- 2. Clasa de baza pentru Colegi (Furnica) ---
class Furnica(ABC):
    def __init__(self, nume: str, mediator: FurnicarMediator):
        self.nume = nume
        self.mediator = mediator

    @abstractmethod
    def primeste_mesaj(self, mesaj: str, de_la: str):
        pass


# --- 3. Coleg Concret ---
class FurnicaLucratoare(Furnica):
    def trimite(self, mesaj: str):
        print(f"\n[EMISIE] Furnica {self.nume} trimite mesajul: '{mesaj}'")
        self.mediator.trimite_mesaj(mesaj, self)

    def primeste_mesaj(self, mesaj: str, de_la: str):
        print(f" -> [RECEPTIE] Furnica {self.nume} a receptionat de la {de_la}: '{mesaj}'")


# --- 4. Mediator Concret ---
class MediatorColonie(FurnicarMediator):
    def __init__(self):
        self._furnici = []

    def inregistreaza_furnica(self, furnica: Furnica):
        self._furnici.append(furnica)

    def trimite_mesaj(self, mesaj: str, expeditor: Furnica):
        # Mediatorul redirectioneaza mesajul catre toate celelalte furnici din grup
        for furnica in self._furnici:
            if furnica != expeditor:
                furnica.primeste_mesaj(mesaj, expeditor.nume)


# --- Zona de rulare si testare ---
if __name__ == "__main__":
    # Cream camera de comunicare (mediatorul)
    mediator_central = MediatorColonie()

    # Cream furnicile din colonie
    f1 = FurnicaLucratoare("Alfa", mediator_central)
    f2 = FurnicaLucratoare("Beta", mediator_central)
    f3 = FurnicaLucratoare("Gama", mediator_central)

    # Inregistram membrii in reteaua mediatorului
    mediator_central.inregistreaza_furnica(f1)
    mediator_central.inregistreaza_furnica(f2)
    mediator_central.inregistreaza_furnica(f3)

    # Simulam schimburile de mesaje specifice cerute de problema
    f1.trimite("mancare")
    f2.trimite("drum bun")
    f3.trimite("pericol")
    f1.trimite("ajutor")