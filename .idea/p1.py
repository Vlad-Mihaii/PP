import datetime
import collections
import functools
from collections import namedtuple
from functional import seq

# echivalent data class din kotlin
Person = namedtuple('Person', ['firstName', 'lastName', 'dateOfBirth', 'emailAddress'])

# lista pentru persoane
persons = [
    Person("John", "Doe", datetime.date(1960, 11, 3), "jdoe@example.com"),
    Person("Ellen", "Smith", datetime.date(1992, 5, 13), "ellensmith@example.com"),
    Person("Jane", "White", datetime.date(1986, 2, 1), "janewhite@example.com"),
    Person("Bill", "Jackson", datetime.date(1999, 11, 6), "bjackson@example.com"),
    Person("John", "Smith", datetime.date(1975, 7, 14), "johnsmith@example.com"),
    Person("Jack", "Williams", datetime.date(2005, 5, 28), "")
]

#  Gasirea celei mai tinere si celei mai batrane persoane
youngest = seq(persons).sorted(key=lambda p: p.dateOfBirth, reverse=True).first()
oldest = seq(persons).sorted(key=lambda p: p.dateOfBirth).first()
print(f"Cea mai tanara persoana: {youngest}")
print(f"Cea mai batrana persoana: {oldest}\n")

#  Filtrare persoane minore (< 18 ani)
today = datetime.date.today()
underage = seq(persons).filter(lambda p: (today - p.dateOfBirth).days / 365.25 < 18).to_list()
print(f"Persoane minore: {underage}\n")

#  Lista de e-mailuri
emails = seq(persons).map(lambda p: p.emailAddress).to_list()
print(f"E-mailuri: {emails}\n")

#  Dictionar de Nume -> E-mail
emails_map = seq(persons).map(lambda p: (f"{p.firstName} {p.lastName}", p.emailAddress)).to_dict()
print(f"Mapa Nume -> Email: {emails_map}\n")

#  Gruparea dupa luna nasterii
by_month = seq(persons).group_by(lambda p: p.dateOfBirth.month).to_dict()
print(f"Grupate dupa luna: {by_month}\n")

# 6. Prenume distincte concatenate prin virgula
distinct_names = seq(persons).map(lambda p: p.firstName).distinct().make_string(", ")
print(f"Prenume distincte: {distinct_names}\n")

# 7. Numararea persoanelor cu numele "Smith"
smiths_count = seq(persons).count(lambda p: p.lastName == "Smith")
print(f"Numar de persoane numite Smith: {smiths_count}\n")