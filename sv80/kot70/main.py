# Interfata Comanda
class Comanda:

    def executa(self):
        pass


# Receptorul (Receiver) - cel care sufera schimbarile
class Student:

    def __init__(self, nume):
        self.nume = nume
        self.stare = "Fericit"


# Comenzi Concrete
class ComandaExamenNeprevazut(Comanda):

    def __init__(self, student):
        self.student = student

    def executa(self):
        self.student.stare = "Disperat"


class ComandaNotaZece(Comanda):

    def __init__(self, student):
        self.student = student

    def executa(self):
        self.student.stare = "Fericit"


# Invocatorul (Invoker)
class Profesor:

    def __init__(self):
        self._istoric_comenzi = []

    def da_comanda(self, comanda):
        self._istoric_comenzi.append(comanda)
        comanda.executa()


# Executie
if __name__ == "__main__":
    s1 = Student("Mihai")
    prof = Profesor()

    print(f"Stare initiala {s1.nume}: {s1.stare}")

    # Profesorul trimite o comanda de examen neprevazut
    prof.da_comanda(ComandaExamenNeprevazut(s1))
    print(f"Stare dupa actiune profesor: {s1.stare}")