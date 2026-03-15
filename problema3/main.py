import os

class Note:
    def __init__(self, title, content, author):
        self.title = title
        self.content = content
        self.author = author

class NoteApp:
    def __init__(self, folder="notițe"):
        self.folder = folder
        if not os.path.exists(self.folder):
            os.makedirs(self.folder)

    def create_note(self, title, content, author):
        filename = self.folder + "/" + title + ".txt"
        with open(filename, "w") as f:
            f.write("Autor: " + author + "\n---\n" + content)
        print("Notiță salvată!")

    def list_notes(self):
        files = os.listdir(self.folder)
        return [f.replace(".txt", "") for f in files if f.endswith(".txt")]

    def read_note(self, title):
        try:
            path = self.folder + "/" + title + ".txt"
            with open(path, "r") as f:
                print("\n " + title + "  \n" + f.read())
        except FileNotFoundError:
            print("Eroare: Notița nu există!")

    def delete_note(self, title):
        path = self.folder + "/" + title + ".txt"
        if os.path.exists(path):
            os.remove(path)
            print(f"Notița" + " " +  title + " a fost ștearsă.")
        else:
            print("Notița nu a fost găsită.")

def start():
    app = NoteApp()
    user = input("Numele tău: ")

    while True:
        print("\n1. Listă | 2. Citește | 3. Nouă | 4. Șterge | 5. Ieșire")
        op = input("Alege: ")

        if op == "1":
            print("Notițe: " + str(app.list_notes()))
        elif op == "2":
            app.read_note(input("Titlu: "))
        elif op == "3":
            app.create_note(input("Titlu: "), input("Conținut: "), user)
        elif op == "4":
            app.delete_note(input("Titlu de șters: "))
        elif op == "5":
            break

if __name__ == "__main__":
    start()