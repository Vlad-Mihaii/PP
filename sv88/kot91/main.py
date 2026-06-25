import tkinter as tk


# --- PRODUSE ---
class FormaGrafica:

    def deseneaza(self, canvas):
        pass


class Patrat(FormaGrafica):

    def deseneaza(self, canvas):
        canvas.create_rectangle(50, 50, 150, 150, fill="yellow")


class Cerc(FormaGrafica):

    def deseneaza(self, canvas):
        canvas.create_oval(200, 50, 300, 150, fill="cyan")


# --- FABRICA ---
class FabricaForme:

    @staticmethod
    def obtine_forma(tip_forma):
        if tip_forma == "patrat":
            return Patrat()
        elif tip_forma == "cerc":
            return Cerc()
        return None


if __name__ == "__main__":
    root = tk.Tk()
    root.title("Abstract Factory Shape Window")
    canvas = tk.Canvas(root, width=400, height=200, bg="white")
    canvas.pack()

    # Apelam fabrica pentru a crea dinamic formele dorite
    forma1 = FabricaForme.obtine_forma("patrat")
    forma2 = FabricaForme.obtine_forma("cerc")

    # Desenare polimorfica pe Canvas
    if forma1:
        forma1.deseneaza(canvas)
    if forma2:
        forma2.deseneaza(canvas)

    root.mainloop()