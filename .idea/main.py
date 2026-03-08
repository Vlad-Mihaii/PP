import re
from typing import TypeVar, Generic, List

# Definim un tip generic T care trebuie să fie un șir de caractere (str)
T = TypeVar('T', bound=str)

class Ebook(Generic[T]):
    def __init__(self, raw_content: T):
        self.raw_content = raw_content

    def curatare(self) -> str:
        text = str(self.raw_content)

        # 1. Scos numar de pagina ( cifra intre cel putin 2 spatii albe)
        text = re.sub(r"\s{2,}+\d+\s{2,}", " ", text)

        # 2. Eliminare linie nouă multiple
        text = re.sub(r"(\r?\n)", "\n", text)

        # 3. Eliminarea spațiilor multiple (rămâne numai unul)
        text = re.sub(r"[ ]+", " ", text)

        return text.strip()

    def get_paragrafe(self) -> List[str]:
        # Utilizăm colecții (List) și procesare funcțională
        modificat = self.curatare()
        # split creează o listă; filter elimină elementele goale ; split elimina spatii goale la inceput si sfarsit
        return [p for p in modificat.split('\n') if p.strip()]

# --- Programul Principal ---
if __name__ == "__main__":
    text_init = """
    Ana are multe        mere    50 .
        
                  42          
        
        
    Ana are si mai multe mere  .
    """

    print("--- Text Original ---")
    print(text_init)
    print("-" * 30)

    # Instanțiere
    procesare = Ebook(text_init)
    text_rez = procesare.curatare()

    print("--- Text Procesat ---")
    print(text_rez)

    # Lista , len - nr elemente lista
    paragrafe = procesare.get_paragrafe()
    print(f"\nNumăr paragrafe detectate: {len(paragrafe)}")