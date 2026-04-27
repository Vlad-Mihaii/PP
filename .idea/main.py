# Observer

class Observable:
    def __init__(self):
        self._observers = []

    def attach(self, observer):
        self._observers.append(observer)

    def notify(self, *args):
        for observer in self._observers:
            observer.update(*args)

class DisplayObserver:
    def update(self, suma):
        print(f"Suma curenta: {suma} lei.")

# TakeMoneySTM

class TakeMoneySTM(Observable):
    def __init__(self):
        super().__init__()
        self.money = 0

    def adauga_bani(self, suma):
        self.money += suma
        # Observatorul este anuntat ca suma s-a schimbat
        self.notify(self.money)

    def reseteaza_banii(self):
        self.money = 0

#  SelectProductSTM

class SelectProductSTM(Observable):

    def __init__(self):
        super().__init__()
        self.produse = {
            "1": {"nume": "Coca-Cola", "pret": 5},
            "2": {"nume": "Pepsi", "pret": 5},
            "3": {"nume": "Sprite", "pret": 4}
        }

    def alege_produs(self):
        print("\n   Produse disponibile   ")
        for cheie, info in self.produse.items():
            print(f"{cheie}. {info['nume']} - {info['pret']} lei")

        optiune = input("Selectati numarul produsului: ")

        if optiune in self.produse:
            produs_ales = self.produse[optiune]
            # VendingMachine este anunat daca un produs a fost ales
            self.notify(produs_ales)
        else:
            print("Opțiune invalidă!")
            return None

# (VendingMachineSTM)

class VendingMachineSTM:

    def __init__(self):
        # Inițializăm sub-automatele
        self.take_money_stm = TakeMoneySTM()
        self.select_product_stm = SelectProductSTM()

        #  observatorii
        self.take_money_stm.attach(DisplayObserver()) #  afisare bani
        self.select_product_stm.attach(self)          # VendingMachine ascultă SelectProduct

    def update(self, produs_ales):
        #apelata automat la selectia unui produs
        self.valideaza_tranzactia(produs_ales)

    def valideaza_tranzactia(self, produs):
        print(f"\n {produs['nume']}:  care costa {produs['pret']} lei.")

        if self.take_money_stm.money >= produs['pret']:
            rest = self.take_money_stm.money - produs['pret']
            print(f" produsul: {produs['nume']}.")

            if rest > 0:
                print(f"restul: {rest} lei.")

            self.take_money_stm.reseteaza_banii()
        else:
            lipsa = produs['pret'] - self.take_money_stm.money
            print(f" Mai aveti nevoie de {lipsa} lei.")
            # Se revine la starea de adaugare bani (bucla principala )

    def porneste(self):
        """Simularea funcționării automatului."""
        print("Bun venit la automatul de sucuri!")

        while True:
            print("\n1. Introdu bani")
            print("2. Selectează produs")
            print("3. Ieșire")

            comanda = input("Alegeti o actiune: ")

            if comanda == "1":
                suma = int(input("Introduceti suma (1, 5, 10 lei): "))
                self.take_money_stm.adauga_bani(suma)
            elif comanda == "2":
                self.select_product_stm.alege_produs()
            elif comanda == "3":
                print("exit")
                break
            else:
                print("incorect.")

if __name__ == "__main__":
    automat = VendingMachineSTM()
    automat.porneste()