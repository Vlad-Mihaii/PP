from abc import ABC, abstractmethod
from datetime import date

class PaymentMethod(ABC):
    @abstractmethod
    def pay(self, fee: float) -> bool:
        pass

class BankAccount:
    def __init__(self, available_amount: float, card_number: str,
                 expiration_date: date, cvv_code: int, user_name: str):
        self.available_amount = available_amount
        self.card_number = card_number
        self.expiration_date = expiration_date
        self.cvv_code = cvv_code
        self.user_name = user_name

    def update_amount(self, value: float) -> bool:
        if self.available_amount != value:
            self.available_amount = value
            return True
        return False

class CardPayment(PaymentMethod):
    def __init__(self, bank_account: BankAccount):
        self.bank_account = bank_account

    def pay(self, fee: float) -> bool:
        if self.bank_account.available_amount >= fee:
            self.bank_account.available_amount  -= fee
            print("Plata s-a efectuat cu succes!")
            return True
        print("Nu se poate plati cu cardul!")
        return False

class CashPayment(PaymentMethod):
    def __init__(self, available_amount: float):
        self.available_amount = available_amount

    def pay(self, fee: float) -> bool:
        if self.available_amount >= fee:
            self.available_amount -= fee
            print("Plata s-a efectuat cu succes!")
            return True
        print("Nu se poate plati cash!")
        return False

if __name__ == "__main__":

    cont_bancar = BankAccount(100.0, "4444-5555", date(2027, 12, 1), 123, "Andrei M.")
    metoda_card = CardPayment(cont_bancar)

    metoda_cash = CashPayment(20.0)

    metoda_card.pay(50)
    metoda_cash.pay(50)
