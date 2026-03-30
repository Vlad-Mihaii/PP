import os
import sys

class IdentificatorFisiere:
    def __init__(self, director):
        self.director = director

    def analizeaza_fisier(self, cale_fisier):
        try:
            with open(cale_fisier, 'rb') as f:  # citire binara
                date = f.read()

            if not date:
                return "Gol"

            total_octeti = len(date)
            # De cate ori apare fiecare byte (0-255)
            frecvente = [0] * 256
            for octet in date:
                frecvente[octet] += 1

            # UTF-16, 30%
            procent_zero = (frecvente[0] / total_octeti) * 100
            if procent_zero >= 30:
                return "Text UNICODE/UTF16"

            # ASCII / UTF-8
            #9(TAB), 10(LF), 13(CR), 32-127(Space & printable) , frecventa mare
            caractere_text = frecvente[9] + frecvente[10] + frecvente[13]
            for i in range(32, 128):
                caractere_text += frecvente[i]

            procent_text = (caractere_text / total_octeti) * 100

            # Pentru ascii, text
            if procent_text > 90:
                return "Text ASCII/UTF8"
            return "Binar"

        except Exception as e:
            return f"Eroare la citire: {e}"

    def porneste_scanarea(self):
        if not os.path.isdir(self.director):
            print(f"Eroare: Calea {self.director} nu este un director valid.")
            return

        for fisier in os.listdir(self.director):
            cale_completa = os.path.join(self.director, fisier)
            if os.path.isfile(cale_completa):
                rezultat = self.analizeaza_fisier(cale_completa)
                print(f"{fisier:<20} | {rezultat:<20}")

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Utilizare: python script.py <cale_director>")
    else:
        cale_tinta = sys.argv[1]
        app = IdentificatorFisiere(cale_tinta)
        app.porneste_scanarea()