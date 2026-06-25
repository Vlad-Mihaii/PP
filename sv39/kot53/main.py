import tkinter as tk
from tkinter import messagebox
from tkinter import ttk


def salveaza_date():
    # Preluam datele introduse de utilizator in campuri
    companie = entry_companie.get()
    departament = entry_departament.get()
    nume = entry_nume.get()
    zi = entry_zi.get()
    luna = combo_luna.get()
    an = entry_an.get()
    functie = entry_functie.get()
    salariu = entry_salariu.get()

    # Validare simpla pentru a ne asigura ca nu exista campuri goale
    if not (companie and departament and nume and zi and an and functie and salariu):
        messagebox.showwarning("Atentie", "Toate campurile trebuie completate!")
        return

    # Formatam data de nastere intr-un singur sir de caractere
    data_nastere = f"{zi} {luna} {an}"

    # Cream textul final care va fi afisat sau salvat
    text_afisare = (
        f"Companie: {companie}\n"
        f"Departament: {departament}\n"
        f"Nume: {nume}\n"
        f"Data nastere: {data_nastere}\n"
        f"Functie: {functie}\n"
        f"Salariu: {salariu}"
    )

    # Afisam datele introduse intr-o ferestra de tip pop-up
    messagebox.showinfo("Date Angajat Salvate", text_afisare)


def despre_program():
    # Functie pentru meniul Ajutor
    messagebox.showinfo(
        "Despre", "Program Tkinter pentru gestiunea datelor unui angajat."
    )


# 1. Crearea ferestrei principale a aplicatiei
root = tk.Tk()
root.title("Introducere Date Angajat")
root.geometry("400x420")

# 2. Crearea barei de meniu
menu_bar = tk.Menu(root)

# Adaugam meniul 'Fisier' cu optiunea de Iesire
menu_fisier = tk.Menu(menu_bar, tearoff=0)
menu_fisier.add_command(label="Iesire", command=root.quit)
menu_bar.add_cascade(label="Fisier", menu=menu_fisier)

# Adaugam meniul 'Ajutor' cu optiunea Despre
menu_ajutor = tk.Menu(menu_bar, tearoff=0)
menu_ajutor.add_command(label="Despre", command=despre_program)
menu_bar.add_cascade(label="Ajutor", menu=menu_ajutor)

root.config(menu=menu_bar)

# 3. Crearea elementelor de interfata (Labels si Entries) folosind Grid
tk.Label(root, text="Companie:").grid(
    row=0, column=0, padx=10, pady=5, sticky="w"
)
entry_companie = tk.Entry(root, width=25)
entry_companie.grid(row=0, column=1, padx=10, pady=5)

tk.Label(root, text="Departament:").grid(
    row=1, column=0, padx=10, pady=5, sticky="w"
)
entry_departament = tk.Entry(root, width=25)
entry_departament.grid(row=1, column=1, padx=10, pady=5)

tk.Label(root, text="Nume complet:").grid(
    row=2, column=0, padx=10, pady=5, sticky="w"
)
entry_nume = tk.Entry(root, width=25)
entry_nume.grid(row=2, column=1, padx=10, pady=5)

# Campuri pentru Data de Nastere
tk.Label(root, text="Zi nastere (DD):").grid(
    row=3, column=0, padx=10, pady=5, sticky="w"
)
entry_zi = tk.Entry(root, width=25)
entry_zi.grid(row=3, column=1, padx=10, pady=5)

tk.Label(root, text="Luna nastere:").grid(
    row=4, column=0, padx=10, pady=5, sticky="w"
)
luni_an = [
    "Ianuarie",
    "Februarie",
    "Martie",
    "Aprilie",
    "Mai",
    "Iunie",
    "Iulie",
    "August",
    "Septembrie",
    "Octombrie",
    "Noiembrie",
    "Decembrie",
]
combo_luna = ttk.Combobox(root, values=luni_an, width=22, state="readonly")
combo_luna.grid(row=4, column=1, padx=10, pady=5)
combo_luna.set("Ianuarie")  # Selectie implicita

tk.Label(root, text="An nastere (YYYY):").grid(
    row=5, column=0, padx=10, pady=5, sticky="w"
)
entry_an = tk.Entry(root, width=25)
entry_an.grid(row=5, column=1, padx=10, pady=5)

# Campuri pentru Functie si Salariu
tk.Label(root, text="Functie:").grid(
    row=6, column=0, padx=10, pady=5, sticky="w"
)
entry_functie = tk.Entry(root, width=25)
entry_functie.grid(row=6, column=1, padx=10, pady=5)

tk.Label(root, text="Salariu:").grid(
    row=7, column=0, padx=10, pady=5, sticky="w"
)
entry_salariu = tk.Entry(root, width=25)
entry_salariu.grid(row=7, column=1, padx=10, pady=5)

# 4. Butonul de salvare a datelor
btn_salveaza = tk.Button(
    root, text="Salveaza Date Angajat", command=salveaza_date, bg="lightgray"
)
btn_salveaza.grid(row=8, column=0, columnspan=2, pady=20)

# Pornirea buclei principale a interfetei grafice
root.mainloop()